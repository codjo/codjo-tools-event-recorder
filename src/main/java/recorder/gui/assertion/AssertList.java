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
class AssertList extends AbstractAssert {
    static final String ID = "assertion.selected";
    private AssertContext context;


    AssertList(AssertContext assertContext) {
        super(ID, COMPONENT_ASSERT);
        putValue(GuiAction.LABEL, "AssertList");
        putValue(GuiAction.TOOLTIP, resourceBundle.getString("assert.list-row"));

        this.context = assertContext;
    }


    public void execute() {
        if (!canHandleComponentInContext()) {
            return;
        }
        Handler handler = getHandler();
        Component component = context.getSource();
        int rowAtPoint = handler.determineRowAtPoint(component);

        AttributeList attributes = new AttributeList();
        attributes.put("name", component.getName());
        attributes.put("expected", handler.getValue(component, rowAtPoint));
        attributes.put("row", rowAtPoint);

        context.postAssert(new DefaultStatement("assertList", attributes));
    }


    @Override
    public void update() {
        this.setEnabled(canHandleComponentInContext());
    }


    private boolean canHandleComponentInContext() {
        return getHandler() != null;
    }


    private Handler getHandler() {
        if (context.getSource() instanceof JList) {
            return new ListHandler();
        }
        return null;
    }


    //------------------------------------------------------------------------------------------------------------------
    // Handlers for list components : JTable and JList
    //------------------------------------------------------------------------------------------------------------------
    private interface Handler {
        Object getValue(Component comp, int rowAtPoint);


        int determineRowAtPoint(Component comp);
    }

    private class ListHandler implements Handler {
        public Object getValue(Component comp, int rowAtPoint) {
            return ((JList)comp).getModel().getElementAt(rowAtPoint);
        }


        public int determineRowAtPoint(Component comp) {
            return ((JList)comp).locationToIndex(context.getPoint());
        }
    }
}
