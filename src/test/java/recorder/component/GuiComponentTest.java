/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.component;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import junit.framework.TestCase;
/**
 * Classe de test de {@link GuiComponent}.
 */
public class GuiComponentTest extends TestCase {
    public void test_isA() throws Exception {
        GuiComponent guiComponent = GuiComponentFactory.newGuiComponent(new JTextField());
        assertTrue(guiComponent.isA(JTextComponent.class));
        assertTrue(guiComponent.isA(JTextField.class));
        assertFalse(guiComponent.isA(JComboBox.class));
    }


    public void test_equal() throws Exception {
        JTextField fieldA = new JTextField();
        JTextField fieldB = new JTextField();

        GuiComponent compA = GuiComponentFactory.newGuiComponent(fieldA);
        assertTrue("GuiComp(a) = GuiComp(a)", compA.equals(compA));
        assertTrue("new GuiComp(a) = new GuiComp(a)",
            compA.equals(GuiComponentFactory.newGuiComponent(fieldA)));
        assertEquals("[hashcode] new GuiComp(a) = new GuiComp(a)", compA.hashCode(),
            GuiComponentFactory.newGuiComponent(fieldA).hashCode());

        assertFalse("new GuiComp(b) != new GuiComp(a)",
            GuiComponentFactory.newGuiComponent(fieldB).equals(GuiComponentFactory
                .newGuiComponent(fieldA)));
        assertFalse("new GuiComp(b) != 'carotte'",
            GuiComponentFactory.newGuiComponent(fieldB).equals("carotte"));
    }


    public void test_canBeFoundWith() throws Exception {
        JButton textField = new JButton();

        GuiComponent gui = GuiComponentFactory.newGuiComponent(textField);

        textField.setName("gui.name");
        assertTrue("Recherche 'nom' : Ok", gui.canBeFoundWith(FindStrategyId.BY_NAME));
        assertTrue("Recherche 'label' : NOk", !gui.canBeFoundWith(FindStrategyId.BY_LABEL));

        textField.setText("gui.label");
        assertTrue("Recherche 'nom' : Ok", gui.canBeFoundWith(FindStrategyId.BY_NAME));
        assertTrue("Recherche 'label' : Ok", gui.canBeFoundWith(FindStrategyId.BY_LABEL));
    }


    public void test_findByAccessibleContext() throws Exception {
        JTextField textField = new JTextField();

        GuiComponent guiComponent = GuiComponentFactory.newGuiComponent(textField);
        assertFalse("Composant inretrouvable", guiComponent.isFindable());
        assertEquals(FindStrategyId.NONE, guiComponent.getBestFindStrategyId());

        textField.getAccessibleContext().setAccessibleName("Dododidon");

        assertTrue("Composant retrouvable par le contexte", guiComponent.isFindable());
        assertEquals(FindStrategyId.BY_ACCESSIBLE_CONTEXT,
            guiComponent.getBestFindStrategyId());
    }


    public void test_findByName() throws Exception {
        JTextField textField = new JTextField();

        GuiComponent guiComponent = GuiComponentFactory.newGuiComponent(textField);
        assertFalse("Composant inretrouvable", guiComponent.isFindable());

        textField.setName("Dododidon");

        assertTrue("Composant retrouvable par le nom", guiComponent.isFindable());
        assertEquals(FindStrategyId.BY_NAME, guiComponent.getBestFindStrategyId());
    }


    public void test_findByLabel_menuItem() throws Exception {
        assertFindByName(new JMenuItem());
    }


    public void test_findByLabel_button() throws Exception {
        assertFindByName(new JButton());
    }


    private void assertFindByName(AbstractButton comp) {
        GuiComponent guiComponent = GuiComponentFactory.newGuiComponent(comp);
        assertFalse("Composant inretrouvable", guiComponent.isFindable());

        comp.setText("Bobo");

        assertTrue("Composant retrouvable par le label", guiComponent.isFindable());
        assertEquals(FindStrategyId.BY_LABEL, guiComponent.getBestFindStrategyId());
        assertEquals("Bobo", guiComponent.getLabel());
    }
}
