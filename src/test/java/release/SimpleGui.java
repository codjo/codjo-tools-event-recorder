/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package release;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;
/**
 * IHM simple pour les tests manuelles de l'EventRecorder.
 */
public class SimpleGui extends JPanel {
    private JDesktopPane desktopPane;
    private int numberOfFrame = 1;

    public SimpleGui() {
        super(new BorderLayout());
        setName("me.simple");

        JPanel topPanel = buildTopPanel();
        desktopPane = new JDesktopPane();

        JPanel bottomPanel = new JPanel();
        addTree(bottomPanel);


        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.EAST);
        this.add(desktopPane, BorderLayout.CENTER);

        createFrame();
    }

    private void createFrame() {
        JInternalFrame intFrame =
            new JInternalFrame("Internal frame " + numberOfFrame, true, true, true, true);

        intFrame.setContentPane(new JPanel());
        intFrame.getContentPane().add(new JLabel("<html> Le contenu <br> blabla"));

        intFrame.setVisible(true);

        intFrame.setLocation(10, 22);
        intFrame.setSize(300, 100);
        desktopPane.add(intFrame);
        try {
            intFrame.setSelected(true);
        }
        catch (java.beans.PropertyVetoException e) {
            ;
        }

        numberOfFrame++;
    }


    private JPanel buildTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(500, 150));

        topPanel.add(new JLabel("un label"));
        topPanel.add(putName("text.period", textWithPopup()));
        topPanel.add(new JLabel("un autre label"));
        topPanel.add(putName("text.other", textWithActionMap()));
        topPanel.add(new JLabel("un sans nom"));
        topPanel.add(putName(null, new JTextField()));

        topPanel.add(new JLabel("une combo"));
        JComboBox comboBox = new JComboBox(new Object[] {"aa", "bb"});
        comboBox.setName("ma.combo");
        topPanel.add(comboBox);

        addDialogButton(topPanel);

        JButton clicker = new JButton("new frame");
        clicker.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    createFrame();
                }
            });
        clicker.setName("mon.boutton");
        topPanel.add(clicker);

        topPanel.add(new JLabel("une table"));
        JTable table =
            new JTable(new Object[][] {
                    {"a1", "a2"},
                    {"b1", "b2"}
                }, new Object[] {"col 1", "col 2"});
        table.setName("ma.table");
        topPanel.add(table);

        topPanel.add(new JLabel("une liste"));
        JList list = new JList(new Object[] {"a1", "a2", "a3"});
        list.setName("ma.liste");
        topPanel.add(list);

        JCheckBox checkBox = new JCheckBox("checkbox");
        checkBox.setName("ma.checkbox");
        topPanel.add(checkBox);

        return topPanel;
    }


    private void addTree(JPanel panel) {
        DefaultMutableTreeNode books = new DefaultMutableTreeNode("books", true);

        DefaultMutableTreeNode java = new DefaultMutableTreeNode("java", true);
        java.add(new DefaultMutableTreeNode("TDD", false));

        books.add(java);
        books.add(new DefaultMutableTreeNode("Fondation", false));

        JTree tree = new JTree(books);
        tree.setName("ma.jtree");
        panel.add(new JScrollPane(tree));

        tree.setPreferredSize(new Dimension(50,100));
    }


    private void addDialogButton(JPanel topPanel) {
        JButton clicker = new JButton("Questions");
        clicker.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    JOptionPane.showConfirmDialog(SimpleGui.this,
                        "Would you like green eggs and ham?", "An Inane Question",
                        JOptionPane.YES_NO_OPTION);
                }
            });
        clicker.setName("ask.sthg");
        topPanel.add(clicker);
    }


    private JTextField textWithActionMap() {
        final JTextField textField = new JTextField();
        InputMap im = textField.getInputMap(JComponent.WHEN_FOCUSED);
        im.put(KeyStroke.getKeyStroke("UP"), "myAction");
        im.put(KeyStroke.getKeyStroke("shift F2"), "myAction");

        // Get an ActionMap from the desired type of component and initialize it
        ActionMap am = textField.getActionMap();
        am.put("myAction",
            new AbstractAction("myAction") {
                public void actionPerformed(ActionEvent evt) {
                    System.out.println("textField.getText() = " + textField.getText());
                }
            });

        return textField;
    }


    private JTextField textWithPopup() {
        JTextField jTextField = new JTextField();

        final JPopupMenu popup = new JPopupMenu();
        popup.add("Just Do It");
        popup.add("Develop with pleasure");

        MouseListener popupListener =
            new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    maybeShowPopup(e);
                }


                public void mouseReleased(MouseEvent e) {
                    maybeShowPopup(e);
                }


                private void maybeShowPopup(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        popup.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            };

        jTextField.addMouseListener(popupListener);
        return jTextField;
    }


    private JComponent putName(String name, JTextField comp) {
        comp.setColumns(10);
        comp.setName(name);
        return comp;
    }
}
