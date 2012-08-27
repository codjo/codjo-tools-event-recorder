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
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import recorder.gui.border.RaisedHeaderBorder;
/**
 * Panneau avec une bordure ombré ayant un titre avec icon et toolbar.
 */
public class HeaderPanel extends GradientPanel {
    private JLabel titleLabel;
    private boolean isSelected;

    /**
     * Constructeur de <code>HeaderPanel</code>.
     */
    public HeaderPanel() {
        this(null, "title", null);
    }


    /**
     * Constructeur de <code>HeaderPanel</code>.
     *
     * @param title Titre du panneau.
     */
    public HeaderPanel(String title) {
        this(null, title, null);
    }


    /**
     * Constructeur de <code>HeaderPanel</code>.
     *
     * @param title Titre du panneau.
     * @param bar La toolbar
     */
    public HeaderPanel(String title, JToolBar bar) {
        this(null, title, bar);
    }


    /**
     * Constructeur de <code>HeaderPanel</code>.
     *
     * @param icon Icon du titre
     * @param title Titre du panneau.
     * @param bar La toolbar
     */
    public HeaderPanel(Icon icon, String title, JToolBar bar) {
        super(new BorderLayout());
        this.isSelected = false;

        this.titleLabel = new JLabel(title, icon, SwingConstants.LEADING);
        this.titleLabel.setOpaque(false);

        setBackground(getHeaderBackground());
        //@todo a remplacer par un INSET sur RaisedHeaderBorder (cf. EmptyBorder)
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


    /**
     * Retourne la toolbar du panneau.
     *
     * @return La toolbar (ou <code>null</code> si aucune).
     */
    public JToolBar getToolBar() {
        return getComponentCount() > 1 ? (JToolBar)getComponent(1) : null;
    }


    /**
     * Positionne la toolbar.
     *
     * @param newToolBar La nouvelle toolbar
     */
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


    /**
     * Indique si le panneau est selectionné (ie actif). Lorsque le panneau est actif, le
     * fond contient un dégradé.
     *
     * @return boolean  <code>true</code> si le panneau est actif.
     */
    public boolean isSelected() {
        return isSelected;
    }


    /**
     * Positione l'etat du panneau.
     *
     * @param newValue <code>true</code> si le panneau est actif
     *
     * @see #isSelected()
     */
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
