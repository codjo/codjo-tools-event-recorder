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
import javax.swing.tree.TreePath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import recorder.component.GuiComponentFactory;
import recorder.event.GuiEvent;
import recorder.event.GuiEventList;
import recorder.event.GuiEventType;
import recorder.event.TreeEventData;
import recorder.result.StatementList;
/**

 */
public class SelectRowTest {
    private StatementList result;
    private GuiEventList list;
    private SelectRow selectRowGesture;


    @Test
    public void test_receive_badEvent() throws Exception {
        list.addEvent(new GuiEvent(GuiEventType.BUTTON_CLICK,
                                   GuiComponentFactory.newGuiComponent(new JButton())));

        selectRowGesture.receive(list, result);

        Assert.assertEquals("Click n'est pas consommé", 1, list.size());
    }


    @Test
    public void test_receive_table() throws Exception {
        list.addEvent(newTableClickRowEvent("table.name", "1"));

        selectRowGesture.receive(list, result);

        Assert.assertEquals("Click est consommé", 0, list.size());
        Assert.assertEquals("<select name=\"table.name\" row=\"1\"/>", result.toXml());
    }


    @Test
    public void test_receive_list() throws Exception {
        list.addEvent(newListClickRowEvent("list.name", "1"));

        selectRowGesture.receive(list, result);

        Assert.assertEquals("Click est consommé", 0, list.size());
        Assert.assertEquals("<select name=\"list.name\" row=\"1\"/>", result.toXml());
    }


    @Test
    public void test_receive_tree() throws Exception {
        list.addEvent(newTreeClickRowEvent("tree.name", new TreePath("node"), true));

        selectRowGesture.receive(list, result);

        Assert.assertEquals("Click est consommé", 0, list.size());
        Assert.assertEquals("<select name=\"tree.name\" path=\"node\"/>", result.toXml());
    }


    @Test
    public void test_receive_tree_notSelected() throws Exception {
        list.addEvent(newTreeClickRowEvent("tree.name", new TreePath("node"), false));

        selectRowGesture.receive(list, result);

        Assert.assertEquals("Click n'est pas consommé", 1, list.size());
        Assert.assertEquals("", result.toXml());
    }


    @Test
    public void test_receive_cumulative() throws Exception {
        JTable source = buildTable("table.name");

        list.addEvent(newClickRowEvent(source, "1"));
        list.addEvent(newClickRowEvent(source, "1"));
        list.addEvent(newClickRowEvent(source, "2"));

        // 2 click sur la même ligne sont cumulatif (même selection)
        selectRowGesture.receive(list, result);
        selectRowGesture.receive(list, result);
        selectRowGesture.receive(list, result);

        Assert.assertEquals("Click est consommé", 0, list.size());
        Assert.assertEquals("<select name=\"table.name\" row=\"1\"/>\n"
                            + "<select name=\"table.name\" row=\"2\"/>", result.toXml());
    }


    @Before
    public void setUp() throws Exception {
        result = new StatementList();
        list = new GuiEventList();
        selectRowGesture = new SelectRow();
    }


    private GuiEvent newTableClickRowEvent(String name, String idx) {
        return new GuiEvent(GuiEventType.TABLE_CLICK,
                            GuiComponentFactory.newGuiComponent(buildTable(name)), Integer.decode(idx));
    }


    private GuiEvent newTreeClickRowEvent(String name, TreePath path, boolean selected) {
        return new GuiEvent(GuiEventType.TREE_CLICK,
                            GuiComponentFactory.newGuiComponent(buildTree(name)),
                            new TreeEventData(path, false, selected));
    }


    private GuiEvent newListClickRowEvent(String name, String idx) {
        return new GuiEvent(GuiEventType.LIST_CLICK,
                            GuiComponentFactory.newGuiComponent(buildList(name)), Integer.decode(idx));
    }


    private GuiEvent newClickRowEvent(JComponent source, String idx) {
        return new GuiEvent(GuiEventType.TABLE_CLICK,
                            GuiComponentFactory.newGuiComponent(source), Integer.decode(idx));
    }


    private JTable buildTable(String name) {
        JTable table = new JTable();
        table.setName(name);
        return table;
    }


    private JList buildList(String name) {
        JList newList = new JList();
        newList.setName(name);
        return newList;
    }


    private JTree buildTree(String name) {
        JTree tree = new JTree();
        tree.setName(name);
        return tree;
    }
}
