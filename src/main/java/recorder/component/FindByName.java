/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.component;
import javax.swing.JComponent;
/**
 * Strat�gie de recherche par nom. Cette strat�gie permet de trouver un composant grace a
 * son nom ({@link java.awt.Component#getName()}).
 *
 * @version $Revision: 1.1.1.1 $
 */
class FindByName implements FindStrategy {
    public boolean canFound(JComponent swingComponent) {
        return swingComponent.getName() != null;
    }


    public FindStrategyId getStrategyId() {
        return FindStrategyId.BY_NAME;
    }
}
