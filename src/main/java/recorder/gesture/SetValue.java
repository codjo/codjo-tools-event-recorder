/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gesture;
import javax.swing.text.JTextComponent;
import recorder.component.FindStrategyId;
import recorder.component.GuiComponent;
import recorder.event.GuiEvent;
import recorder.event.GuiEventList;
import recorder.event.GuiEventType;
import recorder.result.Statement;
import recorder.result.StatementList;
/**
 * Detect a value change in a TextField, JComboBox, ...
 */
class SetValue extends AbstractGesture {
    SetValue() {
        super(new GuiEventType[]{GuiEventType.KEY, GuiEventType.COMBO_FOCUS_LOST, GuiEventType.CHECKBOX_CLICK},
              FindStrategyId.BY_NAME);
    }


    @Override
    protected void receiveImpl(GuiEventList list, StatementList resultList) {
        GuiEvent event = list.peek();

        if (GuiEventType.KEY == event.getType()
            && event.getSource().isA(JTextComponent.class)) {
            list.pop();
            proceedKeyEvent(event, resultList);
        }
        else if (GuiEventType.COMBO_FOCUS_LOST == event.getType()) {
            list.pop();

            GuiEvent prevFocusGain =
                  list.findPrevious(new GuiEvent(GuiEventType.COMBO_FOCUS_GAIN,
                                                 event.getSource()));

            if (prevFocusGain == null || !sameValue(event, prevFocusGain)) {
                resultList.add(buildResult(event.getSource(), event.getValue()));
            }
        }
        else if (GuiEventType.CHECKBOX_CLICK == event.getType()) {
            list.pop();

            resultList.add(buildResult(event.getSource(), event.getValue()));
        }
    }


    private boolean sameValue(GuiEvent event, GuiEvent prevFocusGain) {
        //noinspection SimplifiableIfStatement
        if (event.getValue() == prevFocusGain.getValue()) {
            return true;
        }
        return event.getValue() != null && prevFocusGain.getValue() != null
               && prevFocusGain.getValue().equals(event.getValue());
    }


    private void proceedKeyEvent(GuiEvent event, StatementList resultList) {
        GuiComponent component = event.getSource();
        Object value = event.getValue();

        Statement last = resultList.lastResult();

        if (last instanceof SetValueStatement
            && ((SetValueStatement)last).getComponent().equals(component)) {
            ((SetValueStatement)last).setValue(value);
        }
        else {
            resultList.add(buildResult(component, value));
        }
    }


    private Statement buildResult(GuiComponent comp, Object value) {
        return new SetValueStatement(comp, value);
    }


    /**
     * Translate the gesture in XML.
     */
    private static class SetValueStatement implements Statement {
        private GuiComponent component;
        private Object value;


        SetValueStatement(GuiComponent component, Object value) {
            this.component = component;
            this.value = value;
        }


        public boolean isEquivalentTo(Statement stmt) {
            return false;
        }


        public String toXml() {
            return "<setValue name=\"" + component.getName() + "\" value=\"" + value
                   + "\"/>";
        }


        public GuiComponent getComponent() {
            return component;
        }


        public void setValue(Object value) {
            this.value = value;
        }
    }
}
