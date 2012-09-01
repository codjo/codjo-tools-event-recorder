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
package recorder.gui.script;
import javax.swing.*;
import recorder.Recorder;
import recorder.RecorderListener;
import recorder.result.StatementList;
public class ScriptLogic {
    private ScriptGui gui;


    public ScriptLogic(ScriptGui scriptGui, final Recorder recorder) {
        this.gui = scriptGui;
        display(recorder.getGestureResultList());
        RecorderListener listener = new RecorderListener() {
            public void recorderUpdate() {
                gui.display(recorder.getGestureResultList());
            }
        };
        recorder.addRecorderListener(listener);
    }


    private void display(StatementList statementList) {
        this.gui.display(statementList);
    }


    public JComponent getGui() {
        return gui;
    }
}
