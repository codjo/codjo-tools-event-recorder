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
import javax.swing.tree.TreePath;
import recorder.gui.action.GuiAction;
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
/**
 * Creates tag assertTree.
 */
class AssertTree extends AbstractAssert {
    static final String ID = "assertion.tree";
    private AssertContext context;


    AssertTree(AssertContext assertContext) {
        super(ID, COMPONENT_ASSERT);
        putValue(GuiAction.LABEL, "AssertTree");
        putValue(GuiAction.TOOLTIP, resourceBundle.getString("assert.tree-node"));
        this.context = assertContext;
    }


    public void execute() {
        if (!isAllowed()) {
            return;
        }
        JTree tree = (JTree)context.getSource();
        TreePath path =
              tree.getClosestPathForLocation(context.getPoint().x, context.getPoint().y);

        AttributeList attributes = new AttributeList();
        attributes.put("name", tree.getName());
        attributes.put("path", pathToString(tree, path));
        attributes.put("exists", "true");

        context.postAssert(new DefaultStatement("assertTree", attributes));
    }


    @Override
    public void update() {
        this.setEnabled(isAllowed());
    }


    private boolean isAllowed() {
        if (!(context.getSource() instanceof JTree)) {
            return false;
        }
        JTree tree = (JTree)context.getSource();
        TreePath path =
              tree.getClosestPathForLocation(context.getPoint().x, context.getPoint().y);

        return path != null;
    }


    private static String pathToString(JTree tree, TreePath treePath) {
        StringBuilder buffer = new StringBuilder();

        Object[] path = treePath.getPath();

        for (int i = 0; i < path.length; i++) {
            buffer.append(tree.convertValueToText(path[i], false, true, true, i, false));
            if (i + 1 < path.length) {
                buffer.append(":");
            }
        }
        return buffer.toString();
    }
}
