/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import java.awt.*;
import javax.swing.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import recorder.component.GuiComponentFactory;
import recorder.result.Statement;
/**

 */
public class AssertSelectedTest {
    private static final int SELECTED_ROW = 1;
    private AssertSelected assertSelected;
    private MockAssertContext context;


    @Before
    public void setUp() throws Exception {
        context = new MockAssertContext();
        assertSelected = new AssertSelected(context);
    }


    @Test
    public void test_type() throws Exception {
        Assert.assertEquals(AbstractAssert.COMPONENT_ASSERT, assertSelected.getAssertType());
    }


    @Test
    public void test_assert_selected_table() throws Exception {
        JTable source = buildJTable(SELECTED_ROW, "Ma table");

        assertSelectedTest(source, source.getSelectionModel(), "Ma table");
    }


    @Test
    public void test_assert_selected_list() throws Exception {
        JList source = buildJList(SELECTED_ROW, "Ma Liste");

        assertSelectedTest(source, source.getSelectionModel(), "Ma Liste");
    }


    private void assertSelectedTest(JComponent source, ListSelectionModel selectionModel,
                                    String name) {
        selectionModel.setSelectionInterval(SELECTED_ROW, SELECTED_ROW);
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));
        context.setPoint(new Point(1, 1));

        assertSelected.execute();

        Statement resultAssert = context.getPostedAssert();
        Assert.assertNotNull("un assertion est défini pour " + name, resultAssert);
        Assert.assertEquals("<assertSelected name=\"" + name + "\" row=\"" + SELECTED_ROW
                            + "\"/>", resultAssert.toXml());
    }


    @Test
    public void test_assert_notselected() throws Exception {
        JTable source = buildJTable(2, "Ma table");

        source.getSelectionModel().setSelectionInterval(1, 1);
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));
        context.setPoint(new Point(1, 1));

        assertSelected.execute();

        Statement resultAssert = context.getPostedAssert();
        Assert.assertNotNull("un assertion est défini", resultAssert);
        Assert.assertEquals("<assertSelected name=\"Ma table\" row=\"2\" expected=\"false\"/>",
                            resultAssert.toXml());
    }


    @Test
    public void test_assert_bad_source() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertSelected.execute();

        Statement resultAssert = context.getPostedAssert();
        Assert.assertNull("un assertion n'est pas défini", resultAssert);
    }


    @Test
    public void test_update_disable() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertSelected.update();

        Assert.assertFalse("Assert disable", assertSelected.isEnabled());
    }


    @Test
    public void test_update_enable_table() throws Exception {
        assertEnabledTest(buildJTable(2, "Ma table"));
    }


    @Test
    public void test_update_enable_list() throws Exception {
        assertEnabledTest(buildJList(2, "Ma List"));
    }


    private void assertEnabledTest(JComponent source) {
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertSelected.update();

        Assert.assertTrue("Assert ensable", assertSelected.isEnabled());
    }


    private JTable buildJTable(final int rowAtPoint, String name) {
        JTable source =
              new JTable(3, 2) {
                  @Override
                  public int rowAtPoint(Point point) {
                      return rowAtPoint;
                  }
              };
        source.setName(name);
        return source;
    }


    private JList buildJList(final int rowAtPoint, String name) {
        JList list =
              new JList() {
                  @Override
                  public int locationToIndex(Point point) {
                      return rowAtPoint;
                  }
              };
        list.setName(name);
        return list;
    }
}
