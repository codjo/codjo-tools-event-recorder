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
import javax.swing.*;
import recorder.gui.action.GuiAction;
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
/**
 * Creates tag assertTable.
 */
class AssertTable extends AbstractAssert {
    static final String ID = "assertion.table";
    private AssertContext context;


    AssertTable(AssertContext assertContext) {
        super(ID, COMPONENT_ASSERT);
        putValue(GuiAction.LABEL, "AssertTable");
        putValue(GuiAction.TOOLTIP, resourceBundle.getString("assert.table-row"));
        this.context = assertContext;
    }


    public void execute() {
        if (!(context.getSource() instanceof JTable)) {
            return;
        }
        JTable table = (JTable)context.getSource();
        int rowAtPoint = table.rowAtPoint(context.getPoint());
        int colAtPoint = table.columnAtPoint(context.getPoint());

        AttributeList attributes = new AttributeList();
        attributes.put("name", table.getName());
        attributes.put("expected", table.getValueAt(rowAtPoint, colAtPoint));
        attributes.put("row", rowAtPoint);
        attributes.put("column", colAtPoint);

        context.postAssert(new DefaultStatement("assertTable", attributes));
    }


    @Override
    public void update() {
        this.setEnabled((context.getSource() instanceof JTable));
    }
}
