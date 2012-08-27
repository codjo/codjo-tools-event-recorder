/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.component;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
/**
 * Stratégie de recherche par label. Cette stratégie permet de trouver un composant par
 * le label.
 * 
 * <p>
 * <b>NB:</b>Pour l'instant seul les JMenuItem sont pris en compte.
 * </p>
 *
 * @version $Revision: 1.2 $
 */
class FindByLabel implements FindStrategy {
    public boolean canFound(JComponent swingComponent) {
        return swingComponent instanceof AbstractButton
        && isEmpty(((AbstractButton)swingComponent).getText());
    }


    private boolean isEmpty(String text) {
        return text != null && text.length() != 0;
    }


    public FindStrategyId getStrategyId() {
        return FindStrategyId.BY_LABEL;
    }


    public String getLabelFor(JComponent component) {
        return ((AbstractButton)component).getText();
    }
}
