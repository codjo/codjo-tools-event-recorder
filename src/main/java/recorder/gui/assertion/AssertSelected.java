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
package recorder.gui.assertion;
import java.awt.*;
import javax.swing.*;
import recorder.gui.action.GuiAction;
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
/**
 * Creates tag assertSelected.
 */
class AssertSelected extends AbstractAssert {
    static final String ID = "assertion.selected";
    private AssertContext context;


    AssertSelected(AssertContext assertContext) {
        super(ID, COMPONENT_ASSERT);
        putValue(GuiAction.LABEL, "AssertSelected");
        putValue(GuiAction.TOOLTIP, resourceBundle.getString("assert.list-selected"));

        this.context = assertContext;
    }


    public void execute() {
        if (!canHandleComponentInContext()) {
            return;
        }
        Handler handler = getHandler();
        Component component = context.getSource();
        int rowAtPoint = handler.determineRowAtPoint(component);

        AttributeList attributes = new AttributeList();
        attributes.put("name", component.getName());
        attributes.put("row", rowAtPoint);
        if (!handler.isRowSelected(component, rowAtPoint)) {
            attributes.put("expected", "false");
        }

        context.postAssert(new DefaultStatement("assertSelected", attributes));
    }


    @Override
    public void update() {
        this.setEnabled(canHandleComponentInContext());
    }


    private boolean canHandleComponentInContext() {
        return getHandler() != null;
    }


    private Handler getHandler() {
        if (context.getSource() instanceof JTable) {
            return new TableHandler();
        }
        if (context.getSource() instanceof JList) {
            return new ListHandler();
        }
        return null;
    }


    //------------------------------------------------------------------------------------------------------------------
    // Handlers for list components : JTable and JList
    //------------------------------------------------------------------------------------------------------------------
    private interface Handler {
        boolean isRowSelected(Component comp, int rowAtPoint);


        int determineRowAtPoint(Component comp);
    }

    private class TableHandler implements Handler {
        public boolean isRowSelected(Component comp, int rowAtPoint) {
            return ((JTable)comp).isRowSelected(rowAtPoint);
        }


        public int determineRowAtPoint(Component comp) {
            return ((JTable)comp).rowAtPoint(context.getPoint());
        }
    }

    private class ListHandler implements Handler {
        public boolean isRowSelected(Component comp, int rowAtPoint) {
            return ((JList)comp).getSelectionModel().isSelectedIndex(rowAtPoint);
        }


        public int determineRowAtPoint(Component comp) {
            return ((JList)comp).locationToIndex(context.getPoint());
        }
    }
}
