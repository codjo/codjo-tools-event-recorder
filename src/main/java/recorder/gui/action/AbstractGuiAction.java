/*
 * codjo (Prototype)
 * =================
 *
 *    Copyright (C) 2005, 2012 by codjo.net
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *    implied. See the License for the specific language governing permissions
 *    and limitations under the License.
 */
package recorder.gui.action;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.event.SwingPropertyChangeSupport;
public abstract class AbstractGuiAction implements GuiAction {
    protected final ResourceBundle resourceBundle = ResourceBundle.getBundle("recorder.gui.gui-resources");
    ;
    private boolean enabled = true;
    private SwingPropertyChangeSupport changeSupport;
    private Map<String, Object> storage = new HashMap<String, Object>();


    protected AbstractGuiAction(String actionId) {
        putValue(ACTION_ID, actionId);
    }


    public void update() {
    }


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
