/*
 * codjo (Prototype)
 * =================
 *
 *    Copyright (C) 2005, 2012 by codjo.net
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *    implied. See the License for the specific language governing permissions
 *    and limitations under the License.
 */
package recorder.event;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.tree.TreePath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import recorder.component.GuiComponentFactory;
/**

 */
public class EventRecognizerTest {
    private EventRecognizerManager recognizerManager;


    @Test
    public void test_notJComponent() throws Exception {
        Assert.assertNull(recognizerManager.toGuiEvent(
              new KeyEvent(new JFrame(), KeyEvent.KEY_RELEASED, 0, 0, 0, 'a')));
    }


    @Test
    public void test_notNamed() throws Exception {
        JTextField field = new JTextField();
        KeyEvent awtEvent = new KeyEvent(field, KeyEvent.KEY_RELEASED, 0, 0, 0, 'a');

        Assert.assertNull(recognizerManager.toGuiEvent(awtEvent));

        field.setName("name");
        Assert.assertNotNull(recognizerManager.toGuiEvent(awtEvent));
    }


    @Test
    public void test_mouseEvent_menu() throws Exception {
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        file.add(open);

        Assert.assertNull(recognizerManager.toGuiEvent(newMouse(file, MouseEvent.MOUSE_ENTERED)));
        Assert.assertNotNull("Event pressed sur file est reconnue",
                             recognizerManager.toGuiEvent(newMouse(file, MouseEvent.MOUSE_PRESSED)));
        Assert.assertNotNull("Event pressed sur open est reconnue",
                             recognizerManager.toGuiEvent(newMouse(open, MouseEvent.MOUSE_PRESSED)));
    }


    @Test
    public void test_mouseEvent_button() throws Exception {
        JButton button = new JButton();
        button.setName("my.button");

        Assert.assertNull(recognizerManager.toGuiEvent(newMouse(button, MouseEvent.MOUSE_ENTERED)));
        Assert.assertNull(recognizerManager.toGuiEvent(newMouse(button, MouseEvent.MOUSE_PRESSED)));
        GuiEvent event =
              recognizerManager.toGuiEvent(newMouse(button, MouseEvent.MOUSE_RELEASED));
        Assert.assertNotNull("Event pressed est reconnue", event);
        Assert.assertEquals(GuiEventType.BUTTON_CLICK, event.getType());
        Assert.assertEquals(button, event.getSource().getSwingComponent());
    }


    @Test
    public void test_mouseEvent_checkbox() throws Exception {
        JCheckBox checkBox = new JCheckBox();
        checkBox.setName("my.checkBox");
        checkBox.setSelected(false);

        Assert.assertNull(recognizerManager.toGuiEvent(newMouse(checkBox,
                                                                MouseEvent.MOUSE_ENTERED)));
        Assert.assertNull(recognizerManager.toGuiEvent(newMouse(checkBox,
                                                                MouseEvent.MOUSE_PRESSED)));
        Assert.assertNull(recognizerManager.toGuiEvent(newMouse(checkBox,
                                                                MouseEvent.MOUSE_RELEASED)));
        GuiEvent event =
              recognizerManager.toGuiEvent(newMouse(checkBox, MouseEvent.MOUSE_CLICKED));
        Assert.assertNotNull("Event clicked est reconnue", event);
        Assert.assertEquals(GuiEventType.CHECKBOX_CLICK, event.getType());
        Assert.assertEquals(checkBox, event.getSource().getSwingComponent());
        Assert.assertEquals(Boolean.FALSE, event.getValue());
    }


    @Test
    public void test_mouseEvent_tree() throws Exception {
        MockJTree tree = new MockJTree();
        TreePath path = new TreePath(new Object[]{"rootFolder"});
        tree.mockQueryOn(path, false);
        tree.setName("my.tree");

        Assert.assertNull(recognizerManager.toGuiEvent(newMouse(tree, MouseEvent.MOUSE_ENTERED)));
        Assert.assertNull(recognizerManager.toGuiEvent(newMouse(tree, MouseEvent.MOUSE_RELEASED)));

        GuiEvent pressedEvent =
              recognizerManager.toGuiEvent(newMouse(tree, MouseEvent.MOUSE_PRESSED));
        Assert.assertNotNull("Event pressed est reconnue", pressedEvent);

        Assert.assertEquals(GuiEventType.TREE_PRE_CLICK, pressedEvent.getType());
        Assert.assertEquals(tree, pressedEvent.getSource().getSwingComponent());
        Assert.assertEquals(new TreeEventData(path, false, false), pressedEvent.getValue());

        tree.mockQueryOn(path, true);
        GuiEvent clickEvent =
              recognizerManager.toGuiEvent(newMouse(tree, MouseEvent.MOUSE_CLICKED));
        Assert.assertNotNull("Event clicked est reconnue", clickEvent);

        Assert.assertEquals(GuiEventType.TREE_CLICK, clickEvent.getType());
        Assert.assertEquals(tree, clickEvent.getSource().getSwingComponent());
        Assert.assertEquals(new TreeEventData(path, true, false), clickEvent.getValue());
    }


    @Test
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

        Assert.assertNull(recognizerManager.toGuiEvent(newMouse(table, MouseEvent.MOUSE_ENTERED)));
        Assert.assertNull(recognizerManager.toGuiEvent(newMouse(table, MouseEvent.MOUSE_PRESSED)));
        GuiEvent event =
              recognizerManager.toGuiEvent(newMouse(table, MouseEvent.MOUSE_RELEASED));
        Assert.assertNotNull("Event est reconnue", event);

        Assert.assertEquals(new GuiEvent(GuiEventType.TABLE_CLICK,
                                         GuiComponentFactory.newGuiComponent(table), Integer.decode("3")), event);
    }


    @Test
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

        Assert.assertNull(recognizerManager.toGuiEvent(newMouse(list, MouseEvent.MOUSE_ENTERED)));
        Assert.assertNull(recognizerManager.toGuiEvent(newMouse(list, MouseEvent.MOUSE_PRESSED)));
        GuiEvent event =
              recognizerManager.toGuiEvent(newMouse(list, MouseEvent.MOUSE_RELEASED));
        Assert.assertNotNull("Event est reconnue", event);

        Assert.assertEquals(new GuiEvent(GuiEventType.LIST_CLICK,
                                         GuiComponentFactory.newGuiComponent(list), Integer.decode("3")), event);
    }


    @Before
    public void setUp() throws Exception {
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
