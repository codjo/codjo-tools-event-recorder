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
package recorder.gui.assertion;
import javax.swing.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import recorder.component.GuiComponentFactory;
import recorder.result.Statement;
/**

 */
public class AssertValueTest {
    private AssertValue assertValue;
    private MockAssertContext context;


    @Before
    public void setUp() throws Exception {
        context = new MockAssertContext();
        assertValue = new AssertValue(context);
    }


    @Test
    public void test_type() throws Exception {
        Assert.assertEquals(AbstractAssert.COMPONENT_ASSERT, assertValue.getAssertType());
    }


    @Test
    public void test_assert_value_jTextField() throws Exception {
        JTextField source = new JTextField("Ma valeur");
        source.setName("Mon textField");

        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertValue.execute();

        Statement resultAssert = context.getPostedAssert();
        Assert.assertNotNull("un assertion est défini", resultAssert);
        Assert.assertEquals("<assertValue name=\"Mon textField\" expected=\"Ma valeur\"/>",
                            resultAssert.toXml());
    }


    @Test
    public void test_assert_value_jCheckBox() throws Exception {
        JCheckBox source = new JCheckBox();
        source.setName("Mon textField");
        source.setSelected(true);

        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertValue.execute();

        Statement resultAssert = context.getPostedAssert();
        Assert.assertNotNull("un assertion est défini", resultAssert);
        Assert.assertEquals("<assertValue name=\"Mon textField\" expected=\"true\"/>",
                            resultAssert.toXml());
    }


    @Test
    public void test_assert_value_jComboBox() throws Exception {
        JComboBox source = new JComboBox(new String[]{"Val1", "Val2"});
        source.setSelectedItem("Val2");
        source.setName("Mon comboBox");

        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertValue.execute();

        Statement resultAssert = context.getPostedAssert();
        Assert.assertNotNull("un assertion est défini", resultAssert);
        Assert.assertEquals("<assertValue name=\"Mon comboBox\" expected=\"Val2\"/>",
                            resultAssert.toXml());
    }


    @Test
    public void test_assert_bad_source() throws Exception {
        JTable source = new JTable();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertValue.execute();

        Statement resultAssert = context.getPostedAssert();
        Assert.assertNull("un assertion n'est pas défini", resultAssert);
    }


    @Test
    public void test_update_disable() throws Exception {
        JTable source = new JTable();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertValue.update();

        Assert.assertFalse("Assert disable", assertValue.isEnabled());
    }


    @Test
    public void test_update_enable_jTextField() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertValue.update();

        Assert.assertFalse("Assert disabled (car composant non trouvable)",
                           assertValue.isEnabled());

        source.setName("bobo");
        assertValue.update();

        Assert.assertTrue("Assert enable", assertValue.isEnabled());
    }


    @Test
    public void test_update_enable_jComboBox() throws Exception {
        JComboBox source = new JComboBox();
        source.setName("aName");
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertValue.update();

        Assert.assertTrue("Assert enable", assertValue.isEnabled());
    }


    @Test
    public void test_update_enable_jCheckBox() throws Exception {
        JCheckBox source = new JCheckBox();
        source.setName("aName");
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertValue.update();

        Assert.assertTrue("Assert enable", assertValue.isEnabled());
    }
}
