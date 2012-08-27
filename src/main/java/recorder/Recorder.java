/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package recorder;
import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import recorder.component.GuiComponentFactory;
import recorder.event.EventRecognizerManager;
import recorder.event.GuiEvent;
import recorder.event.GuiEventList;
import recorder.gesture.GestureManager;
import recorder.result.Statement;
import recorder.result.StatementList;
/**
 * Enregistre les actions utilisateurs sur les composants nommés.
 */
public class Recorder implements AWTEventListener {
    private GuiEventList simpleEventList = new GuiEventList();
    private StatementList gestureResultList = new StatementList();
    private RecorderEventSupport eventSupport = new RecorderEventSupport();
    private final GestureManager gestureManager;
    private final EventRecognizerManager recognizerManager;

    public Recorder(GuiComponentFactory factory) {
        recognizerManager = new EventRecognizerManager(factory);
        gestureManager = new GestureManager();
    }

    public void startRecord() {
        Toolkit.getDefaultToolkit().addAWTEventListener(this, Long.MAX_VALUE);
    }


    public void stopRecord() {
        Toolkit.getDefaultToolkit().removeAWTEventListener(this);
    }


    public void eventDispatched(AWTEvent awtEvent) {
        GuiEvent simpleEvent = recognizerManager.toGuiEvent(awtEvent);

        if (simpleEvent != null) {
            simpleEventList.addEvent(simpleEvent);
            computeGestureResult();
            eventSupport.fireRecorderUpdate();
        }
    }


    public GuiEventList getSimpleEventList() {
        return simpleEventList;
    }


    public StatementList getGestureResultList() {
        return gestureResultList;
    }


    private void computeGestureResult() {
        while (simpleEventList.size() != 0) {
            long from = simpleEventList.size();

            gestureManager.proceed(simpleEventList, gestureResultList);

            if (from == simpleEventList.size()) {
                simpleEventList.pop();
            }
        }
    }


    public void addRecorderListener(RecorderListener listener) {
        eventSupport.addRecorderListener(listener);
    }


    public void removeRecorderListener(RecorderListener listener) {
        eventSupport.removeRecorderListener(listener);
    }


    public void clearScript() {
        simpleEventList.clear();
        gestureResultList.clear();
        eventSupport.fireRecorderUpdate();
    }


    public void removeLastGesture() {
        gestureResultList.removeLastResult();
        eventSupport.fireRecorderUpdate();
    }


    public void postGestureResult(Statement result) {
        gestureResultList.add(result);
        eventSupport.fireRecorderUpdate();
    }


    public GuiComponentFactory getFinder() {
        return recognizerManager.getGuiComponentFactory();
    }

    private static class RecorderEventSupport {
        private List listenerList;

        public void addRecorderListener(RecorderListener listener) {
            if (listenerList == null) {
                listenerList = new ArrayList();
            }
            listenerList.add(listener);
        }


        public void removeRecorderListener(RecorderListener listener) {
            if (listenerList == null) {
                return;
            }
            listenerList.remove(listener);
        }


        public void fireRecorderUpdate() {
            if (listenerList == null) {
                return;
            }

            for (Iterator iter = listenerList.iterator(); iter.hasNext();) {
                RecorderListener listener = (RecorderListener)iter.next();
                listener.recorderUpdate();
            }
        }
    }
}
