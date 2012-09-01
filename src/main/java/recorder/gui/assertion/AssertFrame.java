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
class AssertFrame extends AbstractAssert {
    static final String ID = "assertion.frame";
    private AssertContext context;


    AssertFrame(AssertContext assertContext) {
        super(ID, GLOBAL_ASSERT);
        putValue(GuiAction.LABEL, "AssertFrame");
        putValue(GuiAction.TOOLTIP, resourceBundle.getString("assert.window-title"));
        this.context = assertContext;
    }


    public void execute() {
        String title = findWindowTitle(context.getSource());

        if (title == null) {
            return;
        }

        context.postAssert(new DefaultStatement("assertFrame", AttributeList.singleton("title", title)));
    }


    @Override
    public void update() {
        putValue(GuiAction.LABEL, "AssertFrame titre='" + findWindowTitle(context.getSource()) + "'");
    }


    private String findWindowTitle(Component source) {
        if (source == null) {
            return null;
        }
        if (source instanceof JInternalFrame) {
            return ((JInternalFrame)source).getTitle();
        }
        if (source instanceof JFrame) {
            return ((JFrame)source).getTitle();
        }
        if (source instanceof JDialog) {
            return ((JDialog)source).getTitle();
        }
        return findWindowTitle(source.getParent());
    }
}
