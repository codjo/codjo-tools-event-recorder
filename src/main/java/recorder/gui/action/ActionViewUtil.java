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
