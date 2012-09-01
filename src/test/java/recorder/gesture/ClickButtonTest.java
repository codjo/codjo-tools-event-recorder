/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gesture;
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
public class ClickButtonTest {
    private ClickButton clickGesture;


    @Test
    public void test_simple() throws Exception {
        JButton file = new JButton("File");
        file.setName("my.button");

        GuiEventList list = new GuiEventList();
        list.addEvent(new GuiEvent(GuiEventType.BUTTON_CLICK,
                                   GuiComponentFactory.newGuiComponent(file)));

        StatementList result = new StatementList();

        clickGesture.receive(list, result);

        Assert.assertEquals("Click est consommé", 0, list.size());

        Assert.assertEquals("<click name=\"my.button\"/>", result.toXml());
    }


    @Test
    public void test_simple_noName() throws Exception {
        JButton file = new JButton("File");

        GuiEventList list = new GuiEventList();
        list.addEvent(new GuiEvent(GuiEventType.BUTTON_CLICK,
                                   GuiComponentFactory.newGuiComponent(file)));

        StatementList result = new StatementList();

        clickGesture.receive(list, result);

        Assert.assertEquals("Click est consommé", 0, list.size());

        Assert.assertEquals("<click label=\"File\"/>", result.toXml());
    }


    @Test
    public void test_simple_noName_noLabel() throws Exception {
        JButton file = new JButton();

        GuiEventList list = new GuiEventList();
        list.addEvent(new GuiEvent(GuiEventType.BUTTON_CLICK,
                                   GuiComponentFactory.newGuiComponent(file)));

        StatementList result = new StatementList();

        clickGesture.receive(list, result);

        Assert.assertEquals("Click n'est pas consommé", 1, list.size());

        Assert.assertEquals("", result.toXml());
    }


    @Before
    public void setUp() throws Exception {
        clickGesture = new ClickButton();
    }
}
