/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import javax.swing.*;
import junit.framework.TestCase;
import recorder.component.GuiComponentFactory;
import recorder.result.Statement;
/**

 */
public class AssertValueTest extends TestCase {
    private AssertValue assertValue;
    private MockAssertContext context;


    @Override
    protected void setUp() throws Exception {
        context = new MockAssertContext();
        assertValue = new AssertValue(context);
    }


    public void test_type() throws Exception {
        assertEquals(AbstractAssert.COMPONENT_ASSERT, assertValue.getAssertType());
    }


    public void test_assert_value_jTextField() throws Exception {
        JTextField source = new JTextField("Ma valeur");
        source.setName("Mon textField");

        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertValue.execute();

        Statement resultAssert = context.getPostedAssert();
        assertNotNull("un assertion est défini", resultAssert);
        assertEquals("<assertValue name=\"Mon textField\" expected=\"Ma valeur\"/>",
                     resultAssert.toXml());
    }


    public void test_assert_value_jCheckBox() throws Exception {
        JCheckBox source = new JCheckBox();
        source.setName("Mon textField");
        source.setSelected(true);

        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertValue.execute();

        Statement resultAssert = context.getPostedAssert();
        assertNotNull("un assertion est défini", resultAssert);
        assertEquals("<assertValue name=\"Mon textField\" expected=\"true\"/>",
                     resultAssert.toXml());
    }


    public void test_assert_value_jComboBox() throws Exception {
        JComboBox source = new JComboBox(new String[]{"Val1", "Val2"});
        source.setSelectedItem("Val2");
        source.setName("Mon comboBox");

        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertValue.execute();

        Statement resultAssert = context.getPostedAssert();
        assertNotNull("un assertion est défini", resultAssert);
        assertEquals("<assertValue name=\"Mon comboBox\" expected=\"Val2\"/>",
                     resultAssert.toXml());
    }


    public void test_assert_bad_source() throws Exception {
        JTable source = new JTable();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertValue.execute();

        Statement resultAssert = context.getPostedAssert();
        assertNull("un assertion n'est pas défini", resultAssert);
    }


    public void test_update_disable() throws Exception {
        JTable source = new JTable();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertValue.update();

        assertFalse("Assert disable", assertValue.isEnabled());
    }


    public void test_update_enable_jTextField() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertValue.update();

        assertFalse("Assert disabled (car composant non trouvable)",
                    assertValue.isEnabled());

        source.setName("bobo");
        assertValue.update();

        assertTrue("Assert enable", assertValue.isEnabled());
    }


    public void test_update_enable_jComboBox() throws Exception {
        JComboBox source = new JComboBox();
        source.setName("aName");
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertValue.update();

        assertTrue("Assert enable", assertValue.isEnabled());
    }


    public void test_update_enable_jCheckBox() throws Exception {
        JCheckBox source = new JCheckBox();
        source.setName("aName");
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertValue.update();

        assertTrue("Assert enable", assertValue.isEnabled());
    }
}
