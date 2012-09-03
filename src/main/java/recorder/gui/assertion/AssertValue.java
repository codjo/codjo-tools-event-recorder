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
import javax.swing.text.JTextComponent;
import recorder.gui.action.GuiAction;
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
/**
 * Creates tag assertValue.
 */
class AssertValue extends AbstractAssert {
    static final String ID = "assertion.value";
    private AssertContext context;


    AssertValue(AssertContext assertContext) {
        super(ID, COMPONENT_ASSERT);
        putValue(GuiAction.LABEL, "AssertValue");
        putValue(GuiAction.TOOLTIP, resourceBundle.getString("assert.value"));
        this.context = assertContext;
    }


    public void execute() {
        Object component = context.getSource();
        if (!(component instanceof JTextComponent
              || component instanceof JComboBox
              || component instanceof JCheckBox)) {
            return;
        }

        AttributeList attributes = new AttributeList();
        attributes.put("name", ((JComponent)component).getName());

        if (component instanceof JTextComponent) {
            attributes.put("expected", ((JTextComponent)component).getText());
        }
        else if (component instanceof JCheckBox) {
            attributes.put("expected",
                           (((JCheckBox)component).isSelected() ? "true" : "false"));
        }
        else {
            attributes.put("expected", ((JComboBox)component).getSelectedItem());
        }

        context.postAssert(new DefaultStatement("assertValue", attributes));
    }


    @Override
    public void update() {
        Object component = context.getSource();
        this.setEnabled((component instanceof JTextComponent
                         || component instanceof JComboBox || component instanceof JCheckBox)
                        && context.isFindableComponent());
    }
}
