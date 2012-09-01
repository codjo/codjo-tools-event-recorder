/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.TreePath;
import junit.framework.TestCase;
import recorder.component.GuiComponentFactory;
import recorder.result.Statement;
/**

 */
public class AssertTreeTest extends TestCase {
    private AssertTree assertTree;
    private MockAssertContext context;


    @Override
    protected void setUp() throws Exception {
        context = new MockAssertContext();
        assertTree = new AssertTree(context);
    }


    public void test_type() throws Exception {
        assertEquals(AbstractAssert.COMPONENT_ASSERT, assertTree.getAssertType());
    }


    public void test_assert_exists() throws Exception {
        JTree source =
              buildJTree(new TreePath(new Object[]{"root", "child"}), "treeName");

        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));
        context.setPoint(new Point(1, 1));

        assertTree.execute();

        Statement resultAssert = context.getPostedAssert();
        assertNotNull("un assertion est défini", resultAssert);
        assertEquals("<assertTree name=\"treeName\" path=\"root:child\" exists=\"true\"/>",
                     resultAssert.toXml());
    }


    public void test_assert_bad_source() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertTree.execute();

        Statement resultAssert = context.getPostedAssert();
        assertNull("un assertion n'est pas défini", resultAssert);
    }


    public void test_update_disable() throws Exception {
        JTextField source = new JTextField();
        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));

        assertTree.update();

        assertFalse("Assert disable", assertTree.isEnabled());
    }


    public void test_update_disabled_noNode() throws Exception {
        JTree source = buildJTree(null, "treeName");

        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));
        context.setPoint(new Point(1, 1));

        assertTree.update();

        assertFalse("Assert disable", assertTree.isEnabled());
    }


    public void test_update_enable() throws Exception {
        JTree source = buildJTree(new TreePath("root"), "treeName");

        context.setGuiComponent(GuiComponentFactory.newGuiComponent(source));
        context.setPoint(new Point(1, 1));

        assertTree.update();

        assertTrue("Assert ensable", assertTree.isEnabled());
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
