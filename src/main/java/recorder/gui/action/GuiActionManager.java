/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.action;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
public class GuiActionManager {
    private Map<Object, GuiAction> storage = new HashMap<Object, GuiAction>();


    public void declare(GuiAction action) {
        storage.put(action.getValue(GuiAction.ACTION_ID), action);
    }


    public GuiAction getAction(String actionId) {
        return storage.get(actionId);
    }


    public Iterator<GuiAction> actions() {
        return storage.values().iterator();
    }
}
