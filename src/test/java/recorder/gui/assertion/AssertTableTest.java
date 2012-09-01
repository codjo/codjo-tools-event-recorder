/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import java.awt.*;
import javax.swing.*;
import junit.framework.TestCase;
import recorder.component.GuiComponentFactory;
import recorder.result.Statement;
/**

 */
public class AssertTableTest extends TestCase {
    private AssertTable assertTable;
    private MockAssertContext context;


    @Override
    protected void setUp() throws Exception {
        context = new MockAssertContext();
        assertTable = new AssertTable(context);
    }


    public void test_type() throws Exception {
        assertEquals(AbstractAssert.COMPONENT_ASSERT, assertTable.getAssertType());
    }


    public void test_assert_selected() throws Exception {
        JTable source = buildJTable(1, 0, "Ma table");

        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));
        context.setPoint(new Point(1, 1));

        assertTable.execute();

        Statement resultAssert = context.getPostedAssert();
        assertNotNull("un assertion est défini", resultAssert);
        assertEquals("<assertTable name=\"Ma table\" expected=\"a2\" row=\"1\" column=\"0\"/>",
                     resultAssert.toXml());
    }


    public void test_assert_bad_source() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertTable.execute();

        Statement resultAssert = context.getPostedAssert();
        assertNull("un assertion n'est pas défini", resultAssert);
    }


    public void test_update_disable() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertTable.update();

        assertFalse("Assert disable", assertTable.isEnabled());
    }


    public void test_update_enable() throws Exception {
        JTable source = buildJTable(2, 0, "Ma table");
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertTable.update();

        assertTrue("Assert ensable", assertTable.isEnabled());
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
