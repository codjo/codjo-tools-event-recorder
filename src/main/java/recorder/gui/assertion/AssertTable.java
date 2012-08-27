/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import javax.swing.JTable;
import recorder.gui.action.GuiAction;
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
/**
 * Permet de créer un tag assertTable.
 */
class AssertTable extends AbstractAssert {
    static final String ID = "assertion.table";
    private AssertContext context;

    AssertTable(AssertContext assertContext) {
        super(ID, COMPONENT_ASSERT);
        putValue(GuiAction.LABEL, "AssertTable");
        putValue(GuiAction.TOOLTIP,
            "Ajoute un 'assertion' sur la valeur d'une cellule d'une JTable");
        this.context = assertContext;
    }

    public void execute() {
        if (!(context.getSource() instanceof JTable)) {
            return;
        }
        JTable table = (JTable)context.getSource();
        int rowAtPoint = table.rowAtPoint(context.getPoint());
        int colAtPoint = table.columnAtPoint(context.getPoint());

        AttributeList attributes = new AttributeList();
        attributes.put("name", table.getName());
        attributes.put("expected", table.getValueAt(rowAtPoint, colAtPoint));
        attributes.put("row", new Integer(rowAtPoint));
        attributes.put("column", new Integer(colAtPoint));

        context.postAssert(new DefaultStatement("assertTable", attributes));
    }


    public void update() {
        this.setEnabled((context.getSource() instanceof JTable));
    }
}
