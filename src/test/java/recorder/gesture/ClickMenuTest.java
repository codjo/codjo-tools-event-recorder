/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gesture;
import javax.swing.*;
import junit.framework.TestCase;
import recorder.component.GuiComponent;
import recorder.component.GuiComponentFactory;
import recorder.event.GuiEvent;
import recorder.event.GuiEventList;
import recorder.event.GuiEventType;
import recorder.result.StatementList;
/**

 */
public class ClickMenuTest extends TestCase {
    private StatementList result;
    private ClickMenu clickMenuGesture;
    private GuiEventList list;


    public void test_menu() throws Exception {
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        file.add(open);

        list.addEvent(new GuiEvent(GuiEventType.MENU_CLICK, toGui(file)));
        list.addEvent(new GuiEvent(GuiEventType.MENU_CLICK, toGui(open)));

        clickMenuGesture.receive(list, result);

        assertEquals("Click sur file n'est pas consommé", 2, list.size());

        list.pop();
        clickMenuGesture.receive(list, result);
        assertEquals("Click sur open est consommé", 0, list.size());

        assertEquals("<click menu=\"File:Open\"/>", result.toXml());
    }


    public void test_deep_menu() throws Exception {
        JMenu file = new JMenu("File");
        JMenu option = new JMenu("Option");
        JMenuItem changeLF = new JMenuItem("ChangeLF");
        file.add(option);
        option.add(changeLF).setName("bobo");

        list.addEvent(new GuiEvent(GuiEventType.MENU_CLICK, toGui(changeLF)));

        clickMenuGesture.receive(list, result);

        assertEquals("Click sur changeLF est consommé", 0, list.size());
        assertEquals("<click menu=\"File:Option:ChangeLF\"/>", result.toXml());
    }


    public void test_popupmenu() throws Exception {
        JPopupMenu popup = new JPopupMenu();
        popup.setInvoker(new JTextField());

        JMenuItem popupItem = new JMenuItem("Open");
        popup.add(popupItem);

        list.addEvent(new GuiEvent(GuiEventType.MENU_CLICK, toGui(popupItem)));

        clickMenuGesture.receive(list, result);

        assertEquals("Click sur popupItem n'est pas consommé", 1, list.size());
        assertEquals("", result.toXml());
    }


    @Override
    protected void setUp() throws Exception {
        result = new StatementList();
        clickMenuGesture = new ClickMenu();
        list = new GuiEventList();
    }


    private GuiComponent toGui(JComponent file) {
        return GuiComponentFactory.newGuiComponent(file);
    }
}
