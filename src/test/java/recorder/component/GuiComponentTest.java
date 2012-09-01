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
package recorder.component;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import org.junit.Assert;
import org.junit.Test;
/**

 */
public class GuiComponentTest {
    @Test
    public void test_isA() throws Exception {
        GuiComponent guiComponent = GuiComponentFactory.newGuiComponent(new JTextField());
        Assert.assertTrue(guiComponent.isA(JTextComponent.class));
        Assert.assertTrue(guiComponent.isA(JTextField.class));
        Assert.assertFalse(guiComponent.isA(JComboBox.class));
    }


    @Test
    public void test_equal() throws Exception {
        JTextField fieldA = new JTextField();
        JTextField fieldB = new JTextField();

        GuiComponent compA = GuiComponentFactory.newGuiComponent(fieldA);
        Assert.assertTrue("GuiComp(a) = GuiComp(a)", compA.equals(compA));
        Assert.assertTrue("new GuiComp(a) = new GuiComp(a)",
                          compA.equals(GuiComponentFactory.newGuiComponent(fieldA)));
        Assert.assertEquals("[hashcode] new GuiComp(a) = new GuiComp(a)", compA.hashCode(),
                            GuiComponentFactory.newGuiComponent(fieldA).hashCode());

        Assert.assertFalse("new GuiComp(b) != new GuiComp(a)",
                           GuiComponentFactory.newGuiComponent(fieldB).equals(GuiComponentFactory
                                                                                    .newGuiComponent(fieldA)));
        Assert.assertFalse("new GuiComp(b) != 'carotte'",
                           GuiComponentFactory.newGuiComponent(fieldB).equals("carotte"));
    }


    @Test
    public void test_canBeFoundWith() throws Exception {
        JButton textField = new JButton();

        GuiComponent gui = GuiComponentFactory.newGuiComponent(textField);

        textField.setName("gui.name");
        Assert.assertTrue("Recherche 'nom' : Ok", gui.canBeFoundWith(FindStrategyId.BY_NAME));
        Assert.assertTrue("Recherche 'label' : NOk", !gui.canBeFoundWith(FindStrategyId.BY_LABEL));

        textField.setText("gui.label");
        Assert.assertTrue("Recherche 'nom' : Ok", gui.canBeFoundWith(FindStrategyId.BY_NAME));
        Assert.assertTrue("Recherche 'label' : Ok", gui.canBeFoundWith(FindStrategyId.BY_LABEL));
    }


    @Test
    public void test_findByAccessibleContext() throws Exception {
        JTextField textField = new JTextField();

        GuiComponent guiComponent = GuiComponentFactory.newGuiComponent(textField);
        Assert.assertFalse("Component cannot be found", guiComponent.isFindable());
        Assert.assertEquals(FindStrategyId.NONE, guiComponent.getBestFindStrategyId());

        textField.getAccessibleContext().setAccessibleName("Dododidon");

        Assert.assertTrue("Component can be found via the context", guiComponent.isFindable());
        Assert.assertEquals(FindStrategyId.BY_ACCESSIBLE_CONTEXT, guiComponent.getBestFindStrategyId());
    }


    @Test
    public void test_findByName() throws Exception {
        JTextField textField = new JTextField();

        GuiComponent guiComponent = GuiComponentFactory.newGuiComponent(textField);
        Assert.assertFalse("Component cannot be found", guiComponent.isFindable());

        textField.setName("Dododidon");

        Assert.assertTrue("Component can be found via the name", guiComponent.isFindable());
        Assert.assertEquals(FindStrategyId.BY_NAME, guiComponent.getBestFindStrategyId());
    }


    @Test
    public void test_findByLabel_menuItem() throws Exception {
        assertFindByName(new JMenuItem());
    }


    @Test
    public void test_findByLabel_button() throws Exception {
        assertFindByName(new JButton());
    }


    private void assertFindByName(AbstractButton comp) {
        GuiComponent guiComponent = GuiComponentFactory.newGuiComponent(comp);
        Assert.assertFalse("Component cannot be found", guiComponent.isFindable());

        comp.setText("Bobo");

        Assert.assertTrue("Component can be found via the label", guiComponent.isFindable());
        Assert.assertEquals(FindStrategyId.BY_LABEL, guiComponent.getBestFindStrategyId());
        Assert.assertEquals("Bobo", guiComponent.getLabel());
    }
}
