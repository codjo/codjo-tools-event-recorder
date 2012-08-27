/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.action;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * Manager d'action.
 */
public class GuiActionManager {
    private Map storage = new HashMap();

    public void declare(GuiAction action) {
        storage.put(action.getValue(GuiAction.ACTION_ID), action);
    }


    public GuiAction getAction(String actionId) {
        return (GuiAction)storage.get(actionId);
    }


    public Iterator actions() {
        return storage.values().iterator();
    }
}
