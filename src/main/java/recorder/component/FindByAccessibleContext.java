/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.component;
import javax.accessibility.AccessibleContext;
import javax.swing.*;
/**
 * Search strategy using the swing accessible context. Can find a component by the {@link javax.accessibility.AccessibleContext} name.
 */
class FindByAccessibleContext implements FindStrategy {
    public boolean canFound(JComponent swingComponent) {
        AccessibleContext accessibleContext = swingComponent.getAccessibleContext();
        return accessibleContext != null && accessibleContext.getAccessibleName() != null
               && accessibleContext.getAccessibleName().length() > 0;
    }


    public FindStrategyId getStrategyId() {
        return FindStrategyId.BY_ACCESSIBLE_CONTEXT;
    }
}
