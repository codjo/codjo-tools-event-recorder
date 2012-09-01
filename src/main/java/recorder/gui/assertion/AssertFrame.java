/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import java.awt.*;
import javax.swing.*;
import recorder.gui.action.GuiAction;
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
class AssertFrame extends AbstractAssert {
    static final String ID = "assertion.frame";
    private AssertContext context;


    AssertFrame(AssertContext assertContext) {
        super(ID, GLOBAL_ASSERT);
        putValue(GuiAction.LABEL, "AssertFrame");
        putValue(GuiAction.TOOLTIP, "Ajoute un 'assertion' sur le titre de la fenÍtre");
        this.context = assertContext;
    }


    public void execute() {
        String title = findWindowTitle(context.getSource());

        if (title == null) {
            return;
        }

        context.postAssert(new DefaultStatement("assertFrame", AttributeList.singleton("title", title)));
    }


    @Override
    public void update() {
        putValue(GuiAction.LABEL, "AssertFrame titre='" + findWindowTitle(context.getSource()) + "'");
    }


    private String findWindowTitle(Component source) {
        if (source == null) {
            return null;
        }
        if (source instanceof JInternalFrame) {
            return ((JInternalFrame)source).getTitle();
        }
        if (source instanceof JFrame) {
            return ((JFrame)source).getTitle();
        }
        if (source instanceof JDialog) {
            return ((JDialog)source).getTitle();
        }
        return findWindowTitle(source.getParent());
    }
}
