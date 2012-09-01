/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.action;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import org.apache.log4j.Logger;
import recorder.gui.icons.IconsManager;

/**
 */
class SwingActionAdapter implements Action, PropertyChangeListener {
    private static final Logger LOG = Logger.getLogger(SwingActionAdapter.class);
    private GuiAction guiAction;


    SwingActionAdapter(GuiAction guiAction) {
        this.guiAction = guiAction;
        if (null != guiAction.getValue(GuiAction.ICON_ID)) {
            guiAction.putValue(Action.SMALL_ICON,
                               IconsManager.getIcon(
                                     (IconsManager.IconId)guiAction.getValue(GuiAction.ICON_ID)));
        }
        if (null != guiAction.getValue(GuiAction.LABEL)) {
            guiAction.putValue(Action.NAME, guiAction.getValue(GuiAction.LABEL));
        }
        guiAction.putValue(Action.SHORT_DESCRIPTION, guiAction.getValue(GuiAction.TOOLTIP));
        this.guiAction.addPropertyChangeListener(this);
    }


    public void actionPerformed(ActionEvent event) {
        Object id = guiAction.getValue(GuiAction.ACTION_ID);
        Object aide = guiAction.getValue(GuiAction.TOOLTIP);
        LOG.info("Command '" + id + "' : " + aide);
        guiAction.execute();
    }


    public Object getValue(String key) {
        return guiAction.getValue(key);
    }


    public void putValue(String key, Object value) {
        guiAction.putValue(key, value);
    }


    public void setEnabled(boolean state) {
        guiAction.setEnabled(state);
    }


    public boolean isEnabled() {
        return guiAction.isEnabled();
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        guiAction.addPropertyChangeListener(listener);
    }


    public void removePropertyChangeListener(PropertyChangeListener listener) {
        guiAction.removePropertyChangeListener(listener);
    }


    public void propertyChange(PropertyChangeEvent evt) {
        if (GuiAction.ICON_ID.equals(evt.getPropertyName())) {
            putValue(Action.SMALL_ICON,
                     IconsManager.getIcon((IconsManager.IconId)getValue(GuiAction.ICON_ID)));
        }
        else if (GuiAction.TOOLTIP.equals(evt.getPropertyName())) {
            putValue(Action.SHORT_DESCRIPTION, getValue(GuiAction.TOOLTIP));
        }
        else if (GuiAction.LABEL.equals(evt.getPropertyName())) {
            putValue(Action.NAME, guiAction.getValue(GuiAction.LABEL));
        }
    }
}
