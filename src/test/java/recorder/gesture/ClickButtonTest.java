/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gesture;
import javax.swing.JButton;
import junit.framework.TestCase;
import recorder.component.GuiComponentFactory;
import recorder.event.GuiEvent;
import recorder.event.GuiEventList;
import recorder.event.GuiEventType;
import recorder.result.StatementList;
/**
 * Classe de test de {@link ClickButton}.
 */
public class ClickButtonTest extends TestCase {
    private ClickButton clickGesture;

    public void test_simple() throws Exception {
        JButton file = new JButton("File");
        file.setName("my.button");

        GuiEventList list = new GuiEventList();
        list.addEvent(new GuiEvent(GuiEventType.BUTTON_CLICK,
                GuiComponentFactory.newGuiComponent(file)));

        StatementList result = new StatementList();

        clickGesture.receive(list, result);

        assertEquals("Click est consommé", 0, list.size());

        assertEquals("<click name=\"my.button\"/>", result.toXml());
    }


    public void test_simple_noName() throws Exception {
        JButton file = new JButton("File");

        GuiEventList list = new GuiEventList();
        list.addEvent(new GuiEvent(GuiEventType.BUTTON_CLICK,
                GuiComponentFactory.newGuiComponent(file)));

        StatementList result = new StatementList();

        clickGesture.receive(list, result);

        assertEquals("Click est consommé", 0, list.size());

        assertEquals("<click label=\"File\"/>", result.toXml());
    }


    public void test_simple_noName_noLabel() throws Exception {
        JButton file = new JButton();

        GuiEventList list = new GuiEventList();
        list.addEvent(new GuiEvent(GuiEventType.BUTTON_CLICK,
                GuiComponentFactory.newGuiComponent(file)));

        StatementList result = new StatementList();

        clickGesture.receive(list, result);

        assertEquals("Click n'est pas consommé", 1, list.size());

        assertEquals("", result.toXml());
    }


    protected void setUp() throws Exception {
        clickGesture = new ClickButton();
    }
}
