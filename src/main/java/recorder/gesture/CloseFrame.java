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
import java.awt.*;
import javax.swing.*;
import recorder.component.FindStrategyId;
import recorder.component.GuiComponent;
import recorder.event.GuiEvent;
import recorder.event.GuiEventList;
import recorder.event.GuiEventType;
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
import recorder.result.StatementList;
/**
 * Detect the closing of a JInternalFrame.
 *
 * <p> For the moment only the closing from the close button (in the title bar) is detected. </p>
 */
class CloseFrame extends AbstractGesture {
    CloseFrame() {
        super(GuiEventType.BUTTON_CLICK, FindStrategyId.BY_ACCESSIBLE_CONTEXT);
    }


    @Override
    protected void receiveImpl(GuiEventList list, StatementList resultList) {
        GuiEvent event = list.peek();
        JInternalFrame internalFrame = findInternalFrame(event.getSource());
        if (internalFrame != null && isCloseButton(event)) {
            list.pop();
            resultList.add(new DefaultStatement("closeFrame",
                                                AttributeList.singleton("title", internalFrame.getTitle())));
        }
    }


    private boolean isCloseButton(GuiEvent event) {
        String accessibleCloseName =
              UIManager.getString("InternalFrameTitlePane.closeButtonAccessibleName");

        return accessibleCloseName.equals(event.getSource().getAccessibleName());
    }


    private JInternalFrame findInternalFrame(GuiComponent source) {
        Container parent = source.getSwingComponent().getParent();
        while (parent != null && !(parent instanceof JInternalFrame)) {
            parent = parent.getParent();
        }
        return (JInternalFrame)parent;
    }
}
