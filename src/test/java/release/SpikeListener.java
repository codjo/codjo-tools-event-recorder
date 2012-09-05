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
package release;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import javax.accessibility.AccessibleContext;
import javax.swing.*;
import javax.swing.tree.TreePath;
import org.apache.log4j.Logger;
import recorder.component.GuiComponent;
import recorder.component.GuiComponentFactory;
import recorder.gui.RecorderLogic;
/**
 * Listener AWT utilisé pour chercher des moyens de détecter une 'geste' de l'utilisateur.
 */
public class SpikeListener implements AWTEventListener {
    private static final Logger LOG = Logger.getLogger(RecorderLogic.class);


    public void eventDispatched(AWTEvent awtEvent) {
        GuiComponentFactory factory = new GuiComponentFactory();
        factory.setIgnoredContainer("recorder.mainPanel");

        if (awtEvent instanceof MouseEvent
            && awtEvent.getID() != MouseEvent.MOUSE_MOVED
            && awtEvent.getID() != MouseEvent.MOUSE_ENTERED
            && awtEvent.getID() != MouseEvent.MOUSE_EXITED) {
            GuiComponent data = factory.find(awtEvent.getSource());
            if (data == null) {
                return;
            }
            JComponent result = data.getSwingComponent();
            LOG.debug(awtEvent.paramString() + "\t" + result);
            if (result instanceof JComboBox) {
                JComboBox ss = (JComboBox)result;
                LOG.debug("\tcombo=" + ss.getSelectedItem());
            }
            if (result instanceof JTable) {
                JTable table = (JTable)result;
                int rowIdx = table.rowAtPoint(((MouseEvent)awtEvent).getPoint());
                LOG.debug("\ttable = " + table);
                LOG.debug("\trowIdx = " + rowIdx);
            }
            if (result instanceof JTree) {
                JTree tree = (JTree)result;
                Point point = ((MouseEvent)awtEvent).getPoint();
                TreePath path = tree.getClosestPathForLocation(point.x, point.y);
                int row = tree.getClosestRowForLocation(point.x, point.y);
                LOG.debug("\ttable = " + tree);
                LOG.debug("\tpath = " + path + "(" + row + ")");
                LOG.debug("\tpath.getLastPathComponent() = "
                          + path.getLastPathComponent());
                LOG.debug("\t isCollapsed = " + tree.isCollapsed(path));
                LOG.debug("\t isRowSelected = " + tree.isRowSelected(row));
            }
            if (result instanceof JList) {
                JList list = (JList)result;
                LOG.debug("\tlist=" + list.getSelectedValue());
                LOG.debug("\tlist="
                          + list.locationToIndex(((MouseEvent)awtEvent).getPoint()));
            }
            if (result instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox)result;
                LOG.debug("\tcheckBox=" + checkBox.isSelected());
            }
            if (result instanceof JButton) {
                JButton button = (JButton)result;
                LOG.debug("\tboutton.action=" + button.getAction());
                LOG.debug("\tboutton.actionCommand=" + button.getActionCommand());

                AccessibleContext context = button.getAccessibleContext();

                String accessibleName = context.getAccessibleName();
                LOG.debug("\tboutton.accessible.name =" + accessibleName);
            }
        }
        if (awtEvent instanceof WindowEvent
            && awtEvent.getID() == WindowEvent.WINDOW_CLOSING) {
            GuiComponent data = factory.find(awtEvent.getSource());
            System.out.println("awtEvent = " + awtEvent);
            System.out.println("awtEvent.getSource() = " + awtEvent.getSource());
            if (data == null) {
                System.out.println("close inconnue");
                return;
            }
            System.out.println("TreeEventData = " + data);
        }
    }
}
