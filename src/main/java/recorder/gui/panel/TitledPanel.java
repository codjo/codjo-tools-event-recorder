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
import javax.swing.*;
import recorder.gui.border.ShadowBorder;
/**
 * Panel with a shadow border containing a title, icon and a toolbar.
 */
public class TitledPanel extends JPanel {
    private HeaderPanel header;


    public TitledPanel() {
        this(null, "title", null, null);
    }


    public TitledPanel(String title) {
        this(null, title, null, null);
    }


    public TitledPanel(Icon icon, String title) {
        this(icon, title, null, null);
    }


    public TitledPanel(String title, JToolBar bar, JComponent content) {
        this(null, title, bar, content);
    }


    public TitledPanel(Icon icon, String title, JToolBar bar, JComponent content) {
        super(new BorderLayout());
        setBorder(new ShadowBorder());
        header = new HeaderPanel(icon, title, bar);
        add(header, BorderLayout.NORTH);
        if (content != null) {
            add(content, BorderLayout.CENTER);
        }
    }


    public HeaderPanel getHeader() {
        return header;
    }
}
