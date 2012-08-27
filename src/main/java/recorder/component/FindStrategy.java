/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.component;
import javax.swing.JComponent;
/**
 * Strategy to component a component.
 */
interface FindStrategy {
    /**
     * Indique si le composant peut �tre trouv� gr�ce � cette strat�gie.
     *
     * @param swingComponent Le composant Swing
     *
     * @return <code>true</code> le composant peut �tre retrouv�
     */
    public boolean canFound(JComponent swingComponent);


    /**
     * Retourne l'identifiant de la strat�gie.
     *
     * @return Id de la strat�gie (e.g. FindStrategy.BY_NAME)
     */
    public FindStrategyId getStrategyId();
}
