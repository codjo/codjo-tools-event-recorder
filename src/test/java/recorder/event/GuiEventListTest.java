/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.event;
import javax.swing.*;
import junit.framework.TestCase;
import recorder.component.GuiComponent;
import recorder.component.GuiComponentFactory;
/**

 */
public class GuiEventListTest extends TestCase {
    private GuiEventList list;


    public void test_findPrevious() throws Exception {
        GuiComponent srcA = toGui(new JTextField());
        GuiComponent srcB = toGui(new JTextField());

        GuiEvent eventA = new GuiEvent(GuiEventType.KEY, srcA);
        GuiEvent eventB = new GuiEvent(GuiEventType.KEY, srcB);
        GuiEvent eventC = new GuiEvent(GuiEventType.MENU_CLICK, srcA);

        list.addEvent(eventA);
        list.addEvent(eventB);
        list.addEvent(eventC);

        list.pop();
        list.pop();
        list.pop();

        assertEquals(eventC,
                     list.findPrevious(new GuiEvent(GuiEventType.MENU_CLICK, srcA)));
        assertEquals(eventA, list.findPrevious(new GuiEvent(GuiEventType.KEY, srcA)));

        assertEquals(eventC, list.findPrevious(new GuiEvent(null, srcA)));
        assertEquals(eventB, list.findPrevious(new GuiEvent(GuiEventType.KEY, null)));

        assertNull(list.findPrevious(new GuiEvent(GuiEventType.COMBO_FOCUS_GAIN, null)));
    }


    public void test_findPrevious_notSameInstance()
          throws Exception {
        JTextField swingA = new JTextField();
        GuiComponent compA = toGui(swingA);
        GuiComponent sameCompA = toGui(swingA);

        GuiEvent eventA = new GuiEvent(GuiEventType.KEY, compA);
        GuiEvent eventC = new GuiEvent(GuiEventType.MENU_CLICK, compA);

        list.addEvent(eventA);
        list.addEvent(eventC);

        list.pop();
        list.pop();

        assertEquals(eventC,
                     list.findPrevious(new GuiEvent(GuiEventType.MENU_CLICK, sameCompA)));
    }


    public void test_peek_empty() throws Exception {
        assertNull("liste vide : peek renvoie null", list.peek());
        GuiEvent event = new GuiEvent(GuiEventType.BUTTON_CLICK, null);
        list.addEvent(event);
        assertSame("liste non vide : peek renvoie le sommet de la pile", event,
                   list.peek());
        list.pop();
        assertNull("liste de nouveau vide : peek renvoie null", list.peek());
    }


    public void test_consumedEvent_sizeLimit_default()
          throws Exception {
        assertEquals(100, list.getConsumedEventLimit());
    }


    public void test_consumedEvent_sizeLimit() throws Exception {
        list.setConsumedEventLimit(2);

        GuiEvent event1 = new GuiEvent(GuiEventType.BUTTON_CLICK, toGui(new JButton()));
        list.addEvent(event1);
        list.addEvent(new GuiEvent(GuiEventType.BUTTON_CLICK, toGui(new JButton())));
        list.addEvent(new GuiEvent(GuiEventType.BUTTON_CLICK, toGui(new JButton())));

        list.pop();

        assertEquals(event1, list.findPrevious(new GuiEvent(null, event1.getSource())));

        list.pop();
        list.pop();

        assertNull("La liste à atteint sa limite de 2 (l’évènement à disparu)",
                   list.findPrevious(new GuiEvent(null, event1.getSource())));
    }


    @Override
    protected void setUp() throws Exception {
        list = new GuiEventList();
    }


    private GuiComponent toGui(JComponent field) {
        return GuiComponentFactory.newGuiComponent(field);
    }
}
