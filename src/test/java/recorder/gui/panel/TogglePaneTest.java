/*
 * codjo (Prototype)
 * =================
 *
 *    Copyright (C) 2005, 2012 by codjo.net
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *    implied. See the License for the specific language governing permissions
 *    and limitations under the License.
 */
package recorder.gui.panel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import org.junit.Before;
import org.junit.Test;
import recorder.gui.util.Util;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
/**

 */
public class TogglePaneTest {
    private static final String SPLIT_NAME = "TogglePane.split";
    private static final int SPLIT_DIVIDER_SIZE = 10;
    private TogglePane pane;
    private JComponent firstPane;
    private String firstPaneLabel;
    private JPanel oneContent;
    private JComponent secondPane;
    private String secondPaneLabel;


    @Test
    public void test_setContent() {
        pane.setContent(oneContent);
        assertNotNull("oneContent is displayed", Util.findByName(oneContent.getName(), pane));
        assertSame(oneContent, Util.findByName(oneContent.getName(), pane));
    }


    @Test
    public void test_setContent_cleanup() {
        pane.setContent(oneContent);
        pane.setContent(null);
        assertNull("oneContent is not displayed anymore",
                   Util.findByName(oneContent.getName(), pane));
    }


    @Test
    public void test_noTabDisplayed_thenNoSplitDisplayed() {
        pane.addTab(firstPaneLabel, firstPane);
        AbstractButton firstButton = findByLabel(firstPaneLabel);

        assertNull("No split at the beginning", findByName(SPLIT_NAME));

        firstButton.doClick();
        assertNotNull("Split appears when tab is displayed", findByName(SPLIT_NAME));

        firstButton.doClick();
        assertNull("No split when no tab is displayed", findByName(SPLIT_NAME));
    }


    @Test
    public void test_tabPosition_defaultSize() {
        pane.addTab(firstPaneLabel, firstPane);
        AbstractButton firstButton = findByLabel(firstPaneLabel);

        pane.setSize(0, 10 + SPLIT_DIVIDER_SIZE);
        firstPane.setPreferredSize(new Dimension(0, 4));

        firstButton.doClick();
        assertNotNull("Panel 1 is displayed", findByName(firstPane.getName()));

        JSplitPane splitPane = (JSplitPane)findByName(SPLIT_NAME);
        assertThat(splitPane.getDividerLocation(), anyOf(equalTo(6) /* Windows */,
                                                         equalTo(7)) /* OS X */);
    }


    @Test
    public void test_tabPosition_defaultSize_noPreferredSize() {
        pane.addTab(firstPaneLabel, firstPane);
        AbstractButton firstButton = findByLabel(firstPaneLabel);

        pane.setSize(0, 9 + SPLIT_DIVIDER_SIZE);

        firstButton.doClick();
        assertNotNull("Panel 1 is displayed", findByName(firstPane.getName()));

        JSplitPane splitPane = (JSplitPane)findByName(SPLIT_NAME);
        assertEquals(3, splitPane.getDividerLocation());
    }


    @Test
    public void test_tabPosition() {
        pane.addTab(firstPaneLabel, firstPane);
        pane.addTab(secondPaneLabel, secondPane);

        AbstractButton firstButton = findByLabel(firstPaneLabel);
        AbstractButton secondButton = findByLabel(secondPaneLabel);

        firstButton.doClick();
        assertNotNull("Panel 1 is displayed", findByName(firstPane.getName()));

        JSplitPane splitPane = (JSplitPane)findByName(SPLIT_NAME);
        assertNotNull("Split is displayed", splitPane);

        splitPane.setDividerLocation(10);

        secondButton.doClick();
        splitPane.setDividerLocation(20);

        firstButton.doClick();
        assertEquals(10, splitPane.getDividerLocation());

        secondButton.doClick();
        assertEquals(20, splitPane.getDividerLocation());
    }


    @Test
    public void test_addTab_oneTab() {
        pane.addTab(firstPaneLabel, firstPane);

        AbstractButton button = findByLabel(firstPaneLabel);
        assertNotNull("Button 'first' exists", button);
        assertNull("The pane is not displayed by default", findByName(firstPane.getName()));

        button.doClick();

        assertSame("The pane is displayed after a click on the toggle", firstPane,
                   findByName(firstPane.getName()));

        button.doClick();

        assertNull("The pane disappears after a second click on the toggle",
                   findByName(firstPane.getName()));

        button.doClick();

        assertSame("The pane is displayed after a new click on the toggle",
                   firstPane, findByName(firstPane.getName()));
    }


    @Test
    public void test_addTab_twoTab() {
        pane.addTab(firstPaneLabel, firstPane);
        pane.addTab(secondPaneLabel, secondPane);

        AbstractButton firstButton = findByLabel(firstPaneLabel);
        AbstractButton secondButton = findByLabel(secondPaneLabel);

        firstButton.doClick();

        assertSame("Pane 1 is displayed", firstPane, findByName(firstPane.getName()));
        assertFalse("Button 2 is off", secondButton.isSelected());

        secondButton.doClick();

        assertSame("Pane 2 is displayed", secondPane, findByName(secondPane.getName()));
        assertNull("Pane 1 is not anymore displayed", findByName(firstPane.getName()));
        assertFalse("Button 1 is off", firstButton.isSelected());
    }


    @Before
    public void setUp() {
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
        JFrame frame = new JFrame("Sample GUI");

        TogglePane pane = new TogglePane();
        JLabel oneContent =
              new JLabel("<html> <b>content</b> <br> HTML is easy");
        oneContent.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        pane.setContent(oneContent);
        pane.addTab("Project",
                    new TitledPanel("My project", null, new JLabel("An important project")));
        pane.addTab("Log", new TitledPanel("My Log"));
        pane.addTab("Help", new JLabel("<html> <b>Help</b> <br> <i>Beautiful help should be found here</i>"));

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
