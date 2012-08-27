/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import junit.framework.TestCase;
import recorder.component.GuiComponentFactory;
import recorder.result.Statement;
/**
 * Classe de test de {@link AssertFrame}.
 */
public class AssertFrameTest extends TestCase {
    private AssertFrame assertFrame;
    private MockAssertContext assertContext;

    public void test_type() throws Exception {
        assertEquals(AbstractAssert.GLOBAL_ASSERT, assertFrame.getAssertType());
    }


    public void test_assert_jframe() throws Exception {
        assertTitle("Options", new JFrame("Options"));
    }


    public void test_assert_jdialog() throws Exception {
        assertTitle("Options", new JDialog(new JFrame(), "Options"));
    }


    public void test_assert_noParent() throws Exception {
        JTextField source = new JTextField();

        assertContext.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertFrame.execute();

        Statement resultAssert = assertContext.getPostedAssert();
        assertNull("un assertion est défini", resultAssert);
    }


    public void test_assert_internalframe() throws Exception {
        assertTitle("Options", new JInternalFrame("Options"));
    }


    public void test_update() throws Exception {
        JInternalFrame frame = new JInternalFrame("Options");
        JTextField source = new JTextField();
        frame.getContentPane().add(source);

        assertContext.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertFrame.update();

        assertEquals("AssertFrame titre='Options'",
            assertFrame.getValue(AssertFrame.LABEL));
    }


    protected void setUp() throws Exception {
        assertContext = new MockAssertContext();
        assertFrame = new AssertFrame(assertContext);
    }


    private void assertTitle(String expectedTitle, RootPaneContainer container) {
        JTextField source = new JTextField();
        container.getContentPane().add(source);

        assertContext.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertFrame.execute();

        Statement resultAssert = assertContext.getPostedAssert();
        assertNotNull("un assertion est défini", resultAssert);
        assertEquals("<assertFrame title=\"" + expectedTitle + "\"/>",
            resultAssert.toXml());
    }
}
