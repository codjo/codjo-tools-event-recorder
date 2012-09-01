/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import junit.framework.TestCase;
import recorder.component.GuiComponentFactory;
import recorder.result.Statement;
/**

 */
public class AssertListTest extends TestCase {
    private static final int ROW = 1;
    private static final Object VALUE = "expectedValue";
    private AssertList assertList;
    private MockAssertContext context;


    @Override
    protected void setUp() throws Exception {
        context = new MockAssertContext();
        assertList = new AssertList(context);
    }


    public void test_type() throws Exception {
        assertEquals(AbstractAssert.COMPONENT_ASSERT, assertList.getAssertType());
    }


    public void test_assert_list() throws Exception {
        JList source = buildJList(ROW, VALUE, "Ma Liste");

        assertListTest(source, "Ma Liste");
    }


    private void assertListTest(JComponent source, String name) {
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));
        context.setPoint(new Point(1, 1));

        assertList.execute();

        Statement resultAssert = context.getPostedAssert();
        assertNotNull("un assertion est défini pour " + name, resultAssert);
        assertEquals("<assertList name=\"" + name + "\" expected=\"" + VALUE
                     + "\" row=\"" + ROW + "\"/>", resultAssert.toXml());
    }


    public void test_assert_bad_source() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertList.execute();

        Statement resultAssert = context.getPostedAssert();
        assertNull("un assertion n'est pas défini", resultAssert);
    }


    public void test_update_disable() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertList.update();

        assertFalse("Assert disable", assertList.isEnabled());
    }


    public void test_update_enable_list() throws Exception {
        assertEnabledTest(buildJList(2, VALUE, "Ma List"));
    }


    private void assertEnabledTest(JComponent source) {
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertList.update();

        assertTrue("Assert ensable", assertList.isEnabled());
    }


    private JList buildJList(final int rowAtPoint, Object value, String name) {
        Object[] content = new Object[2 * rowAtPoint];
        Arrays.fill(content, "bad");
        content[rowAtPoint] = value;

        JList list =
              new JList(content) {
                  @Override
                  public int locationToIndex(Point point) {
                      return rowAtPoint;
                  }
              };
        list.setName(name);
        return list;
    }
}
