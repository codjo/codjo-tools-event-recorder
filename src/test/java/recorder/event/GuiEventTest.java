/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.event;
import javax.swing.*;
import org.junit.Assert;
import org.junit.Test;
import recorder.component.GuiComponent;
import recorder.component.GuiComponentFactory;
/**

 */
public class GuiEventTest {
    @Test
    public void test_equals_badclass() throws Exception {
        GuiEvent event = newGuiEvent(GuiEventType.BUTTON_CLICK, new JButton());
        Assert.assertFalse(event.equals("notGuiEvent"));
    }


    @Test
    public void test_equals() throws Exception {
        JButton source = new JButton();
        GuiEventType type = GuiEventType.BUTTON_CLICK;

        Assert.assertTrue("Egaux", newGuiEvent(type, source).equals(newGuiEvent(type, source)));
        Assert.assertEquals("même hashcode", newGuiEvent(type, source).hashCode(),
                            newGuiEvent(type, source).hashCode());

        Assert.assertFalse("Pas le même type",
                           newGuiEvent(type, source).equals(newGuiEvent(GuiEventType.KEY, source)));

        Assert.assertFalse("Pas la même source",
                           newGuiEvent(type, source).equals(newGuiEvent(type, new JButton())));

        Assert.assertFalse("Pas la même value",
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
