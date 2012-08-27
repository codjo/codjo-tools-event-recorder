/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.script;
import javax.swing.JTree;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import recorder.result.Statement;
import recorder.result.StatementList;
/**
 * Représentation sous forme d'arbre d'un script.
 */
public class ScriptGui extends JTree {
    private StatementTreeModel myModel;

    public ScriptGui() {
        myModel = new StatementTreeModel(new StatementList());
        setModel(myModel);
        setName("script.tree.display");
        setRootVisible(false);
    }

    public String convertValueToText(Object value, boolean selected, boolean expanded,
        boolean leaf, int row, boolean hasFocus) {
        if (value instanceof StatementList) {
            return "group";
        }
        if (value instanceof Statement) {
            System.identityHashCode(value);
            return ((Statement)value).toXml();
        }
        return super.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
    }


    public void display(StatementList statementList) {
        myModel.setRoot(statementList);
        if (statementList.lastResult() != null) {
            this.scrollPathToVisible(new TreePath(
                    new Object[] {statementList, statementList.lastResult()}));
        }
    }

    /**
     * TreeModel mappant un StatementList.
     */
    private static class StatementTreeModel implements TreeModel {
        private StatementList root;
        private EventListenerList listenerList = new EventListenerList();

        StatementTreeModel(StatementList root) {
            this.root = root;
        }

        public Object getRoot() {
            return root;
        }


        public void setRoot(StatementList root) {
            this.root = root;
            fireTreeStructureChanged(root);
        }


        private String toXml(Object obj) {
            if (obj instanceof StatementList) {
                return "LIST";
            }

            return ((Statement)obj).toXml() + " " + System.identityHashCode(obj);
        }


        public Object getChild(Object parent, int index) {
            Statement child = ((StatementList)parent).get(index);
            return child;
        }


        public int getChildCount(Object parent) {
            int size = ((StatementList)parent).size();
            return size;
        }


        public boolean isLeaf(Object node) {
            return !(node instanceof StatementList);
        }


        public void valueForPathChanged(TreePath path, Object newValue) {
            throw new IllegalStateException("Impossible");
        }


        public int getIndexOfChild(Object parent, Object child) {
            int index = findIndex(parent, child);
            return index;
        }


        private int findIndex(Object parent, Object child) {
            StatementList parentList = (StatementList)parent;
            for (int i = 0; i < parentList.size(); i++) {
                if (parentList.get(i) == child) {
                    return i;
                }
            }
            return -1;
        }


        public void addTreeModelListener(TreeModelListener listener) {
            listenerList.add(TreeModelListener.class, listener);
        }


        public void removeTreeModelListener(TreeModelListener listener) {
            listenerList.remove(TreeModelListener.class, listener);
        }


        protected void fireTreeStructureChanged(Statement oldRoot) {
            Object[] listeners = listenerList.getListenerList();
            TreeModelEvent event = new TreeModelEvent(this, new Object[] {oldRoot});

            for (int i = listeners.length - 2; i >= 0; i -= 2) {
                ((TreeModelListener)listeners[i + 1]).treeStructureChanged(event);
            }
        }
    }
}
