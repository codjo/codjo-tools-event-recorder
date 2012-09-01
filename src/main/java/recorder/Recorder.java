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
package recorder;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.util.ArrayList;
import java.util.List;
import recorder.component.GuiComponentFactory;
import recorder.event.EventRecognizerManager;
import recorder.event.GuiEvent;
import recorder.event.GuiEventList;
import recorder.gesture.GestureManager;
import recorder.result.Statement;
import recorder.result.StatementList;
/**
 * Record user interaction.
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
        private List<RecorderListener> listenerList;


        public void addRecorderListener(RecorderListener listener) {
            if (listenerList == null) {
                listenerList = new ArrayList<RecorderListener>();
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

            for (RecorderListener listener : listenerList) {
                listener.recorderUpdate();
            }
        }
    }
}
