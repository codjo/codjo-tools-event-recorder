/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.event;

import recorder.component.GuiComponent;
public class GuiEvent {
    private final GuiEventType type;
    private final GuiComponent source;
    private Object value;


    public GuiEvent(GuiEventType eventType, GuiComponent source) {
        this.type = eventType;
        this.source = source;
    }


    public GuiEvent(GuiEventType eventType, GuiComponent source, Object value) {
        this.type = eventType;
        this.source = source;
        this.value = value;
    }


    public Object getValue() {
        return value;
    }


    public GuiEventType getType() {
        return type;
    }


    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GuiEvent)) {
            return false;
        }

        final GuiEvent event = (GuiEvent)obj;

        if (!source.equals(event.source)) {
            return false;
        }
        if (!type.equals(event.type)) {
            return false;
        }
        //noinspection RedundantIfStatement
        if (value != null ? !value.equals(event.value) : event.value != null) {
            return false;
        }

        return true;
    }


    public int hashCode() {
        return source.hashCode();
    }


    public GuiComponent getSource() {
        return source;
    }


    public String toString() {
        return "GuiEvent{" + "type=" + type + ", source=" + source + ", value=" + value
               + "}";
    }
}
