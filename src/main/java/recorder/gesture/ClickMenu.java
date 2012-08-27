/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gesture;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import recorder.component.FindStrategyId;
import recorder.event.GuiEvent;
import recorder.event.GuiEventList;
import recorder.event.GuiEventType;
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
import recorder.result.StatementList;
/**
 * Reconnaît une selection dans un menu.
 */
class ClickMenu extends AbstractGesture {
    ClickMenu() {
        super(GuiEventType.MENU_CLICK, FindStrategyId.BY_LABEL);
    }

    protected void receiveImpl(GuiEventList list, StatementList resultList) {
        GuiEvent event = list.peek();

        if (!event.getSource().isA(JMenu.class) && invokedFromMenuBar(event)) {
            list.pop();

            final JMenuItem item = (JMenuItem)event.getSource().getSwingComponent();

            String menuPath = builldMenuPath(item.getParent());

            resultList.add(new DefaultStatement("click",
                    AttributeList.singleton("menu", menuPath + item.getText())));
        }
    }


    private String builldMenuPath(Container parent) {
        if (parent != null && (parent instanceof JPopupMenu)) {
            Component invoker = ((JPopupMenu)parent).getInvoker();

            if (invoker != null && invoker instanceof JMenu) {
                return builldMenuPath(invoker.getParent()) + ((JMenu)invoker).getText()
                + ":";
            }
        }

        return "";
    }


    private boolean invokedFromMenuBar(GuiEvent event) {
        final JMenuItem item = (JMenuItem)event.getSource().getSwingComponent();
        JPopupMenu popupMenu = (JPopupMenu)item.getParent();

        return popupMenu.getInvoker() instanceof JMenu;
    }
}
