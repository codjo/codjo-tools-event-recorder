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
package recorder.event;
import javax.swing.tree.TreePath;
/**
 * Represents extracted data.
 */
public class TreeEventData {
    private TreePath path;
    private boolean collapsed;
    private boolean selected;


    public TreeEventData(TreePath path, boolean collapsed, boolean selected) {
        if (path == null) {
            throw new NullPointerException();
        }
        this.path = path;
        this.collapsed = collapsed;
        this.selected = selected;
    }


    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TreeEventData)) {
            return false;
        }

        final TreeEventData data = (TreeEventData)obj;
        return selected == data.selected && collapsed == data.collapsed
               && path.equals(data.path);
    }


    public int hashCode() {
        return path.hashCode();
    }


    public TreePath getPath() {
        return path;
    }


    public boolean isSelected() {
        return selected;
    }
}
