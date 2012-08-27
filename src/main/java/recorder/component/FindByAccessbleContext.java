/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.component;
import javax.accessibility.AccessibleContext;
import javax.swing.JComponent;
/**
 * Stratégie de recherche par nom d'accés. Cette stratégie permet de trouver un composant
 * par le nom du {@link AccessibleContext}.
 *
 * @version $Revision: 1.1.1.1 $
 */
class FindByAccessbleContext implements FindStrategy {
    public boolean canFound(JComponent swingComponent) {
        AccessibleContext accessibleContext = swingComponent.getAccessibleContext();
        return accessibleContext != null && accessibleContext.getAccessibleName() != null
        && accessibleContext.getAccessibleName().length() > 0;
    }


    public FindStrategyId getStrategyId() {
        return FindStrategyId.BY_ACCESSIBLE_CONTEXT;
    }
}
