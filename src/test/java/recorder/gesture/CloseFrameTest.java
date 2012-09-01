/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gesture;
import java.awt.*;
import javax.swing.*;
import junit.framework.TestCase;
import recorder.component.GuiComponentFactory;
import recorder.event.GuiEvent;
import recorder.event.GuiEventList;
import recorder.event.GuiEventType;
import recorder.result.StatementList;
/**

 */
public class CloseFrameTest extends TestCase {
    private CloseFrame closeGesture;
    private String accessibleCloseName;
    private StatementList statementList;
    private GuiEventList eventList;


    public void test_simple() throws Exception {
        JInternalFrame frame = new JInternalFrame("my internal frame");
        JButton closeButton = findCloseButton(frame);
        assertNotNull("Impossible de trouver le bouton de fermeture de l'internalFrame",
                      closeButton);

        eventList.addEvent(newClickGuiEvent(GuiEventType.BUTTON_CLICK, closeButton));

        closeGesture.receive(eventList, statementList);

        assertEquals("Click est consommé", 0, eventList.size());

        assertEquals("<closeFrame title=\"my internal frame\"/>", statementList.toXml());
    }


    public void test_simple_notCloseButton() throws Exception {
        JInternalFrame frame = new JInternalFrame("my internal frame");
        JButton aButton = new JButton();
        aButton.getAccessibleContext().setAccessibleName("bobo");
        frame.getContentPane().add(aButton);

        eventList.addEvent(newClickGuiEvent(GuiEventType.BUTTON_CLICK, aButton));

        closeGesture.receive(eventList, statementList);

        assertEquals("Click n'est pas consommé", 1, eventList.size());
    }


    public void test_simple_notInInternalFrame() throws Exception {
        JButton closeButton = new JButton();
        closeButton.getAccessibleContext().setAccessibleName(accessibleCloseName);

        eventList.addEvent(newClickGuiEvent(GuiEventType.BUTTON_CLICK, closeButton));

        closeGesture.receive(eventList, statementList);

        assertEquals("Click n'est pas consommé", 1, eventList.size());
    }


    @Override
    protected void setUp() throws Exception {
        closeGesture = new CloseFrame();
        this.accessibleCloseName =
              UIManager.getString("InternalFrameTitlePane.closeButtonAccessibleName");
        statementList = new StatementList();
        eventList = new GuiEventList();
    }


    private GuiEvent newClickGuiEvent(GuiEventType type, JButton closeButton) {
        return new GuiEvent(type, GuiComponentFactory.newGuiComponent(closeButton));
    }


    private JButton findCloseButton(Container frame) {
        if (isCloseButton(frame)) {
            return (JButton)frame;
        }

        Component[] content = frame.getComponents();
        for (Component aContent : content) {
            if (aContent instanceof Container) {
                Container container = (Container)aContent;
                JButton close = findCloseButton(container);
                if (close != null) {
                    return close;
                }
            }
        }

        return null;
    }


    private boolean isCloseButton(Component frame) {
        return frame.getAccessibleContext() != null
               && accessibleCloseName.equals(frame.getAccessibleContext().getAccessibleName());
    }
}
