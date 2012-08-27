/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.panel;
import java.awt.BorderLayout;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import recorder.gui.border.ShadowBorder;
/**
 * Panneau avec une bordure ombré ayant un titre avec icon et toolbar.
 */
public class TitledPanel extends JPanel {
    private HeaderPanel header;

    /**
     * Constructs a <code>TitledPanel</code> with the specified title.
     */
    public TitledPanel() {
        this(null, "title", null, null);
    }


    /**
     * Constructs a <code>TitledPanel</code> with the specified title.
     *
     * @param title the initial title
     */
    public TitledPanel(String title) {
        this(null, title, null, null);
    }


    /**
     * Constructs a <code>TitledPanel</code> with the specified icon, and title.
     *
     * @param icon the initial icon
     * @param title the initial title
     */
    public TitledPanel(Icon icon, String title) {
        this(icon, title, null, null);
    }


    /**
     * Constructs a <code>TitledPanel</code> with the specified title, tool bar, and
     * content panel.
     *
     * @param title the initial title
     * @param bar the initial tool bar
     * @param content the initial content pane
     */
    public TitledPanel(String title, JToolBar bar, JComponent content) {
        this(null, title, bar, content);
    }


    /**
     * Constructs a <code>TitledPanel</code> with the specified icon, title, tool bar,
     * and content panel.
     *
     * @param icon the initial icon
     * @param title the initial title
     * @param bar the initial tool bar
     * @param content the initial content pane
     */
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
