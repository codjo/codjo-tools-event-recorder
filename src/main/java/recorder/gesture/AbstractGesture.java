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
import recorder.result.StatementList;
abstract class AbstractGesture implements Gesture {
    private final GuiEventType[] handledType;
    private final FindStrategyId[] findStrategyId;


    protected AbstractGesture(GuiEventType type, FindStrategyId findStrategyId) {
        this(new GuiEventType[]{type}, findStrategyId);
    }


    protected AbstractGesture(GuiEventType[] type, FindStrategyId findStrategyId) {
        this(type, new FindStrategyId[]{findStrategyId});
    }


    protected AbstractGesture(GuiEventType[] type, FindStrategyId[] findStrategyId) {
        this.handledType = type;
        this.findStrategyId = findStrategyId;
    }


    public final void receive(GuiEventList eventList, StatementList resultList) {
        if (!canHandleEvent(eventList.peek())) {
            return;
        }

        receiveImpl(eventList, resultList);
    }


    protected boolean canHandleEvent(GuiEvent event) {
        if (!canHandleEventType(event)) {
            return false;
        }
        //noinspection RedundantIfStatement
        if (!canHandleFindStrategy(event)) {
            return false;
        }
        return true;
    }


    private boolean canHandleEventType(GuiEvent event) {
        for (GuiEventType type : handledType) {
            if (type.equals(event.getType())) {
                return true;
            }
        }
        return false;
    }


    private boolean canHandleFindStrategy(GuiEvent event) {
        for (FindStrategyId strategy : findStrategyId) {
            if (event.getSource().canBeFoundWith(strategy)) {
                return true;
            }
        }
        return false;
    }


    protected abstract void receiveImpl(GuiEventList eventList, StatementList resultList);
}
