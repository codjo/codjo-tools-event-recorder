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
package recorder.gui.panel;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.SplitPaneUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
/**
 * Extends <code>JSplitPane</code> in order to remove the unaesthetic divider.
 *
 * @see javax.swing.plaf.basic.BasicSplitPaneUI
 */
public final class LightSplitPane extends JSplitPane {
    public LightSplitPane() {
        this(JSplitPane.HORIZONTAL_SPLIT, false,
             new JButton(UIManager.getString("SplitPane.leftButtonText")),
             new JButton(UIManager.getString("SplitPane.rightButtonText")));
    }


    public LightSplitPane(int newOrientation) {
        this(newOrientation, false);
    }


    public LightSplitPane(int newOrientation, boolean newContinuousLayout) {
        this(newOrientation, newContinuousLayout, null, null);
    }


    public LightSplitPane(int newOrientation, Component leftComponent,
                          Component rightComponent) {
        this(newOrientation, false, leftComponent, rightComponent);
    }


    public LightSplitPane(int newOrientation, boolean newContinuousLayout,
                          Component leftComponent, Component rightComponent) {
        super(newOrientation, newContinuousLayout, leftComponent, rightComponent);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }


    @Override
    public void updateUI() {
        super.updateUI();
        hideDivider();
    }


    private void hideDivider() {
        SplitPaneUI splitPaneUI = getUI();

        if (splitPaneUI instanceof BasicSplitPaneUI) {
            BasicSplitPaneUI basicUI = (BasicSplitPaneUI)splitPaneUI;
            BasicSplitPaneDivider divider = basicUI.getDivider();
            divider.setBorder(new BlankDividerBorder());
        }
    }


    private class BlankDividerBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component component, Graphics graphics, int x, int y, int w, int h) {
            graphics.setColor(getBackground());
            graphics.fillRect(x, y, w, h);
        }
    }
}
