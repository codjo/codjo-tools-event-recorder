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
import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import recorder.component.GuiComponentFactory;
import recorder.result.Statement;
/**

 */
public class AssertListSizeTest {
    private static final int EXPECTED_SIZE = 1;
    private AssertListSize assertListSize;
    private MockAssertContext context;


    @Before
    public void setUp() throws Exception {
        context = new MockAssertContext();
        assertListSize = new AssertListSize(context);
    }


    @Test
    public void test_type() throws Exception {
        Assert.assertEquals(AbstractAssert.COMPONENT_ASSERT, assertListSize.getAssertType());
    }


    @Test
    public void test_assert_size_table() throws Exception {
        String name = "Ma table";
        JTable source = buildJTable(EXPECTED_SIZE, name);

        assertSizeTest(source, name);
    }


    @Test
    public void test_assert_size_list() throws Exception {
        JList source = buildJList(EXPECTED_SIZE, "A List");

        assertSizeTest(source, "A List");
    }


    @Test
    public void test_assert_size_combo() throws Exception {
        JComboBox source = buildJCombo(EXPECTED_SIZE, "My combo");

        assertSizeTest(source, "My combo");
    }


    private void assertSizeTest(JComponent source, String name) {
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));
        context.setPoint(new Point(1, 1));

        assertListSize.execute();

        Statement resultAssert = context.getPostedAssert();
        Assert.assertNotNull("An assertion should have been defined for " + name, resultAssert);
        Assert.assertEquals("<assertListSize name=\"" + name + "\" expected=\"" + EXPECTED_SIZE
                            + "\"/>", resultAssert.toXml());
    }


    @Test
    public void test_assert_bad_source() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertListSize.execute();

        Statement resultAssert = context.getPostedAssert();
        Assert.assertNull("An assertion should have been defined", resultAssert);
    }


    @Test
    public void test_update_disable() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertListSize.update();

        Assert.assertFalse("Assert disable", assertListSize.isEnabled());
    }


    @Test
    public void test_update_enable_table() throws Exception {
        assertEnabledTest(buildJTable(2, "Ma table"));
    }


    @Test
    public void test_update_enable_list() throws Exception {
        assertEnabledTest(buildJList(2, "Ma List"));
    }


    @Test
    public void test_update_enable_combo() throws Exception {
        assertEnabledTest(buildJCombo(2, "Ma Combo"));
    }


    private void assertEnabledTest(JComponent source) {
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertListSize.update();

        Assert.assertTrue("Assert enable", assertListSize.isEnabled());
    }


    private JTable buildJTable(final int size, String name) {
        JTable source = new JTable(size, 2);
        source.setName(name);
        return source;
    }


    private JList buildJList(final int size, String name) {
        Object[] listData = new Object[size];
        Arrays.fill(listData, "value");
        JList list = new JList(listData);
        list.setName(name);
        return list;
    }


    private JComboBox buildJCombo(int expectedSize, String name) {
        Object[] items = new Object[expectedSize];
        Arrays.fill(items, "value");
        JComboBox combo = new JComboBox(items);
        combo.setName(name);
        return combo;
    }
}
