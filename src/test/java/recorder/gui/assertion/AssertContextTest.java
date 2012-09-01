/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import org.junit.Assert;
import org.junit.Test;
import recorder.Recorder;
import recorder.component.GuiComponentFactory;
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
import recorder.result.Statement;
/**

 */
public class AssertContextTest {
    @Test
    public void test_constructor() throws Exception {
        try {
            new AssertContext(null);
            Assert.fail("The recorder must be defined");
        }
        catch (IllegalArgumentException ex) {
            ; // Ok
        }
    }


    @Test
    public void test_postAssert() throws Exception {
        Recorder recorder = new Recorder(new GuiComponentFactory());
        AssertContext context = new AssertContext(recorder);
        Statement assertStmt =
              new DefaultStatement("assertion", AttributeList.EMPTY_LIST);

        context.postAssert(assertStmt);

        Assert.assertEquals(assertStmt, recorder.getGestureResultList().lastResult());
    }
}
