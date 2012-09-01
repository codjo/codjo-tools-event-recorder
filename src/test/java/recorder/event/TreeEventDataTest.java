/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.event;
import javax.swing.tree.TreePath;
import junit.framework.TestCase;
/**

 */
public class TreeEventDataTest extends TestCase {
    private TreePath pathA;
    private TreePath pathB;


    public void test_constructor_bad() throws Exception {
        try {
            new TreeEventData(null, true, true);
            fail("Pas path pas de Data");
        }
        catch (NullPointerException ex) {
            ; // Ok
        }
    }


    public void test_equals_bad() throws Exception {
        TreeEventData treeEventData = new TreeEventData(pathA, true, true);

        assertTrue(treeEventData.equals(treeEventData));
        assertTrue(treeEventData.equals(new TreeEventData(pathA, true, true)));
        assertEquals(treeEventData.hashCode(),
                     new TreeEventData(pathA, true, true).hashCode());

        assertFalse(treeEventData.equals("value"));
        assertFalse(treeEventData.equals(new TreeEventData(pathB, true, true)));
        assertFalse(treeEventData.equals(new TreeEventData(pathA, false, true)));
        assertFalse(treeEventData.equals(new TreeEventData(pathA, true, false)));
    }


    @Override
    protected void setUp() throws Exception {
        pathA = new TreePath(new Object[]{"rootFolder"});
        pathB = new TreePath(new Object[]{"rootFolder", "node"});
    }
}
