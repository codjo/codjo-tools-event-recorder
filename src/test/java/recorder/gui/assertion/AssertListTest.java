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
public class AssertListTest {
    private static final int ROW = 1;
    private static final Object VALUE = "expectedValue";
    private AssertList assertList;
    private MockAssertContext context;


    @Before
    public void setUp() throws Exception {
        context = new MockAssertContext();
        assertList = new AssertList(context);
    }


    @Test
    public void test_type() throws Exception {
        Assert.assertEquals(AbstractAssert.COMPONENT_ASSERT, assertList.getAssertType());
    }


    @Test
    public void test_assert_list() throws Exception {
        JList source = buildJList(ROW, VALUE, "Ma Liste");

        assertListTest(source, "Ma Liste");
    }


    private void assertListTest(JComponent source, String name) {
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));
        context.setPoint(new Point(1, 1));

        assertList.execute();

        Statement resultAssert = context.getPostedAssert();
        Assert.assertNotNull("un assertion est d�fini pour " + name, resultAssert);
        Assert.assertEquals("<assertList name=\"" + name + "\" expected=\"" + VALUE
                            + "\" row=\"" + ROW + "\"/>", resultAssert.toXml());
    }


    @Test
    public void test_assert_bad_source() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertList.execute();

        Statement resultAssert = context.getPostedAssert();
        Assert.assertNull("un assertion n'est pas d�fini", resultAssert);
    }


    @Test
    public void test_update_disable() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertList.update();

        Assert.assertFalse("Assert disable", assertList.isEnabled());
    }


    @Test
    public void test_update_enable_list() throws Exception {
        assertEnabledTest(buildJList(2, VALUE, "Ma List"));
    }


    private void assertEnabledTest(JComponent source) {
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertList.update();

        Assert.assertTrue("Assert ensable", assertList.isEnabled());
    }


    private JList buildJList(final int rowAtPoint, Object value, String name) {
        Object[] content = new Object[2 * rowAtPoint];
        Arrays.fill(content, "bad");
        content[rowAtPoint] = value;

        JList list =
              new JList(content) {
                  @Override
                  public int locationToIndex(Point point) {
                      return rowAtPoint;
                  }
              };
        list.setName(name);
        return list;
    }
}
