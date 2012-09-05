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
