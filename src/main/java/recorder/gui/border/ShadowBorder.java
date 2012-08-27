/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.border;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
/**
 * Ajoute une bordure avec ombre (coté droit et en bas).
 * 
 * <p>
 * Utilise les couleurs 'controlShadow' définit dans le UIManager.
 * </p>
 */
public class ShadowBorder extends AbstractBorder {
    private static final Insets INSETS = new Insets(1, 1, 3, 3);

    public Insets getBorderInsets(Component component) {
        return INSETS;
    }


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
