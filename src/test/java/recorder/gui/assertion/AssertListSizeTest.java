/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import java.awt.Point;
import java.util.Arrays;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import junit.framework.TestCase;
import recorder.component.GuiComponentFactory;
import recorder.result.Statement;
/**
 * Classe de test de {@link AssertListSize}.
 */
public class AssertListSizeTest extends TestCase {
    private static final int EXPECTED_SIZE = 1;
    private AssertListSize assertListSize;
    private MockAssertContext context;

    protected void setUp() throws Exception {
        context = new MockAssertContext();
        assertListSize = new AssertListSize(context);
    }


    public void test_type() throws Exception {
        assertEquals(AbstractAssert.COMPONENT_ASSERT, assertListSize.getAssertType());
    }


    public void test_assert_size_table() throws Exception {
        String name = "Ma table";
        JTable source = buildJTable(EXPECTED_SIZE, name);

        assertSizeTest(source, name);
    }


    public void test_assert_size_list() throws Exception {
        JList source = buildJList(EXPECTED_SIZE, "Ma Liste");

        assertSizeTest(source, "Ma Liste");
    }


    public void test_assert_size_combo() throws Exception {
        JComboBox source = buildJCombo(EXPECTED_SIZE, "Ma combo");

        assertSizeTest(source, "Ma combo");
    }


    private void assertSizeTest(JComponent source, String name) {
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));
        context.setPoint(new Point(1, 1));

        assertListSize.execute();

        Statement resultAssert = context.getPostedAssert();
        assertNotNull("un assertion est défini pour " + name, resultAssert);
        assertEquals("<assertListSize name=\"" + name + "\" expected=\"" + EXPECTED_SIZE
            + "\"/>", resultAssert.toXml());
    }


    public void test_assert_bad_source() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertListSize.execute();

        Statement resultAssert = context.getPostedAssert();
        assertNull("un assertion n'est pas défini", resultAssert);
    }


    public void test_update_disable() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertListSize.update();

        assertFalse("Assert disable", assertListSize.isEnabled());
    }


    public void test_update_enable_table() throws Exception {
        assertEnabledTest(buildJTable(2, "Ma table"));
    }


    public void test_update_enable_list() throws Exception {
        assertEnabledTest(buildJList(2, "Ma List"));
    }


    public void test_update_enable_combo() throws Exception {
        assertEnabledTest(buildJCombo(2, "Ma Combo"));
    }


    private void assertEnabledTest(JComponent source) {
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertListSize.update();

        assertTrue("Assert enable", assertListSize.isEnabled());
    }


    private JTable buildJTable(final int size, String name) {
        JTable source = new JTable(size, 2);
        source.setName(name);
        return source;
    }


    private JList buildJList(final int size, String name) {
        Object[] listData = new Object[size];
        Arrays.fill(listData, "value");
        JList list = new JList(listData);
        list.setName(name);
        return list;
    }


    private JComboBox buildJCombo(int expectedSize, String name) {
        Object[] items = new Object[expectedSize];
        Arrays.fill(items, "value");
        JComboBox combo = new JComboBox(items);
        combo.setName(name);
        return combo;
    }
}
