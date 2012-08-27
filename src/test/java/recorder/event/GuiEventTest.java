/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.event;
import javax.swing.JButton;
import javax.swing.JComponent;
import junit.framework.TestCase;
import recorder.component.GuiComponent;
import recorder.component.GuiComponentFactory;
/**
 * Classe de test de {@link GuiEvent}.
 */
public class GuiEventTest extends TestCase {
    public void test_equals_badclass() throws Exception {
        GuiEvent event = newGuiEvent(GuiEventType.BUTTON_CLICK, new JButton());
        assertFalse(event.equals("notGuiEvent"));
    }


    public void test_equals() throws Exception {
        JButton source = new JButton();
        GuiEventType type = GuiEventType.BUTTON_CLICK;

        assertTrue("Egaux", newGuiEvent(type, source).equals(newGuiEvent(type, source)));
        assertEquals("même hashcode", newGuiEvent(type, source).hashCode(),
            newGuiEvent(type, source).hashCode());

        assertFalse("Pas le même type",
            newGuiEvent(type, source).equals(newGuiEvent(GuiEventType.KEY, source)));

        assertFalse("Pas la même source",
            newGuiEvent(type, source).equals(newGuiEvent(type, new JButton())));

        assertFalse("Pas la même value",
            newGuiEvent(type, source, "other").equals(newGuiEvent(type, source, "value")));
    }


    private static GuiEvent newGuiEvent(GuiEventType eventType, JComponent source) {
        return new GuiEvent(eventType, toGui(source));
    }


    private static GuiEvent newGuiEvent(GuiEventType eventType, JComponent source,
        Object value) {
        return new GuiEvent(eventType, toGui(source), value);
    }


    private static GuiComponent toGui(JComponent field) {
        return GuiComponentFactory.newGuiComponent(field);
    }
}
