/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gesture;
import java.awt.Container;
import javax.swing.JInternalFrame;
import javax.swing.UIManager;
import recorder.component.FindStrategyId;
import recorder.component.GuiComponent;
import recorder.event.GuiEvent;
import recorder.event.GuiEventList;
import recorder.event.GuiEventType;
import recorder.result.AttributeList;
import recorder.result.DefaultStatement;
import recorder.result.StatementList;
/**
 * Detecte la fermeture d'une InternalFrame. Pour l'instant cet objet ne detecte que les
 * clicks sur le bouton de fermeture de la barre de titres.
 */
class CloseFrame extends AbstractGesture {
    CloseFrame() {
        super(GuiEventType.BUTTON_CLICK, FindStrategyId.BY_ACCESSIBLE_CONTEXT);
    }

    protected void receiveImpl(GuiEventList list, StatementList resultList) {
        GuiEvent event = list.peek();
        JInternalFrame internalFrame = findInternalFrame(event.getSource());
        if (internalFrame != null && isCloseButton(event)) {
            list.pop();
            resultList.add(new DefaultStatement("closeFrame",
                    AttributeList.singleton("title", internalFrame.getTitle())));
        }
    }


    private boolean isCloseButton(GuiEvent event) {
        String accessibleCloseName =
            UIManager.getString("InternalFrameTitlePane.closeButtonAccessibleName");

        return accessibleCloseName.equals(event.getSource().getAccessibleName());
    }


    private JInternalFrame findInternalFrame(GuiComponent source) {
        Container parent = source.getSwingComponent().getParent();
        while (parent != null && !(parent instanceof JInternalFrame)) {
            parent = parent.getParent();
        }
        return (JInternalFrame)parent;
    }
}
