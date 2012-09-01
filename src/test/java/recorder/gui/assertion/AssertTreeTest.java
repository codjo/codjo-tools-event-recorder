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
import javax.swing.*;
import javax.swing.tree.TreePath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import recorder.component.GuiComponentFactory;
import recorder.result.Statement;
/**

 */
public class AssertTreeTest {
    private AssertTree assertTree;
    private MockAssertContext context;


    @Before
    public void setUp() throws Exception {
        context = new MockAssertContext();
        assertTree = new AssertTree(context);
    }


    @Test
    public void test_type() throws Exception {
        Assert.assertEquals(AbstractAssert.COMPONENT_ASSERT, assertTree.getAssertType());
    }


    @Test
    public void test_assert_exists() throws Exception {
        JTree source =
              buildJTree(new TreePath(new Object[]{"root", "child"}), "treeName");

        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));
        context.setPoint(new Point(1, 1));

        assertTree.execute();

        Statement resultAssert = context.getPostedAssert();
        Assert.assertNotNull("un assertion est défini", resultAssert);
        Assert.assertEquals("<assertTree name=\"treeName\" path=\"root:child\" exists=\"true\"/>",
                            resultAssert.toXml());
    }


    @Test
    public void test_assert_bad_source() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertTree.execute();

        Statement resultAssert = context.getPostedAssert();
        Assert.assertNull("un assertion n'est pas défini", resultAssert);
    }


    @Test
    public void test_update_disable() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertTree.update();

        Assert.assertFalse("Assert disable", assertTree.isEnabled());
    }


    @Test
    public void test_update_disabled_noNode() throws Exception {
        JTree source = buildJTree(null, "treeName");

        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));
        context.setPoint(new Point(1, 1));

        assertTree.update();

        Assert.assertFalse("Assert disable", assertTree.isEnabled());
    }


    @Test
    public void test_update_enable() throws Exception {
        JTree source = buildJTree(new TreePath("root"), "treeName");

        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));
        context.setPoint(new Point(1, 1));

        assertTree.update();

        Assert.assertTrue("Assert ensable", assertTree.isEnabled());
    }


    private JTree buildJTree(final TreePath currentPath, String name) {
        JTree tree =
              new JTree() {
                  @Override
                  public TreePath getClosestPathForLocation(int x, int y) {
                      return currentPath;
                  }
              };
        tree.setName(name);
        return tree;
    }
}
