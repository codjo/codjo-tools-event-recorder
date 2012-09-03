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
package recorder.gui.script;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.tree.TreePath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import recorder.Recorder;
import recorder.component.GuiComponentFactory;
import recorder.gui.util.Util;
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
import recorder.result.StatementList;
/**

 */
public class ScriptLogicTest {
    private ScriptLogic logic;
    private Recorder recorder;


    @Test
    public void test_contentIsATree() {
        Component comp = Util.findByName("script.tree.display", logic.getGui());
        Assert.assertTrue("Le composant est un arbre : ", comp instanceof JTree);
    }


    @Test
    public void test_display() {
        Assert.assertEquals("", uiDisplayedContent());

        recorder.postGestureResult(newStatement("nodeA"));

        Assert.assertEquals("[group, <nodeA/>]", uiDisplayedContent());

        recorder.postGestureResult(newStatement("nodeB"));

        Assert.assertEquals("[group, <nodeA/>]\n[group, <nodeB/>]", uiDisplayedContent());
    }


    @Test
    public void test_display_sameNode() {
        DefaultStatement nodeA1 = newStatement("nodeA");
        DefaultStatement nodeA2 = newStatement("nodeA");

        recorder.postGestureResult(newStatement("node"));
        recorder.postGestureResult(nodeA1);
        recorder.postGestureResult(nodeA2);

        Assert.assertEquals("[group, <node/>]\n[group, <nodeA/>]\n[group, <nodeA/>]",
                            uiDisplayedContent());

        StatementList root = recorder.getGestureResultList();

        int rowA2 = getGui().getRowForPath(new TreePath(new Object[]{root, nodeA2}));
        Assert.assertEquals("A2 est sur la ligne 2", 2, rowA2);

        int rowA1 = getGui().getRowForPath(new TreePath(new Object[]{root, nodeA1}));
        Assert.assertEquals("A1 est sur la ligne 1", 1, rowA1);
    }


    @Test
    public void test_display_remove() {
        Assert.assertEquals("", uiDisplayedContent());

        recorder.postGestureResult(newStatement("nodeA"));
        Assert.assertEquals("[group, <nodeA/>]", uiDisplayedContent());

        recorder.removeLastGesture();
        Assert.assertEquals("", uiDisplayedContent());
    }


    @Test
    public void test_display_alreadyInitialized() {
        Assert.assertEquals("", uiDisplayedContent());

        recorder.postGestureResult(newStatement("nodeA"));
        logic = new ScriptLogic(new ScriptGui(), recorder);

        Assert.assertEquals("[group, <nodeA/>]", uiDisplayedContent());
    }


    @Before
    public void setUp() {
        recorder = new Recorder(new GuiComponentFactory());
        logic = new ScriptLogic(new ScriptGui(), recorder);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Check tree");

        Recorder recorder = new Recorder(new GuiComponentFactory());
        ScriptLogic logic = new ScriptLogic(new ScriptGui(), recorder);
        recorder.postGestureResult(new DefaultStatement("bobo1", AttributeList.EMPTY_LIST));
        recorder.postGestureResult(new DefaultStatement("bobo2", AttributeList.EMPTY_LIST));
        recorder.postGestureResult(new DefaultStatement("bobo3", AttributeList.EMPTY_LIST));
        recorder.postGestureResult(new DefaultStatement("bobo3", AttributeList.EMPTY_LIST));

        frame.setContentPane(logic.getGui());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        frame.pack();
        frame.setSize(600, 50);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }


    private DefaultStatement newStatement(String tagName) {
        return new DefaultStatement(tagName, AttributeList.EMPTY_LIST);
    }


    private String uiDisplayedContent() {
        return Util.uiDisplayedContent(getGui());
    }


    private JTree getGui() {
        return (JTree)Util.findByName("script.tree.display", logic.getGui());
    }
}
