/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gesture;
import java.util.ArrayList;
import java.util.List;
import recorder.event.GuiEventList;
import recorder.result.StatementList;
public class GestureManager {
    private List<AbstractGesture> gestures = new ArrayList<AbstractGesture>();


    public GestureManager() {
        gestures.add(new ClickMenu());
        gestures.add(new SetValue());
        gestures.add(new ClickButton());
        gestures.add(new SelectRow());
        gestures.add(new CloseFrame());
    }


    public void proceed(GuiEventList source, StatementList result) {
        for (AbstractGesture gesture : gestures) {
            gesture.receive(source, result);
            if (source.size() == 0) {
                return;
            }
        }
    }
}
