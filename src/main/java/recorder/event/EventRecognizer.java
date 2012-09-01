/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.event;
import java.awt.*;
public interface EventRecognizer {
    boolean recognize(AWTEvent awtEvent);


    GuiEvent toGuiEvent(AWTEvent awtEvent);
}
