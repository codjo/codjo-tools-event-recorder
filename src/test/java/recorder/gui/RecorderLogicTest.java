/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui;
import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import recorder.gui.util.Util;
/**

 */
public class RecorderLogicTest {
    private RecorderLogic logic;
    private JTextField field;


    @Test
    public void test_log() {
        Assert.assertNull("log non affiché par défaut", findByName("log.display", logic.getGui()));

        AbstractButton displayLog = Util.findButtonByLabel("Log", logic.getGui());
        displayLog.doClick();

        JTextComponent log = (JTextComponent)findByName("log.display", logic.getGui());
        Assert.assertNotNull("Présence d'un composant log", log);

        String trace = "Log de test";
        Logger.getLogger(RecorderLogicTest.class).info(trace);

        Assert.assertTrue("La trace se trouve dans le log", log.getText().contains(trace));

        Component clearLog = findByName("log.clear", logic.getGui());
        Assert.assertNotNull("Présence d'un bouton clear des log", clearLog);

        logic.clearLog();

        Assert.assertTrue("Les logs sont éffacés", !log.getText().contains(trace));
    }


    @Test
    public void test_highlight() {
        Assert.assertNull("Structure non affiché par défaut", findByName("hierarchy", logic.getGui()));

        AbstractButton displayLog = Util.findButtonByLabel("Structure", logic.getGui());
        displayLog.doClick();

        JButton highlight = (JButton)findByName("highlight", logic.getGui());
        Assert.assertNotNull(highlight);
        JTextComponent hierarchy = (JTextComponent)findByName("hierarchy", logic.getGui());
        Assert.assertNotNull(hierarchy);

        Assert.assertEquals("", hierarchy.getText());

        highlight.doClick();

        mockUserIn("ok", newMouseEnter());
        Assert.assertEquals("JPanel(ok)\n  JTextField(myfield)\n", hierarchy.getText());

        highlight.doClick();

        hierarchy.setText("");
        mockUserIn("ok", newMouseEnter());
        Assert.assertEquals("", hierarchy.getText());
    }


    @Test
    public void test_record() {
        Component stopRecord = findByName("record.stop", logic.getGui());
        Assert.assertNotNull(stopRecord);

        Component startRecord = findByName("record.start", logic.getGui());
        Assert.assertNotNull(startRecord);

        Assert.assertTrue("Bouton start enabled", startRecord.isEnabled());
        Assert.assertTrue("Bouton stop disabled", !stopRecord.isEnabled());

        logic.startRecord();

        Assert.assertTrue("Bouton start disabled", !startRecord.isEnabled());
        Assert.assertTrue("Bouton stop enabled", stopRecord.isEnabled());

        logic.stopRecord();

        Assert.assertTrue("Bouton start enabled", startRecord.isEnabled());
        Assert.assertTrue("Bouton stop disabled", !stopRecord.isEnabled());
    }

/*    public void test_record_start() {
        JTextComponent scriptUi = (JTextComponent)findByName("script.display", logic.getGui());
        assertNotNull(scriptUi);

//        ScriptGui scriptTreetUi =
//            (ScriptGui)findByName("script.tree.display", logic.getGui());
//        assertNotNull(scriptTreetUi);
        logic.startRecord();
        assertEquals("Au démarrage pas de script", "", scriptUi.getText());

        mockUserSetFieldValueIn("recorder.mainPanel");

        assertEquals("Action sur le recorder ne font rien", "", scriptUi.getText());

        mockUserSetFieldValueIn("another.gui");

        assertEquals("Les events s'enregistrent", "<setValue name=\"myfield\" value=\"e\"/>",
            scriptUi.getText());
    }


    public void test_record_clear() {
        Component clearRecord = findByName("script.clear", logic.getGui());
        assertNotNull("Bouton de remise à zero du script", clearRecord);

        JTextComponent scriptUi = (JTextComponent)findByName("script.display", logic.getGui());

        logic.startRecord();
        mockUserSetFieldValueIn("another.gui");
        assertTrue("script non vide", scriptUi.getText().length() != 0);

        logic.clearRecord();

        assertTrue("script vide", scriptUi.getText().length() == 0);
    }


    public void test_record_removelast() {
        JButton removeLast = (JButton)findByName("script.clear.last", logic.getGui());
        assertNotNull("Bouton de suppression du dernier event", removeLast);
        assertFalse("Boutton disabled", removeLast.isEnabled());

        JTextComponent scriptUi = (JTextComponent)findByName("script.display", logic.getGui());

        logic.startRecord();
        mockUserSetFieldValueIn("another.gui");
        assertTrue("script non vide", scriptUi.getText().length() != 0);

        removeLast.doClick();

        assertEquals("script vide (car gesture supprimé)", "", scriptUi.getText());
        assertFalse("Boutton disabled (car plus de script)", removeLast.isEnabled());
    }
*/


    @Test
    public void test_record_export() {
        JButton removeLast = (JButton)findByName("script.export", logic.getGui());

//        assertNotNull("Bouton d'export du script", removeLast);
//        assertFalse("Boutton disabled", removeLast.isEnabled());
        Assert.assertNull(removeLast);

//        JTextComponent scriptUi =
//            (JTextComponent)findByName("script.display", logic.getGui());
//
//        logic.startRecord();
//        mockUserSetFieldValueIn("another.gui");
//        assertTrue("script non vide", scriptUi.getText().length() != 0);
//
//        removeLast.doClick();
//
//        assertEquals("script vide (car gesture supprimé)", "", scriptUi.getText());
//        assertFalse("Boutton disabled (car plus de script)", removeLast.isEnabled());
    }


    private MouseEvent newMouseEnter() {
        return new MouseEvent(field, MouseEvent.MOUSE_ENTERED, 0, 0, 0, 0, 0, false);
    }

//    private void mockUserSetFieldValueIn(String containerName) {
//        mockUserIn(containerName, new KeyEvent(field, KeyEvent.KEY_RELEASED, 0, 0, 0, 'e'));
//    }


    private void mockUserIn(String containerName, AWTEvent event) {
        JPanel container = new JPanel();
        container.setName(containerName);

        field.setName("myfield");
        container.add(field);
        field.setText("e");

        field.dispatchEvent(event);
    }


    @Before
    public void setUp() throws Exception {
        logic = new RecorderLogic();
        field = new JTextField();
    }


    private static Component findByName(String name, Component comp) {
        return Util.findByName(name, comp);
    }
}
