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
import recorder.component.FindStrategyId;
import recorder.event.GuiEvent;
import recorder.event.GuiEventList;
import recorder.event.GuiEventType;
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
import recorder.result.StatementList;
/**
 * Detect a button click.
 */
class ClickButton extends AbstractGesture {
    ClickButton() {
        super(new GuiEventType[]{GuiEventType.BUTTON_CLICK},
              new FindStrategyId[]{FindStrategyId.BY_NAME, FindStrategyId.BY_LABEL});
    }


    @Override
    protected void receiveImpl(GuiEventList list, StatementList resultList) {
        GuiEvent event = list.pop();

        FindStrategyId bestFindStrategyId = event.getSource().getBestFindStrategyId();
        if (FindStrategyId.BY_NAME.equals(bestFindStrategyId)) {
            resultList.add(new DefaultStatement("click",
                                                AttributeList.singleton("name", event.getSource().getName())));
        }
        else if (FindStrategyId.BY_LABEL.equals(bestFindStrategyId)) {
            resultList.add(new DefaultStatement("click",
                                                AttributeList.singleton("label", event.getSource().getLabel())));
        }
    }
}
