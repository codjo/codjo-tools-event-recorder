/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import junit.framework.TestCase;
import recorder.Recorder;
import recorder.component.GuiComponentFactory;
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
import recorder.result.Statement;
/**

 */
public class AssertContextTest extends TestCase {
    public void test_constructor() throws Exception {
        try {
            new AssertContext(null);
            fail("The recorder must be defined");
        }
        catch (IllegalArgumentException ex) {
            ; // Ok
        }
    }


    public void test_postAssert() throws Exception {
        Recorder recorder = new Recorder(new GuiComponentFactory());
        AssertContext context = new AssertContext(recorder);
        Statement assertStmt =
              new DefaultStatement("assertion", AttributeList.EMPTY_LIST);

        context.postAssert(assertStmt);

        assertEquals(assertStmt, recorder.getGestureResultList().lastResult());
    }
}
