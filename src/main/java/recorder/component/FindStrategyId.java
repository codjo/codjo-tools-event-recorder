/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.component;
/**
 * Enumération des différentes stratégie de recherche d'un composant.
 *
 * @version $Revision: 1.1.1.1 $
 */
public final class FindStrategyId {
    public static final FindStrategyId NONE = new FindStrategyId("NONE");
    public static final FindStrategyId BY_NAME = new FindStrategyId("BY_NAME");
    public static final FindStrategyId BY_LABEL = new FindStrategyId("BY_LABEL");
    public static final FindStrategyId BY_ACCESSIBLE_CONTEXT =
        new FindStrategyId("BY_ACCESSIBLE_CONTEXT");
    private final String myName; // for debug only

    private FindStrategyId(String name) {
        myName = name;
    }

    public String toString() {
        return myName;
    }
}
