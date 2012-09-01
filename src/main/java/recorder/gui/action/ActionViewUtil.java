/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.action;
import javax.swing.*;
/**
 * Utility class used to connect an action to a GUI View.
 */
public final class ActionViewUtil {
    private ActionViewUtil() {
    }


    public static void connectActionTo(GuiAction action, final AbstractButton button) {
        button.setAction(new SwingActionAdapter(action));

        String actionId = (String)action.getValue(GuiAction.ACTION_ID);
        button.setName(actionId);
        button.setActionCommand(actionId);
    }
}
