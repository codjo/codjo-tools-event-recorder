/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder.gui.script;
import javax.swing.JComponent;
import recorder.Recorder;
import recorder.RecorderListener;
import recorder.result.StatementList;
/**
 * Logic de l'IHM permettant d'éditer un script.
 */
public class ScriptLogic {
    private ScriptGui gui;
    private Recorder recorder;
    private RecorderListener listener =
        new RecorderListener() {
            public void recorderUpdate() {
                gui.display(recorder.getGestureResultList());
            }
        };

    public ScriptLogic(ScriptGui scriptGui, Recorder recorder) {
        this.gui = scriptGui;
        this.recorder = recorder;
        display(recorder.getGestureResultList());
        this.recorder.addRecorderListener(listener);
    }

    private void display(StatementList statementList) {
        this.gui.display(statementList);
    }


    public JComponent getGui() {
        return gui;
    }
}
