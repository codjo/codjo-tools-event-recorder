/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
/*
 * REPOWEB, repository manager.
 *
 * Terms of license - http://opensource.org/licenses/apachepl.php
 */
package recorder.gui.panel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
/**
 * Un composant similaire à un {@link javax.swing.JTabbedPane} mais utilisant des {@link
 * javax.swing.JToggleButton} comme onglet.
 */
public class TogglePane extends JPanel {
    private JPanel buttonPanel = new JPanel();
    private JSplitPane splitPane = new LightSplitPane(JSplitPane.VERTICAL_SPLIT, true);
    private TogglePaneControler controler;
    private Map lastPositionByTabPanel = new WeakHashMap();
    private JComponent togglePaneContent;

    public TogglePane() {
        super(new BorderLayout());
        controler = new TogglePaneControler(this);

        add(buttonPanel, BorderLayout.SOUTH);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        splitPane.setName("TogglePane.split");
    }

    public void addTab(String title, final Component component) {
        controler.addTabDescription(new TabDescritpion(title, component));
    }


    public void setContent(JComponent oneContent) {
        if (togglePaneContent != null) {
            remove(togglePaneContent);
        }
        this.togglePaneContent = oneContent;

        updateGui();
    }


    private void updateGui() {
        if (controler.isNothingSelected()) {
            remove(splitPane);
            if (togglePaneContent != null) {
                add(togglePaneContent, BorderLayout.CENTER);
            }
        }
        else {
            splitPane.setTopComponent(togglePaneContent);
            add(splitPane, BorderLayout.CENTER);
        }
        revalidate();
    }


    private void addButton(JToggleButton button) {
        button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(
                    Color.gray), BorderFactory.createEmptyBorder(2, 5, 2, 5)));

        buttonPanel.add(button);
    }


    private void displayTab(Component component) {
        Component previousDisplayedTab = splitPane.getBottomComponent();
        lastPositionByTabPanel.put(previousDisplayedTab, new Integer(splitPane.getDividerLocation()));

        splitPane.setBottomComponent(component);

        if (component != null) {
            splitPane.setDividerLocation(determineDividerLocation(component));
        }

        updateGui();
    }


    private int determineDividerLocation(Component component) {
        if (lastPositionByTabPanel.containsKey(component)) {
            return ((Integer)lastPositionByTabPanel.get(component)).intValue();
        }
        else {
            int realHeight = getHeight() - splitPane.getDividerSize() - buttonPanel.getHeight();

            Dimension preferredSize = component.getPreferredSize();
            int tabHeight = (preferredSize != null) ? preferredSize.height : Integer.MAX_VALUE;

            return (tabHeight < realHeight) ? realHeight - tabHeight : realHeight / 3;
        }
    }

    // Inner Class -----------------------------------------------------------------------------------------------------
    private static class TabDescritpion {
        private Component component;
        private JToggleButton button;

        TabDescritpion(String title, Component component) {
            this.component = component;
            button = new JToggleButton(title);
        }

        public JToggleButton getButton() {
            return button;
        }


        public Component getComponent() {
            return component;
        }
    }


    private static class TabModel {
        private Map tabMap = new HashMap();

        void add(TabDescritpion tabDescritpion) {
            tabMap.put(tabDescritpion.getButton().getModel(), tabDescritpion);
        }


        TabDescritpion getDescription(ButtonModel model) {
            return (TabDescritpion)tabMap.get(model);
        }
    }


    private static class TogglePaneControler extends ButtonGroup {
        private JToggleButton nothingSelected = new JToggleButton();
        private TabModel tabModel;
        private TogglePane gui;

        TogglePaneControler(TogglePane togglePane) {
            this.gui = togglePane;
            this.tabModel = new TabModel();
            nothingSelected.setSelected(true);
            add(nothingSelected);
        }

        void addTabDescription(TabDescritpion tabDescritpion) {
            tabModel.add(tabDescritpion);
            this.add(tabDescritpion.getButton());
            gui.addButton(tabDescritpion.getButton());
        }


        boolean isNothingSelected() {
            return nothingSelected.getModel() == getSelection();
        }


        public void setSelected(ButtonModel model, boolean isSelected) {
            if (!isSelected && model == getSelection()) {
                nothingSelected.setSelected(true);
                return;
            }

            super.setSelected(model, isSelected);

            updateDisplay(isSelected, model);
        }


        private void updateDisplay(boolean isSelected, ButtonModel model) {
            if (isNothingSelected(isSelected, model)) {
                gui.displayTab(null);
            }
            else if (isSelected) {
                gui.displayTab(this.tabModel.getDescription(model).getComponent());
            }
        }


        private boolean isNothingSelected(boolean isSelected, ButtonModel model) {
            return isSelected && model == getSelection() && model == nothingSelected.getModel();
        }
    }
}
