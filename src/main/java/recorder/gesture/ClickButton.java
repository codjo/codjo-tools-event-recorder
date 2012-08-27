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
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
import recorder.result.StatementList;
/**
 * Reconnaît un click sur un bouton.
 *
 * @version $Revision: 1.1.1.1 $
 */
class ClickButton extends AbstractGesture {
    ClickButton() {
        super(new GuiEventType[] {GuiEventType.BUTTON_CLICK},
            new FindStrategyId[] {FindStrategyId.BY_NAME, FindStrategyId.BY_LABEL});
    }

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
