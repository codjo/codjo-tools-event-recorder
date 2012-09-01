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
package recorder.component;
import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.*;
/**
 * Creates the 'semantic' representation of the gui component linked to the event.
 */
public class GuiComponentFactory {
    private final ComponentResolver resolver = new ComponentResolver();


    public GuiComponent find(Object source) {
        return toGuiComponent(resolver.find(source));
    }


    public GuiComponent find(MouseEvent event) {
        return toGuiComponent(resolver.find(event));
    }


    public GuiComponent find(AWTEvent event) {
        return toGuiComponent(resolver.find(event));
    }


    public void setIgnoredContainer(String ignoredContainer) {
        resolver.setIgnoredContainer(ignoredContainer);
    }


    private GuiComponent toGuiComponent(JComponent found) {
        if (found != null) {
            return newGuiComponent(found);
        }
        else {
            return null;
        }
    }


    public static GuiComponent newGuiComponent(JComponent swingComponent) {
        return new GuiComponent(swingComponent);
    }
}
