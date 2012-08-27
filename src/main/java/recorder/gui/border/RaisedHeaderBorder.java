/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.border;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
/**
 * Bordure qui ajoute un pseudo effet de 3D.
 * 
 * <p>
 * Utilise les couleurs 'controlLtHighlight' et 'controlShadow' définit dans le
 * UIManager.
 * </p>
 */
public class RaisedHeaderBorder extends AbstractBorder {
    private static final Insets INSETS = new Insets(1, 1, 1, 0);

    public Insets getBorderInsets(Component component) {
        return INSETS;
    }


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
