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
import org.junit.Assert;
import org.junit.Test;
public class GuiComponentFactoryTest {
    @Test
    public void test_container_noPoint() throws Exception {
        final JComponent source = new JLabel();

        GuiComponentFactory factory = new GuiComponentFactory();
        Assert.assertEquals(source, factory.find(source).getSwingComponent());
    }


    /**
     * JComboBox is composed of more atomic GUI component.
     */
    @Test
    public void test_jcombobox() {
        JComponent combo = new JComboBox();
        JComponent subComponent = new JLabel();
        combo.add(subComponent);

        GuiComponentFactory factory = new GuiComponentFactory();
        GuiComponent guiComponent = factory.find(subComponent);
        Assert.assertFalse("No name and therefore can not be found", guiComponent.isFindable());
        Assert.assertSame(combo, guiComponent.getSwingComponent());
    }


    @Test
    public void test_jcombobox_mousevent() {
        JComponent combo = new JComboBox();
        JComponent subComponent = new JLabel();
        combo.add(subComponent);
        GuiComponentFactory factory = new GuiComponentFactory();
        Assert.assertSame(combo, factory.find(newEvent(subComponent)).getSwingComponent());
    }


    @Test
    public void test_jcomponent() throws Exception {
        JComponent source = new JLabel();
        GuiComponentFactory factory = new GuiComponentFactory();
        Assert.assertSame(source, factory.find(newEvent(source)).getSwingComponent());
    }


    @Test
    public void test_excpetion() throws Exception {
        final JComponent source = new JLabel();
        JComponent container = new JPanel();
        container.add(source);
        container.setName("ignore");

        GuiComponentFactory factory = new GuiComponentFactory();
        factory.setIgnoredContainer("ignore");
        Assert.assertNull("The component is under an ignored one. It cannot be 'found'",
                          factory.find(newEvent(source)));
    }


    @Test
    public void test_container() throws Exception {
        final JComponent source = new JLabel();
        Container frame = newFrame(source);

        GuiComponentFactory factory = new GuiComponentFactory();
        Assert.assertSame(source, factory.find(newEvent(frame)).getSwingComponent());
    }


    @Test
    public void test_container_inContainer() throws Exception {
        Container frame = newFrame(new Container());

        GuiComponentFactory factory = new GuiComponentFactory();
        Assert.assertNull(factory.find(newEvent(frame)));
    }


    @Test
    public void test_container_empty() throws Exception {
        Container frame = newFrame(null);

        GuiComponentFactory factory = new GuiComponentFactory();
        Assert.assertNull(factory.find(newEvent(frame)));
    }


    private Container newFrame(final Component source) {
        return new JFrame() {
            @Override
            public Component findComponentAt(Point point) {
                if (point == null) {
                    throw new NullPointerException();
                }
                if (source != null) {
                    return source;
                }
                else {
                    return this;
                }
            }
        };
    }


    private MouseEvent newEvent(Component source) {
        return new MouseEvent(source, MouseEvent.MOUSE_ENTERED, 0, 0, 0, 0, 0, false);
    }
}
