/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import recorder.gui.action.AbstractGuiAction;
abstract class AbstractAssert extends AbstractGuiAction {
    static final int GLOBAL_ASSERT = 1;
    static final int COMPONENT_ASSERT = 2;
    private final int assertType;


    protected AbstractAssert(String actionId, int assertType) {
        super(actionId);
        this.assertType = assertType;
    }


    public int getAssertType() {
        return assertType;
    }
}
