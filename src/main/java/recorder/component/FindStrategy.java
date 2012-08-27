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
     * Indique si le composant peut être trouvé grâce à cette stratégie.
     *
     * @param swingComponent Le composant Swing
     *
     * @return <code>true</code> le composant peut être retrouvé
     */
    public boolean canFound(JComponent swingComponent);


    /**
     * Retourne l'identifiant de la stratégie.
     *
     * @return Id de la stratégie (e.g. FindStrategy.BY_NAME)
     */
    public FindStrategyId getStrategyId();
}
