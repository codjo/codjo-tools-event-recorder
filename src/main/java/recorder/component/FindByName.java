/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.component;
import javax.swing.*;
/**
 * Search strategy using the swing component name. See ({@link java.awt.Component#getName()}).
 */
class FindByName implements FindStrategy {
    public boolean canFound(JComponent swingComponent) {
        return swingComponent.getName() != null;
    }


    public FindStrategyId getStrategyId() {
        return FindStrategyId.BY_NAME;
    }
}
