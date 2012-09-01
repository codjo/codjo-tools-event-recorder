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
package recorder.gui.assertion;
import java.awt.*;
import recorder.Recorder;
import recorder.component.GuiComponent;
import recorder.result.Statement;
class AssertContext {
    private GuiComponent guiComponent;
    private Recorder recorder;
    private Point point;


    AssertContext(Recorder recorder) {
        if (recorder == null) {
            throw new IllegalArgumentException();
        }
        this.recorder = recorder;
    }


    public void setGuiComponent(GuiComponent guiComponent) {
        this.guiComponent = guiComponent;
    }


    public Component getSource() {
        return guiComponent.getSwingComponent();
    }


    public void postAssert(Statement assertStatement) {
        recorder.postGestureResult(assertStatement);
    }


    public Point getPoint() {
        return point;
    }


    public void setPoint(Point point) {
        this.point = point;
    }


    public boolean isFindableComponent() {
        return guiComponent.isFindable();
    }
}
