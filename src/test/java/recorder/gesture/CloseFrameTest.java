/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gesture;
import java.awt.*;
import javax.swing.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import recorder.component.GuiComponentFactory;
import recorder.event.GuiEvent;
import recorder.event.GuiEventList;
import recorder.event.GuiEventType;
import recorder.result.StatementList;
/**

 */
public class CloseFrameTest {
    private CloseFrame closeGesture;
    private String accessibleCloseName;
    private StatementList statementList;
    private GuiEventList eventList;


    @Test
    public void test_simple() throws Exception {
        JInternalFrame frame = new JInternalFrame("my internal frame");
        JButton closeButton = findCloseButton(frame);
        Assert.assertNotNull("JInternalFrame's close button should have been found", closeButton);

        eventList.addEvent(newClickGuiEvent(GuiEventType.BUTTON_CLICK, closeButton));

        closeGesture.receive(eventList, statementList);

        Assert.assertEquals("Click consumed", 0, eventList.size());

        Assert.assertEquals("<closeFrame title=\"my internal frame\"/>", statementList.toXml());
    }


    @Test
    public void test_simple_notCloseButton() throws Exception {
        JInternalFrame frame = new JInternalFrame("my internal frame");
        JButton aButton = new JButton();
        aButton.getAccessibleContext().setAccessibleName("bobo");
        frame.getContentPane().add(aButton);

        eventList.addEvent(newClickGuiEvent(GuiEventType.BUTTON_CLICK, aButton));

        closeGesture.receive(eventList, statementList);

        Assert.assertEquals("Click n'est pas consommé", 1, eventList.size());
    }


    @Test
    public void test_simple_notInInternalFrame() throws Exception {
        JButton closeButton = new JButton();
        closeButton.getAccessibleContext().setAccessibleName(accessibleCloseName);

        eventList.addEvent(newClickGuiEvent(GuiEventType.BUTTON_CLICK, closeButton));

        closeGesture.receive(eventList, statementList);

        Assert.assertEquals("Click n'est pas consommé", 1, eventList.size());
    }


    @Before
    public void setUp() throws Exception {
        closeGesture = new CloseFrame();
        this.accessibleCloseName = UIManager.getString("InternalFrameTitlePane.closeButtonAccessibleName");
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
        System.out.println("frame.getAccessibleContext().getAccessibleName() = " + frame.getAccessibleContext().getAccessibleName());
        System.out.println("frame.getClass().getSimpleName() = " + frame.getClass().getSimpleName());
        return frame.getAccessibleContext() != null
               && accessibleCloseName.equals(frame.getAccessibleContext().getAccessibleName());
    }
}
