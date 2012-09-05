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
package recorder.gesture;
import java.util.ArrayList;
import java.util.List;
import recorder.event.GuiEventList;
import recorder.result.StatementList;
public class GestureManager {
    private List<AbstractGesture> gestures = new ArrayList<AbstractGesture>();


    public GestureManager() {
        gestures.add(new ClickMenu());
        gestures.add(new SetValue());
        gestures.add(new ClickButton());
        gestures.add(new SelectRow());
        gestures.add(new CloseFrame());
    }


    public void proceed(GuiEventList source, StatementList result) {
        for (AbstractGesture gesture : gestures) {
            gesture.receive(source, result);
            if (source.size() == 0) {
                return;
            }
        }
    }
}
