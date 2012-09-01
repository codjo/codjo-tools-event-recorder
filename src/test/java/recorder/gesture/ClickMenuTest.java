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
import javax.swing.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import recorder.component.GuiComponent;
import recorder.component.GuiComponentFactory;
import recorder.event.GuiEvent;
import recorder.event.GuiEventList;
import recorder.event.GuiEventType;
import recorder.result.StatementList;
/**

 */
public class ClickMenuTest {
    private StatementList result;
    private ClickMenu clickMenuGesture;
    private GuiEventList list;


    @Test
    public void test_menu() throws Exception {
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        file.add(open);

        list.addEvent(new GuiEvent(GuiEventType.MENU_CLICK, toGui(file)));
        list.addEvent(new GuiEvent(GuiEventType.MENU_CLICK, toGui(open)));

        clickMenuGesture.receive(list, result);

        Assert.assertEquals("Click sur file n'est pas consommé", 2, list.size());

        list.pop();
        clickMenuGesture.receive(list, result);
        Assert.assertEquals("Click sur open est consommé", 0, list.size());

        Assert.assertEquals("<click menu=\"File:Open\"/>", result.toXml());
    }


    @Test
    public void test_deep_menu() throws Exception {
        JMenu file = new JMenu("File");
        JMenu option = new JMenu("Option");
        JMenuItem changeLF = new JMenuItem("ChangeLF");
        file.add(option);
        option.add(changeLF).setName("bobo");

        list.addEvent(new GuiEvent(GuiEventType.MENU_CLICK, toGui(changeLF)));

        clickMenuGesture.receive(list, result);

        Assert.assertEquals("Click sur changeLF est consommé", 0, list.size());
        Assert.assertEquals("<click menu=\"File:Option:ChangeLF\"/>", result.toXml());
    }


    @Test
    public void test_popupmenu() throws Exception {
        JPopupMenu popup = new JPopupMenu();
        popup.setInvoker(new JTextField());

        JMenuItem popupItem = new JMenuItem("Open");
        popup.add(popupItem);

        list.addEvent(new GuiEvent(GuiEventType.MENU_CLICK, toGui(popupItem)));

        clickMenuGesture.receive(list, result);

        Assert.assertEquals("Click sur popupItem n'est pas consommé", 1, list.size());
        Assert.assertEquals("", result.toXml());
    }


    @Before
    public void setUp() throws Exception {
        result = new StatementList();
        clickMenuGesture = new ClickMenu();
        list = new GuiEventList();
    }


    private GuiComponent toGui(JComponent file) {
        return GuiComponentFactory.newGuiComponent(file);
    }
}
