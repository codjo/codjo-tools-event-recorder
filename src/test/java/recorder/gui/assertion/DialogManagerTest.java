/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import javax.swing.JPopupMenu;
import junit.framework.TestCase;
/**
 * Classe de test de {@link DialogManager}.
 */
public class DialogManagerTest extends TestCase {
    private DialogManager manager;

    public void test_d() throws Exception {
        JPopupMenu popupMenu = manager.newPopupMenu();
        assertNotNull(popupMenu);
        assertNotSame(popupMenu, manager.newPopupMenu());
    }


    protected void setUp() throws Exception {
        manager = new DialogManager();
    }
}
