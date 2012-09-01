/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.component;
import javax.swing.*;
/**
 * Search strategy using a GUI component label.
 *
 * <p> <b>NB:</b>Only JMenuItem are handled by this strategy. </p>
 */
class FindByLabel implements FindStrategy {
    public boolean canFound(JComponent swingComponent) {
        return swingComponent instanceof AbstractButton
               && isEmpty(((AbstractButton)swingComponent).getText());
    }


    private boolean isEmpty(String text) {
        return text != null && text.length() != 0;
    }


    public FindStrategyId getStrategyId() {
        return FindStrategyId.BY_LABEL;
    }


    public String getLabelFor(JComponent component) {
        return ((AbstractButton)component).getText();
    }
}
