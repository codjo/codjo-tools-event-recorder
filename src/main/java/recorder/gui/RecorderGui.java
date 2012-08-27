/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import recorder.gui.action.ActionViewUtil;
import recorder.gui.action.GuiAction;
import recorder.gui.icons.IconsManager;
import recorder.gui.panel.TitledPanel;
import recorder.gui.panel.TogglePane;
import recorder.gui.script.ScriptGui;
import recorder.result.StatementList;
/**
 * IHM du recorder.
 */
public class RecorderGui extends JPanel {
    private static final Insets NO_INSETS = new Insets(0, 0, 0, 0);
    private static final Border TEXT_BORDER = BorderFactory.createEmptyBorder(5, 5, 2, 5);
    private Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
    private JButton startButton = buildButton();
    private JButton stopButton = buildButton();
    private JButton highlightButton = buildButton();
    private JButton clearButton = buildButton();
    private JButton clearLogButton = buildButton();
    private JButton removeLastButton = buildButton();
    private JTextArea scriptDisplay;
    private JTextArea hierarchy;
    private JTextArea logArea;
    private ScriptGui scriptGui = new ScriptGui();

    public RecorderGui() {
        super(new BorderLayout());
        ButtonGroup group = new ButtonGroup();
        group.add(startButton);
        group.add(stopButton);

        JToolBar bar = new JToolBar();
        bar.add(startButton);
        bar.add(stopButton);
        bar.setOrientation(JToolBar.VERTICAL);
        bar.setFloatable(false);
        add(bar, BorderLayout.WEST);

        TogglePane global = new TogglePane();
        global.setContent(buildScriptResult());
        global.addTab("Log", buildLog());
        global.addTab("Structure", buildHierarchyResult());
        add(global, BorderLayout.CENTER);
    }

    public ScriptGui getScriptGui() {
        return scriptGui;
    }


    public void setClearAction(GuiAction action) {
        ActionViewUtil.connectActionTo(action, clearButton);
    }


    public void setClearLogAction(GuiAction action) {
        ActionViewUtil.connectActionTo(action, clearLogButton);
    }


    public void setStartAction(GuiAction action) {
        ActionViewUtil.connectActionTo(action, startButton);
    }


    public void setStopAction(GuiAction action) {
        ActionViewUtil.connectActionTo(action, stopButton);
    }


    public void setRemoveLastAction(GuiAction action) {
        ActionViewUtil.connectActionTo(action, removeLastButton);
    }


    public void setHighlightAction(GuiAction action) {
        ActionViewUtil.connectActionTo(action, highlightButton);
    }


    public void clearLogArea() {
        logArea.setText("");
    }


    public void display(StatementList gestureResultList) {
        scriptDisplay.setText(gestureResultList.toXml());
    }


    public void displayHierarchy(Component component) {
        StringBuffer buffer = new StringBuffer();
        displayTree(component, buffer);
        hierarchy.setText(buffer.toString());
    }


    public void displayLog(String message) {
        logArea.append(message);
        logArea.append("\n");
        scrollToDisplayLastLog();
    }


    private void scrollToDisplayLastLog() {
        try {
            Rectangle rectangle = logArea.modelToView(logArea.getText().length());
            if (rectangle != null) {
                logArea.scrollRectToVisible(rectangle);
            }
        }
        catch (BadLocationException error) {
            ; // ignore
        }
    }


    private JButton buildButton() {
        JButton recording = new JButton();
        recording.setBorder(emptyBorder);
        recording.setBorderPainted(false);
        return recording;
    }


    private String displayTree(Component component, StringBuffer buffer) {
        if (component == null) {
            return "";
        }
        String tab = displayTree(component.getParent(), buffer);

        buffer.append(tab).append(toSimpleName(component))
              .append(component.getName() != null ? "(" + component.getName() + ")" : "").append('\n');

        return tab + "  ";
    }


    private String toSimpleName(Component component) {
        String name = component.getClass().getName();
        int lastDot = name.lastIndexOf('.');
        if (lastDot == -1) {
            return name;
        }
        return name.substring(lastDot + 1);
    }


    private Component buildLog() {
        logArea = buildTextArea("log.display");
        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setBorder(null);

        JToolBar bar = new JToolBar();
        bar.add(clearLogButton);
        bar.setMargin(NO_INSETS);

        return new TitledPanel(IconsManager.getIcon(IconsManager.LOG), "Log", bar, scroll);
    }


    private JComponent buildScriptResult() {
        scriptDisplay = buildTextArea("script.display");

        JScrollPane scroll = new JScrollPane(scriptDisplay);
        scroll.setBorder(null);

//        scriptGui.setBorder(null);
//        JScrollPane treeScroll = new JScrollPane(scriptGui);
//        treeScroll.setBorder(null);
//
//        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
//        tabbedPane.addTab("xml", scroll);
//        tabbedPane.addTab("tree", treeScroll);
//
        JToolBar bar = new JToolBar();
        bar.add(removeLastButton);
        bar.add(clearButton);
        bar.setMargin(NO_INSETS);

        return new TitledPanel(IconsManager.getIcon(IconsManager.SCRIPT), "Script de test", bar,
            scroll);
    }


    private JComponent buildHierarchyResult() {
        hierarchy = new JTextArea();
        hierarchy.setBorder(TEXT_BORDER);
        hierarchy.setEditable(false);
        hierarchy.setBackground(Color.white);
        hierarchy.setName("hierarchy");
        hierarchy.setMargin(new Insets(10, 10, 10, 10));
        hierarchy.setRows(6);

        JScrollPane scroll = new JScrollPane(hierarchy);
        scroll.setBorder(null);

        JToolBar bar = new JToolBar();
        bar.add(highlightButton);
        bar.setMargin(NO_INSETS);

        return new TitledPanel(IconsManager.getIcon(IconsManager.HIERARCHY), "Structure", bar,
            scroll);
    }


    private JTextArea buildTextArea(String name) {
        JTextArea area = new JTextArea();
        area.setBorder(TEXT_BORDER);
        area.setEditable(false);
        area.setBackground(Color.white);
        area.setName(name);
        area.setRows(5);
        return area;
    }
}
