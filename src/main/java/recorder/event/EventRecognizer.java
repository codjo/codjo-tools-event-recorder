/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.event;
import java.awt.AWTEvent;
/**
 * Interface decrivant un objet capable de construire un GuiEvent à partir d'un AWTEvent.
 */
public interface EventRecognizer {
    boolean recognize(AWTEvent awtEvent);


    GuiEvent toGuiEvent(AWTEvent awtEvent);
}
