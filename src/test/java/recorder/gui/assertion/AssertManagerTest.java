/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import junit.framework.TestCase;
import recorder.Recorder;
import recorder.component.GuiComponentFactory;
/**
 * Classe de test de {@link AssertManager}.
 */
public class AssertManagerTest extends TestCase {
    private AssertManager manager;
    private MockDialogManager mockDialogManager;
    private MouseEvent validEvent;
    private Recorder recorder;
    private GuiComponentFactory factory;

    public void test_isAssertTrigger_valid() {
        assertTrue("Click Valide", manager.isAssertTrigger(validEvent));
    }


    public void test_isAssertTrigger_badMouseEvent() {
        MouseEvent notClick =
            newEvent(MouseEvent.MOUSE_ENTERED,
                MouseEvent.CTRL_MASK | MouseEvent.BUTTON3_MASK);
        assertFalse("Pas un click", manager.isAssertTrigger(notClick));

        MouseEvent badModifierEvent =
            newEvent(MouseEvent.MOUSE_ENTERED, MouseEvent.BUTTON3_MASK);
        assertFalse("Pas le bon modifier", manager.isAssertTrigger(badModifierEvent));
    }


    public void test_isAssertTrigger_bad() {
        assertFalse("Pas un click", manager.isAssertTrigger(null));

        assertFalse("Pas le bon modifier",
            manager.isAssertTrigger(new KeyEvent(new JTextField(), 0, 0, 0, 0)));
    }


    public void test_popupTriggered() {
        manager.eventDispatched(validEvent);

        assertEquals("Popup est affiché", 1, mockDialogManager.newPopupMenuCalled);
        MockPopupMenu popupMenu = mockDialogManager.popupMenu;
        assertEquals("A la Bonne position", validEvent.getPoint(), popupMenu.location);

        assertSame("Avec le Bon invoker", validEvent.getSource(), popupMenu.invoker);
    }


    /**
     * Test que le popup s'affiche correctement dans le cas particulier des invokers
     * ayant des contentPane (JDialog , Frame). Il ne faut pas utiliser la source pour
     * le popup mais le composant retourné par le finder.
     */
    public void test_popupTriggered_invokerWithContent() {
        JDialog source =
            new JDialog() {
                public Component findComponentAt(Point point) {
                    return this.getContentPane();
                }
            };

        validEvent = newValidEventOn(source);
        manager.eventDispatched(validEvent);

        assertEquals("Popup est affiché", 1, mockDialogManager.newPopupMenuCalled);
        MockPopupMenu popupMenu = mockDialogManager.popupMenu;
        assertEquals("A la Bonne position", validEvent.getPoint(), popupMenu.location);

        assertSame("Avec le Bon invoker", source.getContentPane(), popupMenu.invoker);
    }


    public void test_popupTriggered_badsource() {
        factory.setIgnoredContainer("containerName");
        ((JComponent)validEvent.getSource()).setName("containerName");

        manager.eventDispatched(validEvent);

        assertEquals("Popup n'est pas affiché (car composant ignoré)", 0,
            mockDialogManager.newPopupMenuCalled);
    }


    public void test_popupTriggered_bad() {
        MouseEvent bad = newEvent(MouseEvent.MOUSE_PRESSED, MouseEvent.BUTTON3_MASK);

        manager.eventDispatched(bad);

        assertEquals("Le Popup n'est pas affiché", 0, mockDialogManager.newPopupMenuCalled);
    }


    public void test_actionsList() {
        manager.eventDispatched(validEvent);

        // Assert le contexte
        assertSame(validEvent.getSource(), manager.getAssertContext().getSource());
        assertEquals(validEvent.getPoint(), manager.getAssertContext().getPoint());

        // Assert les actions
        List itemList = mockDialogManager.popupMenu.itemList;
        assertContainAction(AssertFrame.ID, itemList);
        assertContainAction(AssertSelected.ID, itemList);
        assertContainAction(AssertListSize.ID, itemList);
        assertContainAction(AssertList.ID, itemList);
        assertContainAction(AssertTable.ID, itemList);
        assertContainAction(AssertTree.ID, itemList);
        assertContainAction(AssertValue.ID, itemList);
        assertEquals("Action d'assertion sont affiché", 6, itemList.size());
    }


    public void test_source_jcombobox() {
        JComboBox comboBox = new JComboBox();
        MouseEvent eventUponCombo =
            newEvent(MouseEvent.MOUSE_RELEASED, MouseEvent.BUTTON2_MASK,
                comboBox.getComponent(0));

        manager.eventDispatched(eventUponCombo);

        assertSame(comboBox, manager.getAssertContext().getSource());
    }


    public void test_actionsList_noName() {
        validEvent.getComponent().setName(null);

        manager.eventDispatched(validEvent);

        List itemList = mockDialogManager.popupMenu.itemList;
        assertTrue("Action assertFrame est global",
            findMenuItem(AssertFrame.ID, itemList).isEnabled());
        assertFalse("Action assertValue necessite un composant nommé",
            findMenuItem(AssertValue.ID, itemList).isEnabled());
    }


    public void test_actionsList_withName() {
        validEvent.getComponent().setName("bobo");

        manager.eventDispatched(validEvent);

        List itemList = mockDialogManager.popupMenu.itemList;
        assertTrue("Action assertFrame est global",
            findMenuItem(AssertFrame.ID, itemList).isEnabled());
        assertTrue("Action assertValue necessite un composant nommé",
            findMenuItem(AssertValue.ID, itemList).isEnabled());
    }


    protected void setUp() throws Exception {
        mockDialogManager = new MockDialogManager();
        factory = new GuiComponentFactory();
        recorder = new Recorder(factory);
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


    private void assertContainAction(String actionId, List itemList) {
        findMenuItem(actionId, itemList);
    }


    private JMenuItem findMenuItem(String actionId, List itemList) {
        for (Iterator iter = itemList.iterator(); iter.hasNext();) {
            JMenuItem item = (JMenuItem)iter.next();
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

        public JPopupMenu newPopupMenu() {
            newPopupMenuCalled++;
            return popupMenu;
        }
    }


    private static class MockPopupMenu extends JPopupMenu {
        List itemList = new ArrayList();
        Component invoker;
        Point location;

        public JMenuItem add(String label) {
            JMenuItem menuItem = super.add(label);
            itemList.add(menuItem);
            return menuItem;
        }


        public void show(Component source, int posX, int posY) {
            this.invoker = source;
            this.location = new Point(posX, posY);
        }
    }
}
