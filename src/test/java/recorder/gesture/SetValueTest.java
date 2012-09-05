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
public class SetValueTest {
    private GuiEventList list;
    private StatementList result;
    private SetValue setValueGesture;


    @Test
    public void test_combo() {
        JComboBox field = new JComboBox();
        field.setName("myfield");

        list.addEvent(newGuiEvent(GuiEventType.COMBO_FOCUS_LOST, field, "bb"));

        setValueGesture.receive(list, result);

        Assert.assertEquals("Les events sont consommés", 0, list.size());
        Assert.assertEquals("<setValue name=\"myfield\" value=\"bb\"/>", result.toXml());
    }


    @Test
    public void test_combo_change() {
        JComboBox field = new JComboBox();
        field.setName("myfield");

        list.addEvent(newGuiEvent(GuiEventType.COMBO_FOCUS_GAIN, field, "aa"));
        list.addEvent(newGuiEvent(GuiEventType.COMBO_FOCUS_GAIN, new JComboBox(), "bb"));
        list.addEvent(newGuiEvent(GuiEventType.COMBO_FOCUS_LOST, new JComboBox(), "bb"));
        list.addEvent(newGuiEvent(GuiEventType.COMBO_FOCUS_LOST, field, "bb"));

        setValueGesture.receive(list, result);
        Assert.assertEquals("Rien n'est consommé", 4, list.size());

        // Consomme les 3 premiers events pour tomber sur COMBO_FOCUS_LOST de field.
        list.pop();
        list.pop();
        list.pop();

        setValueGesture.receive(list, result);

        Assert.assertEquals("Les events sont consommés", 0, list.size());
        Assert.assertEquals("<setValue name=\"myfield\" value=\"bb\"/>", result.toXml());
    }


    @Test
    public void test_combo_nochange() {
        JComboBox field = new JComboBox();
        field.setName("myfield");

        list.addEvent(newGuiEvent(GuiEventType.COMBO_FOCUS_GAIN, field, "aa"));
        list.addEvent(newGuiEvent(GuiEventType.COMBO_FOCUS_GAIN, field, "bb"));
        list.addEvent(newGuiEvent(GuiEventType.COMBO_FOCUS_GAIN, new JComboBox(), "jj"));
        list.addEvent(newGuiEvent(GuiEventType.COMBO_FOCUS_LOST, new JComboBox(), "ii"));
        list.addEvent(newGuiEvent(GuiEventType.COMBO_FOCUS_LOST, field, "bb"));

        setValueGesture.receive(list, result);
        Assert.assertEquals("Rien n'est consommé", 5, list.size());

        // Consomme les 4 premiers events pour tomber sur COMBO_FOCUS_LOST de field.
        list.pop();
        list.pop();
        list.pop();
        list.pop();

        setValueGesture.receive(list, result);

        Assert.assertEquals("Les events sont consommés", 0, list.size());
        Assert.assertEquals("", result.toXml());
    }


    @Test
    public void test_checkbox() {
        JCheckBox field = new JCheckBox();
        field.setName("myfield");

        list.addEvent(newGuiEvent(GuiEventType.CHECKBOX_CLICK, field, Boolean.TRUE));

        setValueGesture.receive(list, result);

        Assert.assertEquals("<setValue name=\"myfield\" value=\"true\"/>", result.toXml());
    }


    @Test
    public void test_textField() {
        JTextField field = new JTextField();
        field.setName("myfield");

        list.addEvent(newGuiEvent(GuiEventType.KEY, field, "une valeur"));

        setValueGesture.receive(list, result);

        Assert.assertEquals("Les events sont consommés", 0, list.size());
        Assert.assertEquals("<setValue name=\"myfield\" value=\"une valeur\"/>", result.toXml());
    }


    @Test
    public void test_textField_multiple() {
        JTextField field = new JTextField();
        field.setName("myfield");

        list.addEvent(newGuiEvent(GuiEventType.KEY, field, "une v"));
        list.addEvent(newGuiEvent(GuiEventType.KEY, field, "une va"));
        list.addEvent(newGuiEvent(GuiEventType.KEY, field, "une vap"));
        list.addEvent(newGuiEvent(GuiEventType.KEY, field, "une va"));
        list.addEvent(newGuiEvent(GuiEventType.KEY, field, "une val"));
        list.addEvent(newGuiEvent(GuiEventType.KEY, field, "une valeur"));

        setValueGesture.receive(list, result);
        Assert.assertEquals("Un event est consommé", 5, list.size());
        Assert.assertEquals("<setValue name=\"myfield\" value=\"une v\"/>", result.toXml());

        setValueGesture.receive(list, result);
        Assert.assertEquals("Un event est consommé", 4, list.size());
        Assert.assertEquals("<setValue name=\"myfield\" value=\"une va\"/>", result.toXml());

        setValueGesture.receive(list, result);
        setValueGesture.receive(list, result);
        setValueGesture.receive(list, result);
        setValueGesture.receive(list, result);
        Assert.assertEquals("Les events sont consommés", 0, list.size());
        Assert.assertEquals("<setValue name=\"myfield\" value=\"une valeur\"/>", result.toXml());
    }


    @Test
    public void test_textField_multiple_notSame() {
        JTextField field = new JTextField();
        field.setName("myfield");

        list.addEvent(newGuiEvent(GuiEventType.KEY, field, "une val"));
        list.addEvent(newGuiEvent(GuiEventType.KEY, new JTextField(), "autre"));

        setValueGesture.receive(list, result);

        Assert.assertEquals("Les events sont consommés", 1, list.size());
        Assert.assertEquals("<setValue name=\"myfield\" value=\"une val\"/>", result.toXml());
    }


    @Before
    public void setUp() {
        list = new GuiEventList();
        result = new StatementList();
        setValueGesture = new SetValue();
    }


    private static GuiEvent newGuiEvent(GuiEventType eventType, JComponent source,
                                        Object value) {
        return new GuiEvent(eventType, toGui(source), value);
    }


    private static GuiComponent toGui(JComponent field) {
        return GuiComponentFactory.newGuiComponent(field);
    }
}
