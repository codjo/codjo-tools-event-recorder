/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.component;
import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.*;
import junit.framework.TestCase;
public class GuiComponentFactoryTest extends TestCase {
    public void test_container_noPoint() throws Exception {
        final JComponent source = new JLabel();

        GuiComponentFactory factory = new GuiComponentFactory();
        assertEquals(source, factory.find(source).getSwingComponent());
    }


    /**
     * JComboBox is composed of more atomic GUI component.
     */
    public void test_jcombobox() {
        JComponent combo = new JComboBox();
        JComponent subComponent = new JLabel();
        combo.add(subComponent);

        GuiComponentFactory factory = new GuiComponentFactory();
        GuiComponent guiComponent = factory.find(subComponent);
        assertFalse("No name and therefore can not be found", guiComponent.isFindable());
        assertSame(combo, guiComponent.getSwingComponent());
    }


    public void test_jcombobox_mousevent() {
        JComponent combo = new JComboBox();
        JComponent subComponent = new JLabel();
        combo.add(subComponent);
        GuiComponentFactory factory = new GuiComponentFactory();
        assertSame(combo, factory.find(newEvent(subComponent)).getSwingComponent());
    }


    public void test_jcomponent() throws Exception {
        JComponent source = new JLabel();
        GuiComponentFactory factory = new GuiComponentFactory();
        assertSame(source, factory.find(newEvent(source)).getSwingComponent());
    }


    public void test_excpetion() throws Exception {
        final JComponent source = new JLabel();
        JComponent container = new JPanel();
        container.add(source);
        container.setName("ignore");

        GuiComponentFactory factory = new GuiComponentFactory();
        factory.setIgnoredContainer("ignore");
        assertNull("The component is under an ignored one. It cannot be 'found'",
                   factory.find(newEvent(source)));
    }


    public void test_container() throws Exception {
        final JComponent source = new JLabel();
        Container frame = newFrame(source);

        GuiComponentFactory factory = new GuiComponentFactory();
        assertSame(source, factory.find(newEvent(frame)).getSwingComponent());
    }


    public void test_container_inContainer() throws Exception {
        Container frame = newFrame(new Container());

        GuiComponentFactory factory = new GuiComponentFactory();
        assertNull(factory.find(newEvent(frame)));
    }


    public void test_container_empty() throws Exception {
        Container frame = newFrame(null);

        GuiComponentFactory factory = new GuiComponentFactory();
        assertNull(factory.find(newEvent(frame)));
    }


    private Container newFrame(final Component source) {
        return new JFrame() {
            @Override
            public Component findComponentAt(Point point) {
                if (point == null) {
                    throw new NullPointerException();
                }
                if (source != null) {
                    return source;
                }
                else {
                    return this;
                }
            }
        };
    }


    private MouseEvent newEvent(Component source) {
        return new MouseEvent(source, MouseEvent.MOUSE_ENTERED, 0, 0, 0, 0, 0, false);
    }
}
