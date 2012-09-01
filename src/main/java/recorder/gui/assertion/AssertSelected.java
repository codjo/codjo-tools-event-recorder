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
class AssertSelected extends AbstractAssert {
    static final String ID = "assertion.selected";
    private AssertContext context;


    AssertSelected(AssertContext assertContext) {
        super(ID, COMPONENT_ASSERT);
        putValue(GuiAction.LABEL, "AssertSelected");
        putValue(GuiAction.TOOLTIP, "Ajoute un 'assertion' sur la sélection d'une ligne sur une JTable ou JList");

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
        attributes.put("row", rowAtPoint);
        if (!handler.isRowSelected(component, rowAtPoint)) {
            attributes.put("expected", "false");
        }

        context.postAssert(new DefaultStatement("assertSelected", attributes));
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
        return null;
    }


    //------------------------------------------------------------------------------------------------------------------
    // Handlers for list components : JTable and JList
    //------------------------------------------------------------------------------------------------------------------
    private interface Handler {
        boolean isRowSelected(Component comp, int rowAtPoint);


        int determineRowAtPoint(Component comp);
    }

    private class TableHandler implements Handler {
        public boolean isRowSelected(Component comp, int rowAtPoint) {
            return ((JTable)comp).isRowSelected(rowAtPoint);
        }


        public int determineRowAtPoint(Component comp) {
            return ((JTable)comp).rowAtPoint(context.getPoint());
        }
    }

    private class ListHandler implements Handler {
        public boolean isRowSelected(Component comp, int rowAtPoint) {
            return ((JList)comp).getSelectionModel().isSelectedIndex(rowAtPoint);
        }


        public int determineRowAtPoint(Component comp) {
            return ((JList)comp).locationToIndex(context.getPoint());
        }
    }
}
