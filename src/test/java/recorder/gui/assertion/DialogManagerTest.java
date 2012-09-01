/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import javax.swing.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**

 */
public class DialogManagerTest {
    private DialogManager manager;


    @Test
    public void test_d() throws Exception {
        JPopupMenu popupMenu = manager.newPopupMenu();
        Assert.assertNotNull(popupMenu);
        Assert.assertNotSame(popupMenu, manager.newPopupMenu());
    }


    @Before
    public void setUp() throws Exception {
        manager = new DialogManager();
    }
}
