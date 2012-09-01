/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.component;
import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.*;
/**
 * Find the real underneath Swing component.
 *
 * <p> Some Swing components are composed of more basic components : JComboBox is a good example. </p>
 */
class ComponentResolver {
    private String ignoredContainer;


    public JComponent find(MouseEvent event) {
        return findImpl(event.getSource(), event.getPoint());
    }


    public JComponent find(AWTEvent event) {
        if (event instanceof MouseEvent) {
            return find((MouseEvent)event);
        }
        return findImpl(event.getSource(), null);
    }


    public JComponent find(Object source) {
        return findImpl(source, null);
    }


    public void setIgnoredContainer(String ignoredContainer) {
        this.ignoredContainer = ignoredContainer;
    }


    private JComponent findImpl(Object source, Point point) {
        JComponent found;

        if (source == null) {
            found = null;
        }
        else if (source instanceof JComponent) {
            JComponent component = (JComponent)source;
            JComboBox combo = findParentCombo(component);
            found = combo != null ? combo : component;
        }
        else {
            Component result = null;
            if (point != null) {
                result = ((Container)source).findComponentAt(point);
            }
            found = findImpl((source == result ? null : result), point);
        }

        return filter(found);
    }


    private JComponent filter(JComponent found) {
        if (found != null && ignoredContainer != null) {
            Component current = found;
            while (current != null) {
                if (ignoredContainer.equals(current.getName())) {
                    found = null;
                    break;
                }
                current = current.getParent();
            }
        }
        return found;
    }


    private JComboBox findParentCombo(Container comp) {
        if (comp == null) {
            return null;
        }
        else if (comp instanceof JComboBox) {
            return (JComboBox)comp;
        }
        return findParentCombo(comp.getParent());
    }
}
