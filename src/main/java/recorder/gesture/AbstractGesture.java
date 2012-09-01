/*
 * codjo.net
 *
 * Common Apache License 2.0
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
