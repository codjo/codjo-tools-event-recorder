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
import javax.swing.*;
import org.junit.Assert;
import org.junit.Test;
import recorder.component.GuiComponent;
import recorder.component.GuiComponentFactory;
/**

 */
public class GuiEventTest {
    @Test
    public void test_equals_badclass() throws Exception {
        GuiEvent event = newGuiEvent(GuiEventType.BUTTON_CLICK, new JButton());
        Assert.assertFalse(event.equals("notGuiEvent"));
    }


    @Test
    public void test_equals() throws Exception {
        JButton source = new JButton();
        GuiEventType type = GuiEventType.BUTTON_CLICK;

        Assert.assertTrue("Egaux", newGuiEvent(type, source).equals(newGuiEvent(type, source)));
        Assert.assertEquals("même hashcode", newGuiEvent(type, source).hashCode(),
                            newGuiEvent(type, source).hashCode());

        Assert.assertFalse("Pas le même type",
                           newGuiEvent(type, source).equals(newGuiEvent(GuiEventType.KEY, source)));

        Assert.assertFalse("Pas la même source",
                           newGuiEvent(type, source).equals(newGuiEvent(type, new JButton())));

        Assert.assertFalse("Pas la même value",
                           newGuiEvent(type, source, "other").equals(newGuiEvent(type, source, "value")));
    }


    private static GuiEvent newGuiEvent(GuiEventType eventType, JComponent source) {
        return new GuiEvent(eventType, toGui(source));
    }


    private static GuiEvent newGuiEvent(GuiEventType eventType, JComponent source,
                                        Object value) {
        return new GuiEvent(eventType, toGui(source), value);
    }


    private static GuiComponent toGui(JComponent field) {
        return GuiComponentFactory.newGuiComponent(field);
    }
}
