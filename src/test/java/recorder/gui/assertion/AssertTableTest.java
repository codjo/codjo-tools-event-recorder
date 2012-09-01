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
public class AssertTableTest {
    private AssertTable assertTable;
    private MockAssertContext context;


    @Before
    public void setUp() throws Exception {
        context = new MockAssertContext();
        assertTable = new AssertTable(context);
    }


    @Test
    public void test_type() throws Exception {
        Assert.assertEquals(AbstractAssert.COMPONENT_ASSERT, assertTable.getAssertType());
    }


    @Test
    public void test_assert_selected() throws Exception {
        JTable source = buildJTable(1, 0, "Ma table");

        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));
        context.setPoint(new Point(1, 1));

        assertTable.execute();

        Statement resultAssert = context.getPostedAssert();
        Assert.assertNotNull("un assertion est d�fini", resultAssert);
        Assert.assertEquals("<assertTable name=\"Ma table\" expected=\"a2\" row=\"1\" column=\"0\"/>",
                            resultAssert.toXml());
    }


    @Test
    public void test_assert_bad_source() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertTable.execute();

        Statement resultAssert = context.getPostedAssert();
        Assert.assertNull("un assertion n'est pas d�fini", resultAssert);
    }


    @Test
    public void test_update_disable() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertTable.update();

        Assert.assertFalse("Assert disable", assertTable.isEnabled());
    }


    @Test
    public void test_update_enable() throws Exception {
        JTable source = buildJTable(2, 0, "Ma table");
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertTable.update();

        Assert.assertTrue("Assert ensable", assertTable.isEnabled());
    }


    private JTable buildJTable(final int rowAtPoint, final int colAtPoint, String name) {
        JTable source =
              new JTable(new String[][]{
                    {"a1", "b1"},
                    {"a2", "b2"}
              }, new String[]{"colA", "colB"}) {
                  @Override
                  public int rowAtPoint(Point point) {
                      return rowAtPoint;
                  }


                  @Override
                  public int columnAtPoint(Point point) {
                      return colAtPoint;
                  }
              };
        source.setName(name);
        return source;
    }
}
