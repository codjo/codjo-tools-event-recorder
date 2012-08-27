/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gesture;
import recorder.event.GuiEventList;
import recorder.result.StatementList;

/**
 */
public interface Gesture {
    void receive(GuiEventList eventList, StatementList resultList);
}
