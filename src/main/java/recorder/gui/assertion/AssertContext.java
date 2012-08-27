/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.assertion;
import java.awt.Component;
import java.awt.Point;
import recorder.Recorder;
import recorder.component.GuiComponent;
import recorder.result.Statement;
/**
 * Context des assertion.
 */
class AssertContext {
    private GuiComponent guiComponent;
    private Recorder recorder;
    private Point point;

    AssertContext(Recorder recorder) {
        if (recorder == null) {
            throw new IllegalArgumentException();
        }
        this.recorder = recorder;
    }

    public void setGuiComponent(GuiComponent guiComponent) {
        this.guiComponent = guiComponent;
    }


    public Component getSource() {
        return guiComponent.getSwingComponent();
    }


    public void postAssert(Statement assertStatement) {
        recorder.postGestureResult(assertStatement);
    }


    public Point getPoint() {
        return point;
    }


    public void setPoint(Point point) {
        this.point = point;
    }


    public boolean isFindableComponent() {
        return guiComponent.isFindable();
    }
}
