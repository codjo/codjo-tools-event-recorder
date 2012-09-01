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
/**
 * <p> Use 'control' color defined in the UIManager. </p>
 */
public class GradientPanel extends JPanel {
    public GradientPanel() {
        setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
    }


    public GradientPanel(LayoutManager lm, Color background) {
        super(lm);
        setBackground(background);
    }


    public GradientPanel(LayoutManager layout) {
        super(layout);
        setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
    }


    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (!isOpaque()) {
            return;
        }
        Color control = UIManager.getColor("control");
        int width = getWidth();
        int height = getHeight();

        Graphics2D g2Graphics2D = (Graphics2D)graphics;
        Paint storedPaint = g2Graphics2D.getPaint();
        g2Graphics2D.setPaint(new GradientPaint(0, 0, getBackground(), width, 0, control));
        g2Graphics2D.fillRect(0, 0, width, height);
        g2Graphics2D.setPaint(storedPaint);
    }
}
