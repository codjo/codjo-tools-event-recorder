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
import org.junit.Before;
import org.junit.Test;
import recorder.component.GuiComponent;
import recorder.component.GuiComponentFactory;
/**

 */
public class GuiEventListTest {
    private GuiEventList list;


    @Test
    public void test_findPrevious() throws Exception {
        GuiComponent srcA = toGui(new JTextField());
        GuiComponent srcB = toGui(new JTextField());

        GuiEvent eventA = new GuiEvent(GuiEventType.KEY, srcA);
        GuiEvent eventB = new GuiEvent(GuiEventType.KEY, srcB);
        GuiEvent eventC = new GuiEvent(GuiEventType.MENU_CLICK, srcA);

        list.addEvent(eventA);
        list.addEvent(eventB);
        list.addEvent(eventC);

        list.pop();
        list.pop();
        list.pop();

        Assert.assertEquals(eventC,
                            list.findPrevious(new GuiEvent(GuiEventType.MENU_CLICK, srcA)));
        Assert.assertEquals(eventA, list.findPrevious(new GuiEvent(GuiEventType.KEY, srcA)));

        Assert.assertEquals(eventC, list.findPrevious(new GuiEvent(null, srcA)));
        Assert.assertEquals(eventB, list.findPrevious(new GuiEvent(GuiEventType.KEY, null)));

        Assert.assertNull(list.findPrevious(new GuiEvent(GuiEventType.COMBO_FOCUS_GAIN, null)));
    }


    @Test
    public void test_findPrevious_notSameInstance()
          throws Exception {
        JTextField swingA = new JTextField();
        GuiComponent compA = toGui(swingA);
        GuiComponent sameCompA = toGui(swingA);

        GuiEvent eventA = new GuiEvent(GuiEventType.KEY, compA);
        GuiEvent eventC = new GuiEvent(GuiEventType.MENU_CLICK, compA);

        list.addEvent(eventA);
        list.addEvent(eventC);

        list.pop();
        list.pop();

        Assert.assertEquals(eventC,
                            list.findPrevious(new GuiEvent(GuiEventType.MENU_CLICK, sameCompA)));
    }


    @Test
    public void test_peek_empty() throws Exception {
        Assert.assertNull("liste vide : peek renvoie null", list.peek());
        GuiEvent event = new GuiEvent(GuiEventType.BUTTON_CLICK, null);
        list.addEvent(event);
        Assert.assertSame("liste non vide : peek renvoie le sommet de la pile", event,
                          list.peek());
        list.pop();
        Assert.assertNull("liste de nouveau vide : peek renvoie null", list.peek());
    }


    @Test
    public void test_consumedEvent_sizeLimit_default()
          throws Exception {
        Assert.assertEquals(100, list.getConsumedEventLimit());
    }


    @Test
    public void test_consumedEvent_sizeLimit() throws Exception {
        list.setConsumedEventLimit(2);

        GuiEvent event1 = new GuiEvent(GuiEventType.BUTTON_CLICK, toGui(new JButton()));
        list.addEvent(event1);
        list.addEvent(new GuiEvent(GuiEventType.BUTTON_CLICK, toGui(new JButton())));
        list.addEvent(new GuiEvent(GuiEventType.BUTTON_CLICK, toGui(new JButton())));

        list.pop();

        Assert.assertEquals(event1, list.findPrevious(new GuiEvent(null, event1.getSource())));

        list.pop();
        list.pop();

        Assert.assertNull("La liste � atteint sa limite de 2 (l��v�nement � disparu)",
                          list.findPrevious(new GuiEvent(null, event1.getSource())));
    }


    @Before
    public void setUp() throws Exception {
        list = new GuiEventList();
    }


    private GuiComponent toGui(JComponent field) {
        return GuiComponentFactory.newGuiComponent(field);
    }
}
