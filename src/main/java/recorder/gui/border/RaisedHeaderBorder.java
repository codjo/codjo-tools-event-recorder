/*
 * codjo.net
 *
 * Common Apache License 2.0
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
