/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.event;
import javax.swing.tree.TreePath;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**

 */
public class TreeEventDataTest {
    private TreePath pathA;
    private TreePath pathB;


    @Test
    public void test_constructor_bad() throws Exception {
        try {
            new TreeEventData(null, true, true);
            Assert.fail("Pas path pas de Data");
        }
        catch (NullPointerException ex) {
            ; // Ok
        }
    }


    @Test
    public void test_equals_bad() throws Exception {
        TreeEventData treeEventData = new TreeEventData(pathA, true, true);

        Assert.assertTrue(treeEventData.equals(treeEventData));
        Assert.assertTrue(treeEventData.equals(new TreeEventData(pathA, true, true)));
        Assert.assertEquals(treeEventData.hashCode(),
                            new TreeEventData(pathA, true, true).hashCode());

        Assert.assertFalse(treeEventData.equals("value"));
        Assert.assertFalse(treeEventData.equals(new TreeEventData(pathB, true, true)));
        Assert.assertFalse(treeEventData.equals(new TreeEventData(pathA, false, true)));
        Assert.assertFalse(treeEventData.equals(new TreeEventData(pathA, true, false)));
    }


    @Before
    public void setUp() throws Exception {
        pathA = new TreePath(new Object[]{"rootFolder"});
        pathB = new TreePath(new Object[]{"rootFolder", "node"});
    }
}
