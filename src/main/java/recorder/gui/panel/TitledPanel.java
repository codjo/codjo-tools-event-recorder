/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.panel;
import java.awt.*;
import javax.swing.*;
import recorder.gui.border.ShadowBorder;
/**
 * Panel with a shadow border containing a title, icon and a toolbar.
 */
public class TitledPanel extends JPanel {
    private HeaderPanel header;


    public TitledPanel() {
        this(null, "title", null, null);
    }


    public TitledPanel(String title) {
        this(null, title, null, null);
    }


    public TitledPanel(Icon icon, String title) {
        this(icon, title, null, null);
    }


    public TitledPanel(String title, JToolBar bar, JComponent content) {
        this(null, title, bar, content);
    }


    public TitledPanel(Icon icon, String title, JToolBar bar, JComponent content) {
        super(new BorderLayout());
        setBorder(new ShadowBorder());
        header = new HeaderPanel(icon, title, bar);
        add(header, BorderLayout.NORTH);
        if (content != null) {
            add(content, BorderLayout.CENTER);
        }
    }


    public HeaderPanel getHeader() {
        return header;
    }
}
