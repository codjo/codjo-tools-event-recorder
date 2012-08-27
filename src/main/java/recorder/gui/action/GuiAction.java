/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.action;
import java.beans.PropertyChangeListener;
/**
 */
public interface GuiAction {
    public static final String ACTION_ID = "action.id";
    public static final String LABEL = "label";
    public static final String TOOLTIP = "tooltip";
    public static final String ICON_ID = "icon.id";

    public void execute();


    public void update();


    public Object getValue(String key);


    public void putValue(String key, Object value);


    public void setEnabled(boolean state);


    public boolean isEnabled();


    public void addPropertyChangeListener(PropertyChangeListener listener);


    public void removePropertyChangeListener(PropertyChangeListener listener);
}
