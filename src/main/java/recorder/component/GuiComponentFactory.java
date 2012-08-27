/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.component;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
/**
 * Retour le composant 'sémantique' associé à l'event.
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
