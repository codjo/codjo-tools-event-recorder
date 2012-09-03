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
package recorder.gesture;
import javax.swing.*;
import javax.swing.tree.TreePath;
import recorder.component.FindStrategyId;
import recorder.event.GuiEvent;
import recorder.event.GuiEventList;
import recorder.event.GuiEventType;
import recorder.event.TreeEventData;
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
import recorder.result.StatementList;
/**
 * Detect row selection in a JTable.
 */
class SelectRow extends AbstractGesture {
    private static final String SELECT_TAG = "select";


    SelectRow() {
        super(new GuiEventType[]{GuiEventType.TABLE_CLICK, GuiEventType.LIST_CLICK, GuiEventType.TREE_CLICK},
              FindStrategyId.BY_NAME);
    }


    @Override
    protected void receiveImpl(GuiEventList list, StatementList resultList) {
        GuiEvent event = list.peek();

        if (isTreeEventWithoutSelect(event)) {
            return;
        }

        list.pop();

        DefaultStatement result =
              new DefaultStatement(SELECT_TAG, buildAttributes(event));

        if (result.isEquivalentTo(resultList.lastResult())) {
            return;
        }

        resultList.add(result);
    }


    private AttributeList buildAttributes(GuiEvent event) {
        AttributeList attributes = new AttributeList();
        attributes.put("name", event.getSource().getName());
        if (GuiEventType.TREE_CLICK == event.getType()) {
            TreeEventData treeEventData = ((TreeEventData)event.getValue());
            JTree tree = (JTree)event.getSource().getSwingComponent();

            attributes.put("path", pathToString(tree, treeEventData.getPath()));
        }
        else {
            attributes.put("row", event.getValue());
        }
        return attributes;
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


    private boolean isTreeEventWithoutSelect(GuiEvent event) {
        return GuiEventType.TREE_CLICK == event.getType()
               && !((TreeEventData)event.getValue()).isSelected();
    }
}
