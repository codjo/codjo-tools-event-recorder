/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
/*
 * Copyright (c) 2003 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of JGoodies Karsten Lentzsch nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package recorder.gui.panel;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.SplitPaneUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
/**
 * Sous-classe de <code>JSplitPane</code> qui essaye de supprimer l'inesthétique divider.
 *
 * @see javax.swing.plaf.basic.BasicSplitPaneUI
 */
public final class LightSplitPane extends JSplitPane {
    /**
     * Constructeur par défaut d'un <code>LightSplitPane</code> avec une configuration
     * par défaut.
     *
     * @see JSplitPane()
     */
    public LightSplitPane() {
        this(JSplitPane.HORIZONTAL_SPLIT, false,
            new JButton(UIManager.getString("SplitPane.leftButtonText")),
            new JButton(UIManager.getString("SplitPane.rightButtonText")));
    }


    /**
     * Constructeur d'un <code>LightSplitPane</code>.
     *
     * @param newOrientation <code>JSplitPane.HORIZONTAL_SPLIT</code> ou
     *        <code>JSplitPane.VERTICAL_SPLIT</code>
     */
    public LightSplitPane(int newOrientation) {
        this(newOrientation, false);
    }


    /**
     * Constructeur d'un <code>LightSplitPane</code>.
     *
     * @param newOrientation <code>JSplitPane.HORIZONTAL_SPLIT</code> ou
     *        <code>JSplitPane.VERTICAL_SPLIT</code>
     * @param newContinuousLayout Un boolean, <code>true</code> pour que les composants
     *        s'affiche pendant que le divider est bougé, <code>false</code> sinon.
     */
    public LightSplitPane(int newOrientation, boolean newContinuousLayout) {
        this(newOrientation, newContinuousLayout, null, null);
    }


    /**
     * Constructeur d'un <code>LightSplitPane</code>.
     *
     * @param newOrientation <code>JSplitPane.HORIZONTAL_SPLIT</code> ou
     *        <code>JSplitPane.VERTICAL_SPLIT</code>
     * @param leftComponent Le composant mis à gauche (ou en haut)
     * @param rightComponent Le composant mis à droite (ou en bas)
     */
    public LightSplitPane(int newOrientation, Component leftComponent,
        Component rightComponent) {
        this(newOrientation, false, leftComponent, rightComponent);
    }


    /**
     * Constructeur d'un <code>LightSplitPane</code>.
     *
     * @param newOrientation <code>JSplitPane.HORIZONTAL_SPLIT</code> ou
     *        <code>JSplitPane.VERTICAL_SPLIT</code>
     * @param newContinuousLayout Un boolean, <code>true</code> pour que les composants
     *        s'affiche pendant que le divider est bougé, <code>false</code> sinon.
     * @param leftComponent Le composant mis à gauche (ou en haut)
     * @param rightComponent Le composant mis à droite (ou en bas)
     */
    public LightSplitPane(int newOrientation, boolean newContinuousLayout,
        Component leftComponent, Component rightComponent) {
        super(newOrientation, newContinuousLayout, leftComponent, rightComponent);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }

    /**
     * Maj de l'UI et masquage du Divider.
     * 
     * <p>
     * Le divider peut-être restore lors du changement de L&F, pour cette raison on
     * essaye de le masquer au changement.
     * </p>
     */
    public void updateUI() {
        super.updateUI();
        hideDivider();
    }


    private void hideDivider() {
        SplitPaneUI splitPaneUI = getUI();

        if (splitPaneUI instanceof BasicSplitPaneUI) {
            BasicSplitPaneUI basicUI = (BasicSplitPaneUI)splitPaneUI;
            BasicSplitPaneDivider divider = basicUI.getDivider();
            divider.setBorder(new BlankDividerBorder());
        }
    }

    /**
     * Border permettant de supprimer le divider des splitpane.
     */
    private class BlankDividerBorder extends AbstractBorder {
        public void paintBorder(Component component, Graphics graphics, int x, int y, int w, int h) {
            graphics.setColor(getBackground());
            graphics.fillRect(x, y, w, h);
        }
    }
}
