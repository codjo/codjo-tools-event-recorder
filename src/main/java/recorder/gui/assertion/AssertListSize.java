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
/**
 * Creates tag assertSelected.
 */
class AssertListSize extends AbstractAssert {
    static final String ID = "assertion.size";
    private AssertContext context;


    AssertListSize(AssertContext assertContext) {
        super(ID, COMPONENT_ASSERT);
        putValue(GuiAction.LABEL, "AssertListSize");
        putValue(GuiAction.TOOLTIP, resourceBundle.getString("assert.list-size"));

        this.context = assertContext;
    }


    public void execute() {
        if (!canHandleComponentInContext()) {
            return;
        }
        Handler handler = getHandler();
        Component component = context.getSource();
        int size = handler.determineSize(component);

        AttributeList attributes = new AttributeList();
        attributes.put("name", component.getName());
        attributes.put("expected", size);

        context.postAssert(new DefaultStatement("assertListSize", attributes));
    }


    @Override
    public void update() {
        this.setEnabled(canHandleComponentInContext());
    }


    private boolean canHandleComponentInContext() {
        return getHandler() != null;
    }


    private Handler getHandler() {
        if (context.getSource() instanceof JTable) {
            return new TableHandler();
        }
        if (context.getSource() instanceof JList) {
            return new ListHandler();
        }
        if (context.getSource() instanceof JComboBox) {
            return new ComboHandler();
        }
        return null;
    }


    //------------------------------------------------------------------------------------------------------------------
    // Handlers for list components : JTable and JList and Combo
    //------------------------------------------------------------------------------------------------------------------
    private interface Handler {
        int determineSize(Component comp);
    }

    private class TableHandler implements Handler {
        public int determineSize(Component comp) {
            return ((JTable)comp).getRowCount();
        }
    }

    private class ListHandler implements Handler {
        public int determineSize(Component comp) {
            return ((JList)comp).getModel().getSize();
        }
    }

    private class ComboHandler implements Handler {
        public int determineSize(Component comp) {
            return ((JComboBox)comp).getModel().getSize();
        }
    }
}
