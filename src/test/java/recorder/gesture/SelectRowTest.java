/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gesture;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import junit.framework.TestCase;
import recorder.component.GuiComponentFactory;
import recorder.event.GuiEvent;
import recorder.event.GuiEventList;
import recorder.event.GuiEventType;
import recorder.event.TreeEventData;
import recorder.result.StatementList;
/**
 * Classe de test de {@link SelectRow}.
 */
public class SelectRowTest extends TestCase {
    private StatementList result;
    private GuiEventList list;
    private SelectRow selectRowGesture;

    public void test_receive_badEvent() throws Exception {
        list.addEvent(new GuiEvent(GuiEventType.BUTTON_CLICK,
                GuiComponentFactory.newGuiComponent(new JButton())));

        selectRowGesture.receive(list, result);

        assertEquals("Click n'est pas consommé", 1, list.size());
    }


    public void test_receive_table() throws Exception {
        list.addEvent(newTableClickRowEvent("table.name", "1"));

        selectRowGesture.receive(list, result);

        assertEquals("Click est consommé", 0, list.size());
        assertEquals("<select name=\"table.name\" row=\"1\"/>", result.toXml());
    }


    public void test_receive_list() throws Exception {
        list.addEvent(newListClickRowEvent("list.name", "1"));

        selectRowGesture.receive(list, result);

        assertEquals("Click est consommé", 0, list.size());
        assertEquals("<select name=\"list.name\" row=\"1\"/>", result.toXml());
    }


    public void test_receive_tree() throws Exception {
        list.addEvent(newTreeClickRowEvent("tree.name", new TreePath("node"), true));

        selectRowGesture.receive(list, result);

        assertEquals("Click est consommé", 0, list.size());
        assertEquals("<select name=\"tree.name\" path=\"node\"/>", result.toXml());
    }


    public void test_receive_tree_notSelected() throws Exception {
        list.addEvent(newTreeClickRowEvent("tree.name", new TreePath("node"), false));

        selectRowGesture.receive(list, result);

        assertEquals("Click n'est pas consommé", 1, list.size());
        assertEquals("", result.toXml());
    }


    public void test_receive_cumulative() throws Exception {
        JTable source = buildTable("table.name");

        list.addEvent(newClickRowEvent(source, "1"));
        list.addEvent(newClickRowEvent(source, "1"));
        list.addEvent(newClickRowEvent(source, "2"));

        // 2 click sur la même ligne sont cumulatif (même selection)
        selectRowGesture.receive(list, result);
        selectRowGesture.receive(list, result);
        selectRowGesture.receive(list, result);

        assertEquals("Click est consommé", 0, list.size());
        assertEquals("<select name=\"table.name\" row=\"1\"/>\n"
            + "<select name=\"table.name\" row=\"2\"/>", result.toXml());
    }


    protected void setUp() throws Exception {
        result = new StatementList();
        list = new GuiEventList();
        selectRowGesture = new SelectRow();
    }


    private GuiEvent newTableClickRowEvent(String name, String idx) {
        return new GuiEvent(GuiEventType.TABLE_CLICK,
            GuiComponentFactory.newGuiComponent(buildTable(name)), Integer.decode(idx));
    }


    private GuiEvent newTreeClickRowEvent(String name, TreePath path, boolean selected) {
        return new GuiEvent(GuiEventType.TREE_CLICK,
            GuiComponentFactory.newGuiComponent(buildTree(name)),
            new TreeEventData(path, false, selected));
    }


    private GuiEvent newListClickRowEvent(String name, String idx) {
        return new GuiEvent(GuiEventType.LIST_CLICK,
            GuiComponentFactory.newGuiComponent(buildList(name)), Integer.decode(idx));
    }


    private GuiEvent newClickRowEvent(JComponent source, String idx) {
        return new GuiEvent(GuiEventType.TABLE_CLICK,
            GuiComponentFactory.newGuiComponent(source), Integer.decode(idx));
    }


    private JTable buildTable(String name) {
        JTable table = new JTable();
        table.setName(name);
        return table;
    }


    private JList buildList(String name) {
        JList list = new JList();
        list.setName(name);
        return list;
    }


    private JTree buildTree(String name) {
        JTree tree = new JTree();
        tree.setName(name);
        return tree;
    }
}
