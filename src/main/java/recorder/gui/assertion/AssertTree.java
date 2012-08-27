/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import recorder.gui.action.GuiAction;
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
/**
 * Permet de créer un tag assertTree.
 */
class AssertTree extends AbstractAssert {
    static final String ID = "assertion.tree";
    private AssertContext context;

    AssertTree(AssertContext assertContext) {
        super(ID, COMPONENT_ASSERT);
        putValue(GuiAction.LABEL, "AssertTree");
        putValue(GuiAction.TOOLTIP,
            "Ajoute un 'assertion' sur l'existance d'un noeud dans un arbre");
        this.context = assertContext;
    }

    public void execute() {
        if (!isAllowed()) {
            return;
        }
        JTree tree = (JTree)context.getSource();
        TreePath path =
            tree.getClosestPathForLocation(context.getPoint().x, context.getPoint().y);

        AttributeList attributes = new AttributeList();
        attributes.put("name", tree.getName());
        attributes.put("path", pathToString(tree, path));
        attributes.put("exists", "true");

        context.postAssert(new DefaultStatement("assertTree", attributes));
    }


    public void update() {
        this.setEnabled(isAllowed());
    }


    private boolean isAllowed() {
        if (!(context.getSource() instanceof JTree)) {
            return false;
        }
        JTree tree = (JTree)context.getSource();
        TreePath path =
            tree.getClosestPathForLocation(context.getPoint().x, context.getPoint().y);

        return path != null;
    }


    private static String pathToString(JTree tree, TreePath treePath) {
        StringBuffer buffer = new StringBuffer();

        Object[] path = treePath.getPath();

        for (int i = 0; i < path.length; i++) {
            buffer.append(tree.convertValueToText(path[i], false, true, true, i, false));
            if (i + 1 < path.length) {
                buffer.append(":");
            }
        }
        return buffer.toString();
    }
}
