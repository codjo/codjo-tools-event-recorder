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
