/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.action;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.event.SwingPropertyChangeSupport;
/**
 */
public abstract class AbstractGuiAction implements GuiAction {
    private boolean enabled = true;
    private SwingPropertyChangeSupport changeSupport;
    private Map storage = new HashMap();

    protected AbstractGuiAction(String actionId) {
        putValue(ACTION_ID, actionId);
    }

    public void update() {}


    public Object getValue(String key) {
        return storage.get(key);
    }


    public void putValue(String key, Object newValue) {
        Object oldValue = null;
        if (storage.containsKey(key)) {
            oldValue = storage.get(key);
        }

        if (newValue == null) {
            storage.remove(key);
        }
        else {
            storage.put(key, newValue);
        }
        firePropertyChange(key, oldValue, newValue);
    }


    public boolean isEnabled() {
        return enabled;
    }


    public void setEnabled(boolean newValue) {
        boolean oldValue = this.enabled;
        this.enabled = newValue;
        firePropertyChange("enabled", toBoolean(oldValue), toBoolean(newValue));
    }


    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        if (changeSupport == null) {
            changeSupport = new SwingPropertyChangeSupport(this);
        }
        changeSupport.addPropertyChangeListener(listener);
    }


    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        if (changeSupport == null) {
            return;
        }
        changeSupport.removePropertyChangeListener(listener);
    }


    private void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        if (changeSupport == null) {
            return;
        }
        changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }


    private Boolean toBoolean(boolean oldValue) {
        return (oldValue ? Boolean.TRUE : Boolean.FALSE);
    }
}
