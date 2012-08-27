/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import recorder.Recorder;
import recorder.component.GuiComponent;
import recorder.component.GuiComponentFactory;
import recorder.gui.action.ActionViewUtil;
import recorder.gui.action.GuiActionManager;
/**
 * Manager des assertion possibles.
 */
public class AssertManager implements AWTEventListener {
    private final DialogManager dialogManager;
    private final GuiActionManager actionManager = new GuiActionManager();
    private final AssertContext assertContext;
    private final GuiComponentFactory factory;

    public AssertManager(Recorder recorder, DialogManager dialogManager) {
        assertContext = new AssertContext(recorder);
        this.dialogManager = dialogManager;
        this.factory = recorder.getFinder();
        actionManager.declare(new AssertFrame(assertContext));
        actionManager.declare(new AssertSelected(assertContext));
        actionManager.declare(new AssertListSize(assertContext));
        actionManager.declare(new AssertList(assertContext));
        actionManager.declare(new AssertTable(assertContext));
        actionManager.declare(new AssertTree(assertContext));
        actionManager.declare(new AssertValue(assertContext));
    }

    public void start() {
        Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.MOUSE_EVENT_MASK);
    }


    public void stop() {
        Toolkit.getDefaultToolkit().removeAWTEventListener(this);
    }


    public void eventDispatched(AWTEvent event) {
        if (!isAssertTrigger(event)) {
            return;
        }

        MouseEvent mouse = (MouseEvent)event;

        GuiComponent guiComponent = factory.find(mouse);

        if (guiComponent == null) {
            return;
        }

        assertContext.setGuiComponent(guiComponent);
        assertContext.setPoint(mouse.getPoint());

        JPopupMenu popupMenu = dialogManager.newPopupMenu();

        for (Iterator iter = actionManager.actions(); iter.hasNext();) {
            AbstractAssert asserter = (AbstractAssert)iter.next();
            String actionId = (String)asserter.getValue(AbstractAssert.ACTION_ID);

            ActionViewUtil.connectActionTo(asserter, popupMenu.add(actionId));
            asserter.update();

            if (!guiComponent.isFindable()
                    && AbstractAssert.COMPONENT_ASSERT == asserter.getAssertType()) {
                asserter.setEnabled(false);
            }
        }

        popupMenu.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
        popupMenu.show(guiComponent.getSwingComponent(), mouse.getX(), mouse.getY());
    }


    AssertContext getAssertContext() {
        return assertContext;
    }


    protected boolean isAssertTrigger(AWTEvent event) {
        if (event == null || !(event instanceof MouseEvent)) {
            return false;
        }
        MouseEvent mouseEvent = ((MouseEvent)event);

        return MouseEvent.MOUSE_RELEASED == mouseEvent.getID()
        && SwingUtilities.isMiddleMouseButton(mouseEvent);
    }
}
