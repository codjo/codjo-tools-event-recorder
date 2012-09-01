/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import javax.swing.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import recorder.component.GuiComponentFactory;
import recorder.result.Statement;
/**

 */
public class AssertFrameTest {
    private AssertFrame assertFrame;
    private MockAssertContext assertContext;


    @Test
    public void test_type() throws Exception {
        Assert.assertEquals(AbstractAssert.GLOBAL_ASSERT, assertFrame.getAssertType());
    }


    @Test
    public void test_assert_jframe() throws Exception {
        assertTitle("Options", new JFrame("Options"));
    }


    @Test
    public void test_assert_jdialog() throws Exception {
        assertTitle("Options", new JDialog(new JFrame(), "Options"));
    }


    @Test
    public void test_assert_noParent() throws Exception {
        JTextField source = new JTextField();

        assertContext.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertFrame.execute();

        Statement resultAssert = assertContext.getPostedAssert();
        Assert.assertNull("an assertion has been defined", resultAssert);
    }


    @Test
    public void test_assert_internalframe() throws Exception {
        assertTitle("Options", new JInternalFrame("Options"));
    }


    @Test
    public void test_update() throws Exception {
        JInternalFrame frame = new JInternalFrame("Options");
        JTextField source = new JTextField();
        frame.getContentPane().add(source);

        assertContext.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertFrame.update();

        Assert.assertEquals("AssertFrame titre='Options'",
                            assertFrame.getValue(AssertFrame.LABEL));
    }


    @Before
    public void setUp() throws Exception {
        assertContext = new MockAssertContext();
        assertFrame = new AssertFrame(assertContext);
    }


    private void assertTitle(String expectedTitle, RootPaneContainer container) {
        JTextField source = new JTextField();
        container.getContentPane().add(source);

        assertContext.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertFrame.execute();

        Statement resultAssert = assertContext.getPostedAssert();
        Assert.assertNotNull("un assertion est défini", resultAssert);
        Assert.assertEquals("<assertFrame title=\"" + expectedTitle + "\"/>",
                            resultAssert.toXml());
    }
}
