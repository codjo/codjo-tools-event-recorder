/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.event;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.tree.TreePath;
import junit.framework.TestCase;
import recorder.component.GuiComponentFactory;
/**

 */
public class EventRecognizerTest extends TestCase {
    private EventRecognizerManager recognizerManager;


    public void test_notJComponent() throws Exception {
        assertNull(recognizerManager.toGuiEvent(
              new KeyEvent(new JFrame(), KeyEvent.KEY_RELEASED, 0, 0, 0, 'a')));
    }


    public void test_notNamed() throws Exception {
        JTextField field = new JTextField();
        KeyEvent awtEvent = new KeyEvent(field, KeyEvent.KEY_RELEASED, 0, 0, 0, 'a');

        assertNull(recognizerManager.toGuiEvent(awtEvent));

        field.setName("name");
        assertNotNull(recognizerManager.toGuiEvent(awtEvent));
    }


    public void test_mouseEvent_menu() throws Exception {
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        file.add(open);

        assertNull(recognizerManager.toGuiEvent(newMouse(file, MouseEvent.MOUSE_ENTERED)));
        assertNotNull("Event pressed sur file est reconnue",
                      recognizerManager.toGuiEvent(newMouse(file, MouseEvent.MOUSE_PRESSED)));
        assertNotNull("Event pressed sur open est reconnue",
                      recognizerManager.toGuiEvent(newMouse(open, MouseEvent.MOUSE_PRESSED)));
    }


    public void test_mouseEvent_button() throws Exception {
        JButton button = new JButton();
        button.setName("my.button");

        assertNull(recognizerManager.toGuiEvent(newMouse(button, MouseEvent.MOUSE_ENTERED)));
        assertNull(recognizerManager.toGuiEvent(newMouse(button, MouseEvent.MOUSE_PRESSED)));
        GuiEvent event =
              recognizerManager.toGuiEvent(newMouse(button, MouseEvent.MOUSE_RELEASED));
        assertNotNull("Event pressed est reconnue", event);
        assertEquals(GuiEventType.BUTTON_CLICK, event.getType());
        assertEquals(button, event.getSource().getSwingComponent());
    }


    public void test_mouseEvent_checkbox() throws Exception {
        JCheckBox checkBox = new JCheckBox();
        checkBox.setName("my.checkBox");
        checkBox.setSelected(false);

        assertNull(recognizerManager.toGuiEvent(newMouse(checkBox,
                                                         MouseEvent.MOUSE_ENTERED)));
        assertNull(recognizerManager.toGuiEvent(newMouse(checkBox,
                                                         MouseEvent.MOUSE_PRESSED)));
        assertNull(recognizerManager.toGuiEvent(newMouse(checkBox,
                                                         MouseEvent.MOUSE_RELEASED)));
        GuiEvent event =
              recognizerManager.toGuiEvent(newMouse(checkBox, MouseEvent.MOUSE_CLICKED));
        assertNotNull("Event clicked est reconnue", event);
        assertEquals(GuiEventType.CHECKBOX_CLICK, event.getType());
        assertEquals(checkBox, event.getSource().getSwingComponent());
        assertEquals(Boolean.FALSE, event.getValue());
    }


    public void test_mouseEvent_tree() throws Exception {
        MockJTree tree = new MockJTree();
        TreePath path = new TreePath(new Object[]{"rootFolder"});
        tree.mockQueryOn(path, false);
        tree.setName("my.tree");

        assertNull(recognizerManager.toGuiEvent(newMouse(tree, MouseEvent.MOUSE_ENTERED)));
        assertNull(recognizerManager.toGuiEvent(newMouse(tree, MouseEvent.MOUSE_RELEASED)));

        GuiEvent pressedEvent =
              recognizerManager.toGuiEvent(newMouse(tree, MouseEvent.MOUSE_PRESSED));
        assertNotNull("Event pressed est reconnue", pressedEvent);

        assertEquals(GuiEventType.TREE_PRE_CLICK, pressedEvent.getType());
        assertEquals(tree, pressedEvent.getSource().getSwingComponent());
        assertEquals(new TreeEventData(path, false, false), pressedEvent.getValue());

        tree.mockQueryOn(path, true);
        GuiEvent clickEvent =
              recognizerManager.toGuiEvent(newMouse(tree, MouseEvent.MOUSE_CLICKED));
        assertNotNull("Event clicked est reconnue", clickEvent);

        assertEquals(GuiEventType.TREE_CLICK, clickEvent.getType());
        assertEquals(tree, clickEvent.getSource().getSwingComponent());
        assertEquals(new TreeEventData(path, true, false), clickEvent.getValue());
    }


    public void test_mouseEvent_table() throws Exception {
        JTable table =
              new JTable() {
                  @Override
                  public int rowAtPoint(Point point) {
                      if (point.x == 0 && point.y == 0) {
                          return 3;
                      }
                      else {
                          return -1;
                      }
                  }
              };
        table.setName("my.table");

        assertNull(recognizerManager.toGuiEvent(newMouse(table, MouseEvent.MOUSE_ENTERED)));
        assertNull(recognizerManager.toGuiEvent(newMouse(table, MouseEvent.MOUSE_PRESSED)));
        GuiEvent event =
              recognizerManager.toGuiEvent(newMouse(table, MouseEvent.MOUSE_RELEASED));
        assertNotNull("Event est reconnue", event);

        assertEquals(new GuiEvent(GuiEventType.TABLE_CLICK,
                                  GuiComponentFactory.newGuiComponent(table), Integer.decode("3")), event);
    }


    public void test_mouseEvent_list() throws Exception {
        JList list =
              new JList() {
                  @Override
                  public int locationToIndex(Point point) {
                      if (point.x == 0 && point.y == 0) {
                          return 3;
                      }
                      else {
                          return -1;
                      }
                  }
              };
        list.setName("my.list");

        assertNull(recognizerManager.toGuiEvent(newMouse(list, MouseEvent.MOUSE_ENTERED)));
        assertNull(recognizerManager.toGuiEvent(newMouse(list, MouseEvent.MOUSE_PRESSED)));
        GuiEvent event =
              recognizerManager.toGuiEvent(newMouse(list, MouseEvent.MOUSE_RELEASED));
        assertNotNull("Event est reconnue", event);

        assertEquals(new GuiEvent(GuiEventType.LIST_CLICK,
                                  GuiComponentFactory.newGuiComponent(list), Integer.decode("3")), event);
    }


    @Override
    protected void setUp() throws Exception {
        recognizerManager = new EventRecognizerManager(new GuiComponentFactory());
    }


    private MouseEvent newMouse(Component source, int id) {
        return new MouseEvent(source, id, 0, MouseEvent.BUTTON1_MASK, 0, 0, 0, false);
    }


    private static class MockJTree extends JTree {
        TreePath rootFolderPath;
        private boolean mockCollapsed;


        void mockQueryOn(TreePath path, boolean collapsed) {
            this.rootFolderPath = path;
            this.mockCollapsed = collapsed;
        }


        @Override
        public TreePath getClosestPathForLocation(int x, int y) {
            return rootFolderPath;
        }


        @Override
        public boolean isCollapsed(TreePath path) {
            return rootFolderPath == path ? mockCollapsed : !mockCollapsed;
        }
    }
}
