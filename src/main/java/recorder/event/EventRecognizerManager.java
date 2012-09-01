/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.event;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.tree.TreePath;
import recorder.component.GuiComponent;
import recorder.component.GuiComponentFactory;
/**
 * Transform AWT event into {@link GuiEvent}.
 */
public class EventRecognizerManager {
    private GuiComponentFactory factory;
    private List<EventRecognizer> recognizers = new ArrayList<EventRecognizer>();


    public EventRecognizerManager(GuiComponentFactory factory) {
        this.factory = factory;
        recognizers.add(new MenuClickRecognizer());
        recognizers.add(new ButtonClickRecognizer());
        recognizers.add(new CheckboxClickRecognizer());
        recognizers.add(new TableClickRecognizer());
        recognizers.add(new ListClickRecognizer());
        recognizers.add(new TreePreClickRecognizer());
        recognizers.add(new TreeClickRecognizer());
        recognizers.add(new KeyReleasedRecognizer());
        recognizers.add(new ComboFocusRecognizer(FocusEvent.FOCUS_GAINED));
        recognizers.add(new ComboFocusRecognizer(FocusEvent.FOCUS_LOST));
    }


    public GuiEvent toGuiEvent(AWTEvent awtEvent) {
        if (isUninterestingEvent(awtEvent)) {
            return null;
        }

        for (EventRecognizer recognizer : recognizers) {
            if (recognizer.recognize(awtEvent)) {
                return recognizer.toGuiEvent(awtEvent);
            }
        }
        return null;
    }


    public GuiComponentFactory getGuiComponentFactory() {
        return factory;
    }


    private boolean isUninterestingEvent(AWTEvent awtEvent) {
        GuiComponent guiComponent = factory.find(awtEvent);
        return guiComponent == null || !guiComponent.isFindable();
    }


    // ----------------------------------------------------------------------------------
    // Base class for EventRecognizer
    // ----------------------------------------------------------------------------------
    private abstract class AbstractRecognizer implements EventRecognizer {
        private Class eventClass;
        private int eventId;
        private Class sourceClass;


        protected AbstractRecognizer(Class eventClass, int eventId, Class sourceClass) {
            this.eventClass = eventClass;
            this.eventId = eventId;
            this.sourceClass = sourceClass;
        }


        public int getEventId() {
            return eventId;
        }


        public boolean recognize(AWTEvent awtEvent) {
            return eventClass.isAssignableFrom(awtEvent.getClass())
                   && awtEvent.getID() == eventId && findSource(awtEvent).isA(sourceClass);
        }


        protected GuiComponent findSource(AWTEvent awtEvent) {
            return getGuiComponentFactory().find(awtEvent);
        }
    }

    // ----------------------------------------------------------------------------------
    // Click Recognizers
    // ----------------------------------------------------------------------------------
    private abstract class MouseClickRecognizer extends AbstractRecognizer {
        protected MouseClickRecognizer(int eventId, Class sourceClass) {
            super(MouseEvent.class, eventId, sourceClass);
        }


        @Override
        public boolean recognize(AWTEvent awtEvent) {
            //noinspection SimplifiableIfStatement
            if (super.recognize(awtEvent)) {
                return (((MouseEvent)awtEvent).getModifiers() & MouseEvent.BUTTON1_MASK) != 0;
            }
            else {
                return false;
            }
        }
    }

    private class MenuClickRecognizer extends MouseClickRecognizer {
        MenuClickRecognizer() {
            super(MouseEvent.MOUSE_PRESSED, JMenuItem.class);
        }


        public GuiEvent toGuiEvent(AWTEvent awtEvent) {
            return new GuiEvent(GuiEventType.MENU_CLICK, findSource(awtEvent));
        }
    }

    private class ButtonClickRecognizer extends MouseClickRecognizer {
        ButtonClickRecognizer() {
            super(MouseEvent.MOUSE_RELEASED, JButton.class);
        }


        public GuiEvent toGuiEvent(AWTEvent awtEvent) {
            return new GuiEvent(GuiEventType.BUTTON_CLICK, findSource(awtEvent));
        }
    }

    private class CheckboxClickRecognizer extends MouseClickRecognizer {
        CheckboxClickRecognizer() {
            super(MouseEvent.MOUSE_CLICKED, JCheckBox.class);
        }


        public GuiEvent toGuiEvent(AWTEvent awtEvent) {
            GuiComponent source = findSource(awtEvent);
            boolean value = ((JCheckBox)source.getSwingComponent()).isSelected();
            return new GuiEvent(GuiEventType.CHECKBOX_CLICK, source,
                                (value ? Boolean.TRUE : Boolean.FALSE));
        }
    }

    private class TableClickRecognizer extends MouseClickRecognizer {
        TableClickRecognizer() {
            super(MouseEvent.MOUSE_RELEASED, JTable.class);
        }


        public GuiEvent toGuiEvent(AWTEvent awtEvent) {
            GuiComponent source = findSource(awtEvent);
            JTable table = (JTable)source.getSwingComponent();
            int rowIdx = table.rowAtPoint(((MouseEvent)awtEvent).getPoint());
            return new GuiEvent(GuiEventType.TABLE_CLICK, source, rowIdx);
        }
    }

    private class ListClickRecognizer extends MouseClickRecognizer {
        ListClickRecognizer() {
            super(MouseEvent.MOUSE_RELEASED, JList.class);
        }


        public GuiEvent toGuiEvent(AWTEvent awtEvent) {
            GuiComponent source = findSource(awtEvent);
            JList table = (JList)source.getSwingComponent();
            int rowIdx = table.locationToIndex(((MouseEvent)awtEvent).getPoint());
            return new GuiEvent(GuiEventType.LIST_CLICK, source, rowIdx);
        }
    }

    private class TreeClickRecognizer extends MouseClickRecognizer {
        private GuiEventType eventType;


        TreeClickRecognizer() {
            this(MouseEvent.MOUSE_CLICKED, JTree.class, GuiEventType.TREE_CLICK);
        }


        TreeClickRecognizer(int eventId, Class<JTree> sourceClass, GuiEventType eventType) {
            super(eventId, sourceClass);
            this.eventType = eventType;
        }


        public GuiEvent toGuiEvent(AWTEvent awtEvent) {
            GuiComponent source = findSource(awtEvent);
            TreeEventData data = buildTreeEventData(awtEvent, source);
            return new GuiEvent(eventType, source, data);
        }


        public TreeEventData buildTreeEventData(AWTEvent awtEvent, GuiComponent source) {
            Point point = ((MouseEvent)awtEvent).getPoint();
            JTree tree = ((JTree)source.getSwingComponent());
            TreePath path = tree.getClosestPathForLocation(point.x, point.y);
            boolean collapsed = tree.isCollapsed(path);
            boolean selected = tree.isPathSelected(path);
            return new TreeEventData(path, collapsed, selected);
        }
    }

    private class TreePreClickRecognizer extends TreeClickRecognizer {
        TreePreClickRecognizer() {
            super(MouseEvent.MOUSE_PRESSED, JTree.class, GuiEventType.TREE_PRE_CLICK);
        }
    }

    // ----------------------------------------------------------------------------------
    // Other Recognizers
    // ----------------------------------------------------------------------------------
    private class KeyReleasedRecognizer extends AbstractRecognizer {
        KeyReleasedRecognizer() {
            super(KeyEvent.class, KeyEvent.KEY_RELEASED, JTextComponent.class);
        }


        public GuiEvent toGuiEvent(AWTEvent awtEvent) {
            GuiComponent source = findSource(awtEvent);
            JTextComponent textField = (JTextComponent)source.getSwingComponent();
            return new GuiEvent(GuiEventType.KEY, source, textField.getText());
        }
    }

    private class ComboFocusRecognizer extends AbstractRecognizer {
        ComboFocusRecognizer(int focusType) {
            super(FocusEvent.class, focusType, JComboBox.class);
        }


        public GuiEvent toGuiEvent(AWTEvent awtEvent) {
            GuiComponent source = findSource(awtEvent);
            JComboBox combo = (JComboBox)source.getSwingComponent();
            return new GuiEvent(guiEventType(), source, combo.getSelectedItem());
        }


        private GuiEventType guiEventType() {
            return (getEventId() == FocusEvent.FOCUS_GAINED)
                   ? GuiEventType.COMBO_FOCUS_GAIN : GuiEventType.COMBO_FOCUS_LOST;
        }
    }
}
