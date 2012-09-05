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
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import javax.swing.*;
/**
 * GUI Component quite similar to {@link javax.swing.JTabbedPane} but using {@link javax.swing.JToggleButton} to switch between JPanel.
 */
public class TogglePane extends JPanel {
    private JPanel buttonPanel = new JPanel();
    private JSplitPane splitPane = new LightSplitPane(JSplitPane.VERTICAL_SPLIT, true);
    private TogglePaneController controller;
    private Map<Component, Integer> lastPositionByTabPanel = new WeakHashMap<Component, Integer>();
    private JComponent togglePaneContent;


    public TogglePane() {
        super(new BorderLayout());
        controller = new TogglePaneController(this);

        add(buttonPanel, BorderLayout.SOUTH);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        splitPane.setName("TogglePane.split");
    }


    public void addTab(String title, final Component component) {
        controller.addTabDescription(new TabDescription(title, component));
    }


    public void setContent(JComponent oneContent) {
        if (togglePaneContent != null) {
            remove(togglePaneContent);
        }
        this.togglePaneContent = oneContent;

        updateGui();
    }


    private void updateGui() {
        if (controller.isNothingSelected()) {
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
        button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.gray),
                                                            BorderFactory.createEmptyBorder(2, 5, 2, 5)));

        buttonPanel.add(button);
    }


    private void displayTab(Component component) {
        Component previousDisplayedTab = splitPane.getBottomComponent();
        lastPositionByTabPanel.put(previousDisplayedTab, splitPane.getDividerLocation());

        splitPane.setBottomComponent(component);

        if (component != null) {
            splitPane.setDividerLocation(determineDividerLocation(component));
        }

        updateGui();
    }


    private int determineDividerLocation(Component component) {
        if (lastPositionByTabPanel.containsKey(component)) {
            return lastPositionByTabPanel.get(component);
        }
        else {
            int realHeight = getHeight() - splitPane.getDividerSize() - buttonPanel.getHeight();

            Dimension preferredSize = component.getPreferredSize();
            int tabHeight = (preferredSize != null) ? preferredSize.height : Integer.MAX_VALUE;

            return (tabHeight < realHeight) ? realHeight - tabHeight : realHeight / 3;
        }
    }


    // Inner Class -----------------------------------------------------------------------------------------------------
    private static class TabDescription {
        private Component component;
        private JToggleButton button;


        TabDescription(String title, Component component) {
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
        private Map<ButtonModel, TabDescription> tabMap = new HashMap<ButtonModel, TabDescription>();


        void add(TabDescription tabDescription) {
            tabMap.put(tabDescription.getButton().getModel(), tabDescription);
        }


        TabDescription getDescription(ButtonModel model) {
            return tabMap.get(model);
        }
    }

    private static class TogglePaneController extends ButtonGroup {
        private JToggleButton nothingSelected = new JToggleButton();
        private TabModel tabModel;
        private TogglePane gui;


        TogglePaneController(TogglePane togglePane) {
            this.gui = togglePane;
            this.tabModel = new TabModel();
            nothingSelected.setSelected(true);
            add(nothingSelected);
        }


        void addTabDescription(TabDescription tabDescription) {
            tabModel.add(tabDescription);
            this.add(tabDescription.getButton());
            gui.addButton(tabDescription.getButton());
        }


        boolean isNothingSelected() {
            return nothingSelected.getModel() == getSelection();
        }


        @Override
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
