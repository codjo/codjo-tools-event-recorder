/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.event;
import java.util.ArrayList;
import java.util.List;

public class GuiEventList {
    private List<GuiEvent> eventList = new ArrayList<GuiEvent>();
    private List<GuiEvent> consumedList = new ArrayList<GuiEvent>();
    private long consumedEventLimit = 100;


    public GuiEventList() {
    }


    public long size() {
        return eventList.size();
    }


    public void addEvent(GuiEvent event) {
        eventList.add(event);
    }


    public GuiEvent pop() {
        GuiEvent removedEvent = eventList.remove(0);
        consumedList.add(0, removedEvent);
        if (consumedList.size() >= getConsumedEventLimit()) {
            consumedList.remove(consumedList.size() - 1);
        }
        return removedEvent;
    }


    public GuiEvent peek() {
        if (size() == 0) {
            return null;
        }
        return eventList.get(0);
    }


    public GuiEvent findPrevious(GuiEvent mask) {
        for (GuiEvent event : consumedList) {
            if (match(mask, event)) {
                return event;
            }
        }
        return null;
    }


    private boolean match(GuiEvent mask, GuiEvent event) {
        return (mask.getType() == null || mask.getType() == event.getType())
               && (mask.getSource() == null || mask.getSource().equals(event.getSource()));
    }


    public String toString() {
        return "GuiEventList{" + "eventList=" + eventList + "}";
    }


    public void clear() {
        eventList.clear();
        consumedList.clear();
    }


    public long getConsumedEventLimit() {
        return consumedEventLimit;
    }


    public void setConsumedEventLimit(long consumedEventLimit) {
        this.consumedEventLimit = consumedEventLimit;
    }
}
