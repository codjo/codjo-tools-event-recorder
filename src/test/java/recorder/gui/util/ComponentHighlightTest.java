/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.util;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import javax.swing.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**

 */
public class ComponentHighlightTest {
    private ComponentHighlighter highlighter;


    @Before
    public void setUp() throws Exception {
        highlighter = new ComponentHighlighter();
    }


    @Test
    public void test_notMouseEvent() throws Exception {
        final JComponent source = new JLabel();
        source.setBackground(null);

        highlighter.eventDispatched(new FocusEvent(source, FocusEvent.FOCUS_GAINED));

        Assert.assertNull(source.getBackground());
    }


    @Test
    public void test_container() throws Exception {
        final JComponent source = new JLabel();
        Container frame = newFrame(source);

        source.setBackground(null);
        highlighter.eventDispatched(newEvent(frame, MouseEvent.MOUSE_ENTERED));

        Assert.assertEquals(ComponentHighlighter.NOT_FINDABLE_COLOR, source.getBackground());
    }


    @Test
    public void test_listener() throws Exception {
        final JComponent source = new JLabel();
        source.setName("myname");

        MockListener listener = new MockListener();
        highlighter.setListener(listener);

        highlighter.eventDispatched(newEvent(source, MouseEvent.MOUSE_ENTERED));

        Assert.assertEquals(1, listener.called);
    }


    /**
     * Cas d'un composant dans un autre. Swing envoie des event 'doublon' quand on rentre dans le 'field' swing envoie aussi un event indiquant que l'on rentre dans le container.
     */
    @Test
    public void test_container_enter_exit() {
        final JComponent field = new JTextField();
        JPanel panel = new JPanel();
        panel.add(field);

        Color fieldColor = Color.cyan;
        field.setBackground(fieldColor);

        Color panelColor = Color.darkGray;
        panel.setBackground(panelColor);

        // Rentre dans le panel
        highlighter.eventDispatched(newEvent(panel, MouseEvent.MOUSE_ENTERED));

        Assert.assertEquals(ComponentHighlighter.NOT_FINDABLE_COLOR, panel.getBackground());
        Assert.assertEquals(fieldColor, field.getBackground());

        // Rentre dans le field (dans le panel)
        highlighter.eventDispatched(newEvent(panel, MouseEvent.MOUSE_ENTERED));
        highlighter.eventDispatched(newEvent(field, MouseEvent.MOUSE_ENTERED));

        Assert.assertEquals(panelColor, panel.getBackground());
        Assert.assertEquals(ComponentHighlighter.NOT_FINDABLE_COLOR, field.getBackground());

        // sort du field (dans le panel)
        highlighter.eventDispatched(newEvent(field, MouseEvent.MOUSE_EXITED));
        highlighter.eventDispatched(newEvent(panel, MouseEvent.MOUSE_EXITED));

        Assert.assertEquals(ComponentHighlighter.NOT_FINDABLE_COLOR, panel.getBackground());
        Assert.assertEquals(fieldColor, field.getBackground());

        // sort du panel
        highlighter.eventDispatched(newEvent(panel, MouseEvent.MOUSE_EXITED));

        Assert.assertEquals(panelColor, panel.getBackground());
        Assert.assertEquals(fieldColor, field.getBackground());
    }


    @Test
    public void test_enter_withName() throws Exception {
        JComponent source = new JLabel();
        source.setName("comp.name");

        source.setBackground(null);
        highlighter.eventDispatched(newEvent(source, MouseEvent.MOUSE_ENTERED));

        Assert.assertEquals(ComponentHighlighter.FIND_BY_NAME_COLOR, source.getBackground());
    }


    @Test
    public void test_enter_otherFindStrategy() throws Exception {
        JComponent source = new JTextField();
        source.getAccessibleContext().setAccessibleName("accessibleName");

        source.setBackground(null);
        highlighter.eventDispatched(newEvent(source, MouseEvent.MOUSE_ENTERED));

        Assert.assertEquals(ComponentHighlighter.OTHER_FIND_COLOR, source.getBackground());
    }


    @Test
    public void test_enter_noName() throws Exception {
        JComponent source = new JLabel();

        source.setBackground(null);
        highlighter.eventDispatched(newEvent(source, MouseEvent.MOUSE_ENTERED));

        Assert.assertEquals(ComponentHighlighter.NOT_FINDABLE_COLOR, source.getBackground());
    }


    @Test
    public void test_enter_exit() throws Exception {
        JComponent source = new JLabel();

        Color originalBorder = Color.cyan;
        source.setBackground(originalBorder);

        highlighter.eventDispatched(newEvent(source, MouseEvent.MOUSE_ENTERED));
        highlighter.eventDispatched(newEvent(source, MouseEvent.MOUSE_EXITED));

        Assert.assertEquals(originalBorder, source.getBackground());
    }


    @Test
    public void test_enter_null() throws Exception {
        JFrame source = new JFrame();

        // Pas de composant pour mettre une bordure mais il ne faut pas faire une erreur
        highlighter.eventDispatched(newEvent(source, MouseEvent.MOUSE_ENTERED));
    }


    private MouseEvent newEvent(Component source, int id) {
        return new MouseEvent(source, id, 0, 0, 0, 0, 0, false);
    }


    private JFrame newFrame(final JComponent source) {
        return new JFrame() {
            @Override
            public Component findComponentAt(Point point) {
                return source;
            }
        };
    }


    private static class MockListener implements HighlightListener {
        private int called = 0;


        public void highlight(JComponent component) {
            called++;
        }
    }
}
