/*
 * codjo.net
 *
 * Common Apache License 2.0
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
