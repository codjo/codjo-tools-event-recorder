/*
 * codjo.net
 *
 * Common Apache License 2.0
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
