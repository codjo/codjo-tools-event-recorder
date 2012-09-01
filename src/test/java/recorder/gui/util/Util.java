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
package recorder.gui.util;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.TreePath;
/**
 * Utility class for GUI testing.
 */
public final class Util {
    private Util() {
    }


    public static String uiDisplayedContent(JTree tree) {
        StringBuilder result = new StringBuilder();
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
        StringBuilder buffer = new StringBuilder("[");

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
