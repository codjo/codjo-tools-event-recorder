/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.event;
import javax.swing.tree.TreePath;
/**
 * Représente les données extraite .
 */
public class TreeEventData {
    private TreePath path;
    private boolean collapsed;
    private boolean selected;

    public TreeEventData(TreePath path, boolean collapsed, boolean selected) {
        if (path == null) {
            throw new NullPointerException();
        }
        this.path = path;
        this.collapsed = collapsed;
        this.selected = selected;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TreeEventData)) {
            return false;
        }

        final TreeEventData data = (TreeEventData)obj;
        return selected == data.selected && collapsed == data.collapsed
        && path.equals(data.path);
    }


    public int hashCode() {
        return path.hashCode();
    }


    public TreePath getPath() {
        return path;
    }


    public boolean isSelected() {
        return selected;
    }
}
