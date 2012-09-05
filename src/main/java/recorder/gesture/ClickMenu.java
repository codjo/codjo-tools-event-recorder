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
import recorder.event.GuiEvent;
import recorder.event.GuiEventList;
import recorder.event.GuiEventType;
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
import recorder.result.StatementList;
/**
 * Detect the selection of a menu.
 */
class ClickMenu extends AbstractGesture {
    ClickMenu() {
        super(GuiEventType.MENU_CLICK, FindStrategyId.BY_LABEL);
    }


    @Override
    protected void receiveImpl(GuiEventList list, StatementList resultList) {
        GuiEvent event = list.peek();

        if (!event.getSource().isA(JMenu.class) && invokedFromMenuBar(event)) {
            list.pop();

            final JMenuItem item = (JMenuItem)event.getSource().getSwingComponent();

            String menuPath = buildMenuPath(item.getParent());

            resultList.add(new DefaultStatement("click",
                                                AttributeList.singleton("menu", menuPath + item.getText())));
        }
    }


    private String buildMenuPath(Container parent) {
        if (parent != null && (parent instanceof JPopupMenu)) {
            Component invoker = ((JPopupMenu)parent).getInvoker();

            if (invoker != null && invoker instanceof JMenu) {
                return buildMenuPath(invoker.getParent()) + ((JMenu)invoker).getText()
                       + ":";
            }
        }

        return "";
    }


    private boolean invokedFromMenuBar(GuiEvent event) {
        final JMenuItem item = (JMenuItem)event.getSource().getSwingComponent();
        JPopupMenu menu = (JPopupMenu)item.getParent();

        return menu.getInvoker() instanceof JMenu;
    }
}
