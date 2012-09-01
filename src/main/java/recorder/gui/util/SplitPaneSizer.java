/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.util;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

public class SplitPaneSizer {
    private JSplitPane content;
    private Limit min = new ProportionalLimit(0);
    private Limit max = new ProportionalLimit(1);
    private DividerLocationListener listener = new DividerLocationListener();


    public SplitPaneSizer(JSplitPane content, double min, double max) {
        this.content = content;
        this.min = new ProportionalLimit(min);
        this.max = new ProportionalLimit(max);
        ensureLocationLimit();
    }


    public SplitPaneSizer(JSplitPane content, int min, int max) {
        this.content = content;
        this.min = new FixLimit(min);
        this.max = new FixLimit(max);
        ensureLocationLimit();
    }


    public void start() {
        content.addComponentListener(listener);
        content.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, listener);
    }


    public void stop() {
        content.removeComponentListener(listener);
        content.removePropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
                                             listener);
    }


    protected void ensureLocationLimit() {
        int size = determineSize();
        final int bottomHeight = size - content.getDividerLocation();

        int maxSize = max.determineLimit(size);
        int minSize = min.determineLimit(size);

        if (bottomHeight > maxSize && (size - maxSize > 0)) {
            content.setDividerLocation(size - maxSize);
        }
        else if (bottomHeight < minSize && (size > minSize)) {
            content.setDividerLocation(size - minSize);
        }
    }


    private int determineSize() {
        if (content.getOrientation() == JSplitPane.VERTICAL_SPLIT) {
            return content.getHeight();
        }
        else {
            return content.getWidth();
        }
    }


    private static interface Limit {
        public int determineLimit(int componentSize);
    }

    private static class ProportionalLimit implements Limit {
        private double limit = 0;


        ProportionalLimit(double limit) {
            this.limit = limit;
        }


        public int determineLimit(int componentSize) {
            return (int)(componentSize * limit);
        }
    }

    private static class FixLimit implements Limit {
        private int limit = 0;


        FixLimit(int limit) {
            this.limit = limit;
        }


        public int determineLimit(int componentSize) {
            return limit;
        }
    }

    private class DividerLocationListener extends ComponentAdapter implements PropertyChangeListener {
        @Override
        public void componentResized(ComponentEvent event) {
            ensureLocationLimit();
        }


        public void propertyChange(PropertyChangeEvent event) {
            ensureLocationLimit();
        }
    }
}
