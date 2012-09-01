/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import javax.swing.*;
import junit.framework.TestCase;
/**

 */
public class DialogManagerTest extends TestCase {
    private DialogManager manager;


    public void test_d() throws Exception {
        JPopupMenu popupMenu = manager.newPopupMenu();
        assertNotNull(popupMenu);
        assertNotSame(popupMenu, manager.newPopupMenu());
    }


    @Override
    protected void setUp() throws Exception {
        manager = new DialogManager();
    }
}
