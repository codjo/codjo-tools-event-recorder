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
package recorder.gui.border;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
/**
 * Border with a pseudo 3D effect.
 *
 * <p> Use 'controlLtHighlight' and 'controlShadow' colors defined in the UIManager. </p>
 */
public class RaisedHeaderBorder extends AbstractBorder {
    private static final Insets INSETS = new Insets(1, 1, 1, 0);


    @Override
    public Insets getBorderInsets(Component component) {
        return INSETS;
    }


    @Override
    public void paintBorder(Component component, Graphics graphics, int x, int y, int w, int h) {
        graphics.translate(x, y);
        graphics.setColor(UIManager.getColor("controlLtHighlight"));
        graphics.fillRect(0, 0, w, 1);
        graphics.fillRect(0, 1, 1, h - 1);
        graphics.setColor(UIManager.getColor("controlShadow"));
        graphics.fillRect(0, h - 1, w, 1);
        graphics.translate(-x, -y);
    }
}
