/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gesture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import recorder.event.GuiEventList;
import recorder.result.StatementList;
/**
 * Manager des gestures.
 */
public class GestureManager {
    private List gestures = new ArrayList();

    public GestureManager() {
        gestures.add(new ClickMenu());
        gestures.add(new SetValue());
        gestures.add(new ClickButton());
        gestures.add(new SelectRow());
        gestures.add(new CloseFrame());
    }

    public void proceed(GuiEventList source, StatementList result) {
        for (Iterator iter = gestures.iterator(); iter.hasNext();) {
            Gesture gesture = (Gesture)iter.next();
            gesture.receive(source, result);
            if (source.size() == 0) {
                return;
            }
        }
    }
}
