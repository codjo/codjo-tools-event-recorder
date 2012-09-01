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
package recorder.gui.assertion;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import recorder.Recorder;
import recorder.component.GuiComponentFactory;
import static org.junit.Assert.*;
/**

 */
public class AssertManagerTest {
    private AssertManager manager;
    private MockDialogManager mockDialogManager;
    private MouseEvent validEvent;
    private GuiComponentFactory factory;


    @Test
    public void test_isAssertTrigger_valid() {
        Assert.assertTrue("Click Valide", manager.isAssertTrigger(validEvent));
    }


    @Test
    public void test_isAssertTrigger_badMouseEvent() {
        MouseEvent notClick =
              newEvent(MouseEvent.MOUSE_ENTERED,
                       MouseEvent.CTRL_MASK | MouseEvent.BUTTON3_MASK);
        Assert.assertFalse("Pas un click", manager.isAssertTrigger(notClick));

        MouseEvent badModifierEvent =
              newEvent(MouseEvent.MOUSE_ENTERED, MouseEvent.BUTTON3_MASK);
        Assert.assertFalse("Pas le bon modifier", manager.isAssertTrigger(badModifierEvent));
    }


    @Test
    public void test_isAssertTrigger_bad() {
        Assert.assertFalse("Pas un click", manager.isAssertTrigger(null));

        Assert.assertFalse("Pas le bon modifier",
                           manager.isAssertTrigger(new KeyEvent(new JTextField(), 0, 0, 0, 0)));
    }


    @Test
    public void test_popupTriggered() {
        manager.eventDispatched(validEvent);

        Assert.assertEquals("Popup est affiché", 1, mockDialogManager.newPopupMenuCalled);
        MockPopupMenu popupMenu = mockDialogManager.popupMenu;
        Assert.assertEquals("A la Bonne position", validEvent.getPoint(), popupMenu.location);

        Assert.assertSame("Avec le Bon invoker", validEvent.getSource(), popupMenu.invoker);
    }


    /**
     * Test que le popup s'affiche correctement dans le cas particulier des invokers ayant des contentPane (JDialog , Frame). Il ne faut pas utiliser la source pour le popup mais le composant retourné par le finder.
     */
    @Test
    public void test_popupTriggered_invokerWithContent() {
        JDialog source =
              new JDialog() {
                  @Override
                  public Component findComponentAt(Point point) {
                      return this.getContentPane();
                  }
              };

        validEvent = newValidEventOn(source);
        manager.eventDispatched(validEvent);

        Assert.assertEquals("Popup est affiché", 1, mockDialogManager.newPopupMenuCalled);
        MockPopupMenu popupMenu = mockDialogManager.popupMenu;
        Assert.assertEquals("A la Bonne position", validEvent.getPoint(), popupMenu.location);

        Assert.assertSame("Avec le Bon invoker", source.getContentPane(), popupMenu.invoker);
    }


    @Test
    public void test_popupTriggered_badsource() {
        factory.setIgnoredContainer("containerName");
        ((JComponent)validEvent.getSource()).setName("containerName");

        manager.eventDispatched(validEvent);

        Assert.assertEquals("Popup n'est pas affiché (car composant ignoré)", 0,
                            mockDialogManager.newPopupMenuCalled);
    }


    @Test
    public void test_popupTriggered_bad() {
        MouseEvent bad = newEvent(MouseEvent.MOUSE_PRESSED, MouseEvent.BUTTON3_MASK);

        manager.eventDispatched(bad);

        Assert.assertEquals("Le Popup n'est pas affiché", 0, mockDialogManager.newPopupMenuCalled);
    }


    @Test
    public void test_actionsList() {
        manager.eventDispatched(validEvent);

        // Assert le contexte
        Assert.assertSame(validEvent.getSource(), manager.getAssertContext().getSource());
        Assert.assertEquals(validEvent.getPoint(), manager.getAssertContext().getPoint());

        // Assert les actions
        List<JMenuItem> itemList = mockDialogManager.popupMenu.itemList;
        assertContainAction(AssertFrame.ID, itemList);
        assertContainAction(AssertSelected.ID, itemList);
        assertContainAction(AssertListSize.ID, itemList);
        assertContainAction(AssertList.ID, itemList);
        assertContainAction(AssertTable.ID, itemList);
        assertContainAction(AssertTree.ID, itemList);
        assertContainAction(AssertValue.ID, itemList);
        Assert.assertEquals("Action d'assertion sont affiché", 6, itemList.size());
    }


    @Test
    public void test_source_jcombobox() {
        JComboBox comboBox = new JComboBox();
        MouseEvent eventUponCombo =
              newEvent(MouseEvent.MOUSE_RELEASED, MouseEvent.BUTTON2_MASK,
                       comboBox.getComponent(0));

        manager.eventDispatched(eventUponCombo);

        Assert.assertSame(comboBox, manager.getAssertContext().getSource());
    }


    @Test
    public void test_actionsList_noName() {
        validEvent.getComponent().setName(null);

        manager.eventDispatched(validEvent);

        List<JMenuItem> itemList = mockDialogManager.popupMenu.itemList;
        Assert.assertTrue("Action assertFrame est global",
                          findMenuItem(AssertFrame.ID, itemList).isEnabled());
        Assert.assertFalse("Action assertValue necessite un composant nommé",
                           findMenuItem(AssertValue.ID, itemList).isEnabled());
    }


    @Test
    public void test_actionsList_withName() {
        validEvent.getComponent().setName("bobo");

        manager.eventDispatched(validEvent);

        List<JMenuItem> itemList = mockDialogManager.popupMenu.itemList;
        Assert.assertTrue("Action assertFrame est global",
                          findMenuItem(AssertFrame.ID, itemList).isEnabled());
        Assert.assertTrue("Action assertValue necessite un composant nommé",
                          findMenuItem(AssertValue.ID, itemList).isEnabled());
    }


    @Before
    public void setUp() throws Exception {
        mockDialogManager = new MockDialogManager();
        factory = new GuiComponentFactory();
        Recorder recorder = new Recorder(factory);
        manager = new AssertManager(recorder, mockDialogManager);
        this.validEvent = newValidEventOn(new JTextField());
    }


    private MouseEvent newEvent(int eventType, int modifiers) {
        return newEvent(eventType, modifiers, new JTextField());
    }


    private MouseEvent newValidEventOn(Component source) {
        return newEvent(MouseEvent.MOUSE_RELEASED, MouseEvent.BUTTON2_MASK, source);
    }


    private MouseEvent newEvent(int eventType, int modifiers, Component source) {
        return new MouseEvent(source, eventType, 0, modifiers, 5, 5, 1, false);
    }


    private void assertContainAction(String actionId, List<JMenuItem> itemList) {
        findMenuItem(actionId, itemList);
    }


    private JMenuItem findMenuItem(String actionId, List<JMenuItem> itemList) {
        for (JMenuItem item : itemList) {
            if (actionId.equals(item.getActionCommand())) {
                return item;
            }
        }
        fail("L'action " + actionId + " est introuvable");
        return null;
    }


    private static class MockDialogManager extends DialogManager {
        MockPopupMenu popupMenu = new MockPopupMenu();
        private int newPopupMenuCalled = 0;


        @Override
        public JPopupMenu newPopupMenu() {
            newPopupMenuCalled++;
            return popupMenu;
        }
    }

    private static class MockPopupMenu extends JPopupMenu {
        List<JMenuItem> itemList = new ArrayList<JMenuItem>();
        Component invoker;
        Point location;


        @Override
        public JMenuItem add(String label) {
            JMenuItem menuItem = super.add(label);
            itemList.add(menuItem);
            return menuItem;
        }


        @Override
        public void show(Component source, int posX, int posY) {
            this.invoker = source;
            this.location = new Point(posX, posY);
        }
    }
}
