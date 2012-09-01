/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.component;
import javax.swing.*;
/**
 * Describe a search strategy.
 */
interface FindStrategy {
    /**
     * @return <code>true</code> if the component can be handled by this strategy
     */
    public boolean canFound(JComponent swingComponent);


    /**
     * @return Strategy id (e.g. FindStrategy.BY_NAME)
     */
    public FindStrategyId getStrategyId();
}
