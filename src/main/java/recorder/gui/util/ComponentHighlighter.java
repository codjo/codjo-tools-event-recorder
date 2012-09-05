/*
 * codjo (Prototype)
 * =================
 *
 *    Copyright (C) 2005, 2012 by codjo.net
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *    implied. See the License for the specific language governing permissions
 *    and limitations under the License.
 */
package recorder.gui.util;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import recorder.component.FindStrategyId;
import recorder.component.GuiComponent;
import recorder.component.GuiComponentFactory;
/**
 * Highlight the GUI Component found beneath the mouse.
 */
public class ComponentHighlighter implements AWTEventListener {
    static final Color FIND_BY_NAME_COLOR = Color.green;
    static final Color OTHER_FIND_COLOR = Color.yellow;
    static final Color NOT_FINDABLE_COLOR = Color.red;
    private GuiComponentFactory factory;
    private Color prevColor;
    private JComponent prevComponent;
    private HighlightListener listener;


    public ComponentHighlighter() {
        this(new GuiComponentFactory());
    }


    public ComponentHighlighter(GuiComponentFactory factory) {
        this.factory = factory;
    }


    public void start() {
        Toolkit.getDefaultToolkit().addAWTEventListener(this,
                                                        AWTEvent.MOUSE_EVENT_MASK | AWTEvent.KEY_EVENT_MASK
                                                        | AWTEvent.FOCUS_EVENT_MASK);
    }


    public void stop() {
        Toolkit.getDefaultToolkit().removeAWTEventListener(this);
    }


    public void eventDispatched(AWTEvent awtEvent) {
        if (!(awtEvent instanceof MouseEvent)) {
            return;
        }

        GuiComponent guiComponent = factory.find(awtEvent);
        if (guiComponent == null) {
            return;
        }

        if (prevComponent != null) {
            prevComponent.setBackground(prevColor);
        }

        boolean exitFromPrev =
              guiComponent.getSwingComponent() == prevComponent
              && awtEvent.getID() != MouseEvent.MOUSE_ENTERED;

        if (exitFromPrev) {
            prevComponent = null;
            return;
        }

        prevComponent = guiComponent.getSwingComponent();
        prevColor = prevComponent.getBackground();

        prevComponent.setBackground(getHighlightColorFor(guiComponent));
        fireHighlight(guiComponent);
    }


    private void fireHighlight(GuiComponent guiComponent) {
        if (listener != null) {
            listener.highlight(guiComponent.getSwingComponent());
        }
    }


    private Color getHighlightColorFor(GuiComponent guiComponent) {
        if (!guiComponent.isFindable()) {
            return NOT_FINDABLE_COLOR;
        }
        else if (guiComponent.canBeFoundWith(FindStrategyId.BY_NAME)) {
            return FIND_BY_NAME_COLOR;
        }
        else {
            return OTHER_FIND_COLOR;
        }
    }


    public void setListener(HighlightListener listener) {
        this.listener = listener;
    }
}
