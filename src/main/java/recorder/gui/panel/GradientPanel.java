/*
 * codjo.net
 *
 * Common Apache License 2.0
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
