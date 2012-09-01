/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
/*
 * Copyright (c) 2003 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * This software is the proprietary information of Karsten Lentzsch.
 * Use is subject to license terms.
 *
 */
package recorder.gui.panel;
import java.awt.*;
import javax.swing.*;
import recorder.gui.border.RaisedHeaderBorder;
/**
 * Panel with a shadow border containing a title with an icon and a toolbar.
 */
public class HeaderPanel extends GradientPanel {
    private JLabel titleLabel;
    private boolean isSelected;


    public HeaderPanel() {
        this(null, "title", null);
    }


    public HeaderPanel(String title) {
        this(null, title, null);
    }


    public HeaderPanel(String title, JToolBar bar) {
        this(null, title, bar);
    }


    public HeaderPanel(Icon icon, String title, JToolBar bar) {
        super(new BorderLayout());
        this.isSelected = false;

        this.titleLabel = new JLabel(title, icon, SwingConstants.LEADING);
        this.titleLabel.setOpaque(false);

        setBackground(getHeaderBackground());
        //@todo to be replaced by an INSET on the RaisedHeaderBorder (cf. EmptyBorder)
        setBorder(BorderFactory.createCompoundBorder(new RaisedHeaderBorder(),
                                                     BorderFactory.createEmptyBorder(3, 4, 3, 1)));

        add(this.titleLabel, BorderLayout.WEST);
        setToolBar(bar);
        setSelected(true);
        updateHeader();
    }


    public Icon getIcon() {
        return titleLabel.getIcon();
    }


    public void setIcon(Icon newIcon) {
        Icon oldIcon = getIcon();
        titleLabel.setIcon(newIcon);
        firePropertyChange("icon", oldIcon, newIcon);
    }


    public String getTitle() {
        return titleLabel.getText();
    }


    public void setTitle(String newText) {
        String oldText = getTitle();
        titleLabel.setText(newText);
        firePropertyChange("title", oldText, newText);
    }


    public JToolBar getToolBar() {
        return getComponentCount() > 1 ? (JToolBar)getComponent(1) : null;
    }


    public void setToolBar(JToolBar newToolBar) {
        JToolBar oldToolBar = getToolBar();
        if (oldToolBar == newToolBar) {
            return;
        }
        if (oldToolBar != null) {
            remove(oldToolBar);
        }
        if (newToolBar != null) {
            newToolBar.setOpaque(false);
            newToolBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            add(newToolBar, BorderLayout.EAST);
        }
        updateHeader();
        firePropertyChange("toolBar", oldToolBar, newToolBar);
    }


    public boolean isSelected() {
        return isSelected;
    }


    public void setSelected(boolean newValue) {
        boolean oldValue = isSelected();
        isSelected = newValue;
        updateHeader();
        firePropertyChange("selected", oldValue, newValue);
    }


    private void updateHeader() {
        setOpaque(isSelected());
        titleLabel.setForeground(getTextForeground(isSelected()));
        repaint();
    }


    private Color getTextForeground(boolean selected) {
        Color color =
              UIManager.getColor(selected ? "HeaderPanel.activeTitleForeground"
                                          : "HeaderPanel.inactiveTitleForeground");
        if (color != null) {
            return color;
        }
        return UIManager.getColor(selected ? "InternalFrame.activeTitleForeground"
                                           : "Label.foreground");
    }


    private Color getHeaderBackground() {
        Color color = UIManager.getColor("HeaderPanel.activeTitleBackground");
        if (color != null) {
            return color;
        }
        else {
            return UIManager.getColor("InternalFrame.activeTitleBackground");
        }
    }
}
