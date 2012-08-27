/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.event;
/**
 * GuiEventType de EVENT.
 *
 * @version $Revision: 1.1.1.1 $
 */
public final class GuiEventType {
    public static final GuiEventType MENU_CLICK = new GuiEventType("MENU_CLICK");
    public static final GuiEventType KEY = new GuiEventType("KEY");
    public static final GuiEventType COMBO_FOCUS_LOST = new GuiEventType("FOCUS_LOST");
    public static final GuiEventType COMBO_FOCUS_GAIN = new GuiEventType("FOCUS_GAIN");
    public static final GuiEventType BUTTON_CLICK = new GuiEventType("BUTTON_CLICK");
    public static final GuiEventType LIST_CLICK = new GuiEventType("LIST_CLICK");
    public static final GuiEventType TABLE_CLICK = new GuiEventType("TABLE_CLICK");
    public static final GuiEventType CHECKBOX_CLICK = new GuiEventType("CHECKBOX_CLICK");
    public static final GuiEventType TREE_PRE_CLICK = new GuiEventType("TREE_PRE_CLICK");
    public static final GuiEventType TREE_CLICK = new GuiEventType("TREE_CLICK");
    private final String myName;

    private GuiEventType(String name) {
        myName = name;
    }

    public String toString() {
        return myName;
    }
}
