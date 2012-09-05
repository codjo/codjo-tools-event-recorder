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
 * Border with a shadow (down and left sides).
 *
 * <p> Use 'controlShadow' color defined in the UIManager. </p>
 */
public class ShadowBorder extends AbstractBorder {
    private static final Insets INSETS = new Insets(1, 1, 3, 3);


    @Override
    public Insets getBorderInsets(Component component) {
        return INSETS;
    }


    @Override
    public void paintBorder(Component component, Graphics graphics, int x, int y, int w, int h) {
        Color shadow = UIManager.getColor("controlShadow");
        if (shadow == null) {
            shadow = Color.gray;
        }
        Color lightShadow =
              new Color(shadow.getRed(), shadow.getGreen(), shadow.getBlue(), 170);
        Color lighterShadow =
              new Color(shadow.getRed(), shadow.getGreen(), shadow.getBlue(), 70);
        graphics.translate(x, y);

        graphics.setColor(shadow);
        graphics.fillRect(0, 0, w - 3, 1);
        graphics.fillRect(0, 0, 1, h - 3);
        graphics.fillRect(w - 3, 1, 1, h - 3);
        graphics.fillRect(1, h - 3, w - 3, 1);
        // Shadow line 1
        graphics.setColor(lightShadow);
        graphics.fillRect(w - 3, 0, 1, 1);
        graphics.fillRect(0, h - 3, 1, 1);
        graphics.fillRect(w - 2, 1, 1, h - 3);
        graphics.fillRect(1, h - 2, w - 3, 1);
        // Shadow line2
        graphics.setColor(lighterShadow);
        graphics.fillRect(w - 2, 0, 1, 1);
        graphics.fillRect(0, h - 2, 1, 1);
        graphics.fillRect(w - 2, h - 2, 1, 1);
        graphics.fillRect(w - 1, 1, 1, h - 2);
        graphics.fillRect(1, h - 1, w - 2, 1);
        graphics.translate(-x, -y);
    }
}
