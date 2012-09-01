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
package recorder.gui.icons;
import javax.swing.*;
public final class IconsManager {
    public static final IconId RECORD_START = new IconId("Irecord.start.png");
    public static final IconId RECORD_STOP = new IconId("Irecord.stop.png");
    public static final IconId LOG = new IconId("Ilog.png");
    public static final IconId SCRIPT = new IconId("Iscript.png");
    public static final IconId CLEAR = new IconId("Iscript.clear2.png");
    public static final IconId HIERARCHY = new IconId("Ihierarchy.gif");
    public static final IconId HIGHLIGHT_START = new IconId("Ihighlight.start.png");
    public static final IconId HIGHLIGHT_STOP = new IconId("Ihighlight.stop.png");
    public static final IconId REMOVE_LAST = new IconId("Iscript.clear.last.png");


    private IconsManager() {
    }


    public static ImageIcon getIcon(IconId iconId) {
        return new ImageIcon(IconsManager.class.getResource(iconId.fileName));
    }


    public static class IconId {
        private String fileName;


        IconId(String fileName) {
            this.fileName = fileName;
        }
    }
}
