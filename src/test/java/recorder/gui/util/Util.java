/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.util;
import java.awt.Component;
import java.awt.Container;
import javax.swing.AbstractButton;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
/**
 * Ensemble de classe utilitaire pour les tests IHM.
 */
public final class Util {
    private Util() {
    }


    public static String uiDisplayedContent(JTree tree) {
        StringBuffer result = new StringBuffer();
        int rowCount = tree.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            TreePath path = tree.getPathForRow(i);
            result.append(pathToString(tree, path));
            if (i + 1 < rowCount) {
                result.append('\n');
//                result.append('\n');
            }
        }
        return result.toString();
    }


    private static String pathToString(JTree tree, TreePath treePath) {
        StringBuffer buffer = new StringBuffer("[");

        Object[] path = treePath.getPath();

        for (int i = 0; i < path.length; i++) {
            buffer.append(tree.convertValueToText(path[i], false, true, true, i, false));
            if (i + 1 < path.length) {
                buffer.append(", ");
            }
        }
        return buffer.append("]").toString();
    }


    public static Component findByName(String name, Component comp) {
        if (comp == null || name.equals(comp.getName())) {
            return comp;
        }

        if (comp instanceof Container) {
            Container container = (Container)comp;
            for (int idx = 0; idx < container.getComponentCount(); idx++) {
                Component result = findByName(name, container.getComponent(idx));
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }


    public static AbstractButton findButtonByLabel(String label, Component comp) {
        if (comp == null) {
            return null;
        }

        if (comp instanceof AbstractButton) {
            AbstractButton button = (AbstractButton)comp;
            if (label.equals(button.getText())) {
                return button;
            }
            else {
                return null;
            }
        }

        if (comp instanceof Container) {
            Container container = (Container)comp;
            for (int idx = 0; idx < container.getComponentCount(); idx++) {
                AbstractButton result =
                      findButtonByLabel(label, container.getComponent(idx));
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }
}
