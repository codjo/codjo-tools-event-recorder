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
package recorder.component;
import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.*;
/**
 * Find the real underneath Swing component.
 *
 * <p> Some Swing components are composed of more basic components : JComboBox is a good example. </p>
 */
class ComponentResolver {
    private String ignoredContainer;


    public JComponent find(MouseEvent event) {
        return findImpl(event.getSource(), event.getPoint());
    }


    public JComponent find(AWTEvent event) {
        if (event instanceof MouseEvent) {
            return find((MouseEvent)event);
        }
        return findImpl(event.getSource(), null);
    }


    public JComponent find(Object source) {
        return findImpl(source, null);
    }


    public void setIgnoredContainer(String ignoredContainer) {
        this.ignoredContainer = ignoredContainer;
    }


    private JComponent findImpl(Object source, Point point) {
        JComponent found;

        if (source == null) {
            found = null;
        }
        else if (source instanceof JComponent) {
            JComponent component = (JComponent)source;
            JComboBox combo = findParentCombo(component);
            found = combo != null ? combo : component;
        }
        else {
            Component result = null;
            if (point != null) {
                result = ((Container)source).findComponentAt(point);
            }
            found = findImpl((source == result ? null : result), point);
        }

        return filter(found);
    }


    private JComponent filter(JComponent found) {
        if (found != null && ignoredContainer != null) {
            Component current = found;
            while (current != null) {
                if (ignoredContainer.equals(current.getName())) {
                    found = null;
                    break;
                }
                current = current.getParent();
            }
        }
        return found;
    }


    private JComboBox findParentCombo(Container comp) {
        if (comp == null) {
            return null;
        }
        else if (comp instanceof JComboBox) {
            return (JComboBox)comp;
        }
        return findParentCombo(comp.getParent());
    }
}
