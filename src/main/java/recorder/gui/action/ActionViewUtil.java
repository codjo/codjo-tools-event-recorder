/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.action;
import javax.swing.AbstractButton;
/**
 * Classe utilitaire permettant de connecter une action à une vue .
 */
public final class ActionViewUtil {
    private ActionViewUtil() {}

    public static void connectActionTo(GuiAction action, final AbstractButton button) {
        button.setAction(new SwingActionAdapter(action));

        String actionId = (String)action.getValue(GuiAction.ACTION_ID);
        button.setName(actionId);
        button.setActionCommand(actionId);
    }
}
