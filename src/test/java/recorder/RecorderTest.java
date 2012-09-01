/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;
import junit.framework.TestCase;
import recorder.component.GuiComponent;
import recorder.component.GuiComponentFactory;
import recorder.event.GuiEvent;
import recorder.event.GuiEventList;
import recorder.event.GuiEventType;
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
import recorder.result.Statement;
import recorder.result.StatementList;
/**

 */
public class RecorderTest extends TestCase {
    private Recorder recorder;


    public void test_simplifiedEvent_setValue_combo() {
        JComboBox field = new JComboBox(new String[]{"la", "lb"});
        field.setName("myfield");
        field.setSelectedItem("la");

        recorder.eventDispatched(newFocus(field, FocusEvent.FOCUS_GAINED));
        recorder.eventDispatched(newFocus(new JTextField(), FocusEvent.FOCUS_GAINED));

        field.setSelectedItem("lb");

        recorder.eventDispatched(newFocus(field, FocusEvent.FOCUS_LOST));

        GuiEventList list = recorder.getSimpleEventList();
        assertEquals("Les events sont consomm»s imm»diatement", 0, list.size());

        assertEquals(new GuiEvent(GuiEventType.COMBO_FOCUS_GAIN, toGui(field), "la"),
                     list.findPrevious(new GuiEvent(GuiEventType.COMBO_FOCUS_GAIN, null)));
        assertEquals(new GuiEvent(GuiEventType.COMBO_FOCUS_LOST, toGui(field), "lb"),
                     list.findPrevious(new GuiEvent(GuiEventType.COMBO_FOCUS_LOST, null)));
    }


    public void test_menu() {
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        file.add(open);
        recorder.eventDispatched(newMouse(file, MouseEvent.MOUSE_PRESSED));
        recorder.eventDispatched(newMouse(open, MouseEvent.MOUSE_PRESSED));

        StatementList result = recorder.getGestureResultList();

        assertEquals("<click menu=\"File:Open\"/>", result.toXml());
    }


    public void test_setValue() {
        JTextField field = buildValidTextField();
        recorder.eventDispatched(newKey(field, KeyEvent.KEY_RELEASED, 'e'));

        StatementList result = recorder.getGestureResultList();

        assertEquals("<setValue name=\"myfield\" value=\"e\"/>", result.toXml());
    }


    public void test_removeLastGesture() {
        JTextField field = new JTextField();
        field.setName("myfield");
        JTextField fields2 = new JTextField();
        fields2.setName("myfield2");

        recorder.eventDispatched(newKey(field, KeyEvent.KEY_RELEASED, ' '));
        recorder.eventDispatched(newKey(fields2, KeyEvent.KEY_RELEASED, 'e'));

        recorder.removeLastGesture();
        StatementList result = recorder.getGestureResultList();

        assertEquals("<setValue name=\"myfield\" value=\"\"/>", result.toXml());
    }


    public void test_postGestureResult() {
        MockRecorderListener listener = new MockRecorderListener();
        recorder.addRecorderListener(listener);

        Statement statement = new DefaultStatement("assertion", AttributeList.EMPTY_LIST);
        recorder.postGestureResult(statement);

        assertEquals("Le listener est prevenu", 1, listener.eventRecognizedCalled);
        assertSame("Le result est ajout»", statement,
                   recorder.getGestureResultList().lastResult());
    }


    public void test_recorderListener_2listeners() {
        MockRecorderListener listener = new MockRecorderListener();
        MockRecorderListener listener2 = new MockRecorderListener();
        recorder.addRecorderListener(listener);
        recorder.addRecorderListener(listener2);

        recorder.eventDispatched(newKey(buildValidTextField(), KeyEvent.KEY_RELEASED, 'e'));

        assertEquals("Event released est reconnu : listener1", 1,
                     listener.eventRecognizedCalled);
        assertEquals("Event released est reconnu : listener2", 1,
                     listener2.eventRecognizedCalled);

        recorder.removeRecorderListener(listener2);
        recorder.eventDispatched(newKey(buildValidTextField(), KeyEvent.KEY_RELEASED, 'e'));

        assertEquals("Event released est reconnu : listener1", 2,
                     listener.eventRecognizedCalled);
        assertEquals("Event released est reconnu : listener2", 1,
                     listener2.eventRecognizedCalled);
    }


    public void test_recorderListener_casParticulier() {
        MockRecorderListener listener = new MockRecorderListener();

        // Enlever un listener ne fait pas de NPA :)
        recorder.removeRecorderListener(listener);

        // Un clear fait un event
        recorder.addRecorderListener(listener);
        recorder.clearScript();
        assertEquals("Un clear fait un event", 1, listener.eventRecognizedCalled);

        // Un awtEvent non reconnu ne fait pas d'event
        recorder.eventDispatched(newKey(buildValidTextField(), KeyEvent.KEY_PRESSED, 'e'));
        assertEquals("Non reconnu pas d'event", 1, listener.eventRecognizedCalled);

        // Un awtEvent reconnu fait un event
        recorder.eventDispatched(newKey(buildValidTextField(), KeyEvent.KEY_RELEASED, 'e'));
        assertEquals("Un awtEvent reconnu declenche", 2, listener.eventRecognizedCalled);

        // Un removeLastGesture fait un event
        recorder.removeLastGesture();
        assertEquals("Un remove fait un event", 3, listener.eventRecognizedCalled);
    }


    @Override
    protected void setUp() throws Exception {
        recorder = new Recorder(new GuiComponentFactory());
    }


    private JTextField buildValidTextField() {
        JTextField field = new JTextField();
        field.setName("myfield");
        field.setText("e");
        return field;
    }


    private MouseEvent newMouse(Component source, int id) {
        return new MouseEvent(source, id, 0, MouseEvent.BUTTON1_MASK, 0, 0, 0, false);
    }


    private KeyEvent newKey(Component source, int id, char ch) {
        return new KeyEvent(source, id, 0, 0, 0, ch);
    }


    private FocusEvent newFocus(Component source, int id) {
        return new FocusEvent(source, id);
    }


    private GuiComponent toGui(JComponent field) {
        return GuiComponentFactory.newGuiComponent(field);
    }


    private static class MockRecorderListener implements RecorderListener {
        int eventRecognizedCalled = 0;


        public void recorderUpdate() {
            eventRecognizedCalled++;
        }
    }
}
