/*
 * REPOWEB, repository manager.
 *
 * Terms of license - http://opensource.org/licenses/apachepl.php
 */
package recorder.gui.panel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import junit.framework.TestCase;
import recorder.gui.util.Util;
/**

 */
public class TogglePaneTest extends TestCase {
    private static final String SPLIT_NAME = "TogglePane.split";
    private static final int SPLIT_DIVIDER_SIZE = 10;
    private TogglePane pane;
    private JComponent firstPane;
    private String firstPaneLabel;
    private JPanel oneContent;
    private JComponent secondPane;
    private String secondPaneLabel;


    public void test_setContent() {
        pane.setContent(oneContent);
        assertNotNull("oneContent est affiché",
                      Util.findByName(oneContent.getName(), pane));
        assertSame(oneContent, Util.findByName(oneContent.getName(), pane));
    }


    public void test_setContent_cleanup() {
        pane.setContent(oneContent);
        pane.setContent(null);
        assertNull("oneContent n'est plus affiché",
                   Util.findByName(oneContent.getName(), pane));
    }


    public void test_noTabDisplayed_thenNoSplitDisplayed() {
        pane.addTab(firstPaneLabel, firstPane);
        AbstractButton firstButton = findByLabel(firstPaneLabel);

        assertNull("Pas de split au démarrage", findByName(SPLIT_NAME));

        firstButton.doClick();
        assertNotNull("Split à l'affichage du tab", findByName(SPLIT_NAME));

        firstButton.doClick();
        assertNull("Pas de split si le tab n'est plus affiché", findByName(SPLIT_NAME));
    }


    public void test_tabPosition_defaultSize() {
        pane.addTab(firstPaneLabel, firstPane);
        AbstractButton firstButton = findByLabel(firstPaneLabel);

        pane.setSize(0, 10 + SPLIT_DIVIDER_SIZE);
        firstPane.setPreferredSize(new Dimension(0, 4));

        firstButton.doClick();
        assertNotNull("Panel 1 est affiché", findByName(firstPane.getName()));

        JSplitPane splitPane = (JSplitPane)findByName(SPLIT_NAME);
        assertEquals(6, splitPane.getDividerLocation());
    }


    public void test_tabPosition_defaultSize_noPreferredSize() {
        pane.addTab(firstPaneLabel, firstPane);
        AbstractButton firstButton = findByLabel(firstPaneLabel);

        pane.setSize(0, 9 + SPLIT_DIVIDER_SIZE);

        firstButton.doClick();
        assertNotNull("Panel 1 est affiché", findByName(firstPane.getName()));

        JSplitPane splitPane = (JSplitPane)findByName(SPLIT_NAME);
        assertEquals(3, splitPane.getDividerLocation());
    }


    public void test_tabPosition() {
        pane.addTab(firstPaneLabel, firstPane);
        pane.addTab(secondPaneLabel, secondPane);

        AbstractButton firstButton = findByLabel(firstPaneLabel);
        AbstractButton secondButton = findByLabel(secondPaneLabel);

        firstButton.doClick();
        assertNotNull("Panel 1 est affiché", findByName(firstPane.getName()));

        JSplitPane splitPane = (JSplitPane)findByName(SPLIT_NAME);
        assertNotNull("Le split est affiché", splitPane);

        splitPane.setDividerLocation(10);

        secondButton.doClick();
        splitPane.setDividerLocation(20);

        firstButton.doClick();
        assertEquals(10, splitPane.getDividerLocation());

        secondButton.doClick();
        assertEquals(20, splitPane.getDividerLocation());
    }


    public void test_addTab_oneTab() {
        pane.addTab(firstPaneLabel, firstPane);

        AbstractButton button = findByLabel(firstPaneLabel);
        assertNotNull("Boutton 'first' existe", button);
        assertNull("Le pane n'est pas affiché par défaut", findByName(firstPane.getName()));

        button.doClick();

        assertSame("Le pane est affiché après un click sur le toggle", firstPane,
                   findByName(firstPane.getName()));

        button.doClick();

        assertNull("Le pane n'est plus affiché après un 2eme click sur le toggle",
                   findByName(firstPane.getName()));

        button.doClick();

        assertSame("Le pane est une nouvelle fois affiché après un click sur le toggle",
                   firstPane, findByName(firstPane.getName()));
    }


    public void test_addTab_twoTab() {
        pane.addTab(firstPaneLabel, firstPane);
        pane.addTab(secondPaneLabel, secondPane);

        AbstractButton firstButton = findByLabel(firstPaneLabel);
        AbstractButton secondButton = findByLabel(secondPaneLabel);

        firstButton.doClick();

        assertSame("Le pane 1 est affiché", firstPane, findByName(firstPane.getName()));
        assertFalse("Le button 2 est désactivé", secondButton.isSelected());

        secondButton.doClick();

        assertSame("Le pane 2 est affiché", secondPane, findByName(secondPane.getName()));
        assertNull("Le pane 1 n'est plus affiché", findByName(firstPane.getName()));
        assertFalse("Le button 1 est désactivé", firstButton.isSelected());
    }


    @Override
    protected void setUp() {
        pane = new TogglePane();

        oneContent = new JPanel();
        oneContent.setName("oneContent");

        firstPane = new JLabel("label firstPane");
        firstPane.setName("firstPane");
        firstPaneLabel = "Titre de " + firstPane.getName();

        secondPane = new JLabel("label secondPane");
        secondPane.setName("secondPane");
        secondPaneLabel = "Titre de " + secondPane.getName();
    }


    private Component findByName(String name) {
        return Util.findByName(name, pane);
    }


    private AbstractButton findByLabel(String label) {
        return Util.findButtonByLabel(label, pane);
    }


    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame frame = new JFrame("Essai composant GUI");

        TogglePane pane = new TogglePane();
        JLabel oneContent =
              new JLabel("<html> <b>content</b> <br> C'est chouette l'HTML");
        oneContent.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        pane.setContent(oneContent);
        pane.addTab("Project",
                    new TitledPanel("Mon projet", null, new JLabel("Un projet important")));
        pane.addTab("Log", new TitledPanel("Mon Log"));
        pane.addTab("Help", new JLabel("<html> <b>Aide</b> <br> <i>Chacun ca merde</i>"));

        frame.setContentPane(pane);
        frame.setSize(600, 400);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }


            @Override
            public void windowClosed(WindowEvent event) {
                System.exit(0);
            }
        });
    }
}
