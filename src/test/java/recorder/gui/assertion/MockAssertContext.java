/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import recorder.Recorder;
import recorder.component.GuiComponentFactory;
import recorder.result.Statement;
/**
 * Simule un contexte d'assertion.
 */
public class MockAssertContext extends AssertContext {
    private Statement postedAssert;


    public MockAssertContext() {
        super(new Recorder(new GuiComponentFactory()));
    }


    @Override
    public void postAssert(Statement result) {
        postedAssert = result;
    }


    public Statement getPostedAssert() {
        return postedAssert;
    }
}
