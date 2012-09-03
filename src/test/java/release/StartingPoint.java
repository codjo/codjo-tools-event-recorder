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
package release;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import recorder.gui.RecorderLogic;
/**
 * Point de d»part pour les tests manuelles.
 */
public final class StartingPoint {
    private StartingPoint() {
    }


    public static void main(String[] args) {
        mainTest(args);

        new RecorderLogic().display();
        SpikeListener spike = new SpikeListener();
        Toolkit.getDefaultToolkit().addAWTEventListener(spike, Long.MAX_VALUE);
    }


    public static void mainTest(String[] args) {
        JFrame frame = new JFrame("IHM simple");

        frame.setContentPane(new SimpleGui());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        frame.setJMenuBar(newBar());
        frame.pack();
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }


    private static JMenuBar newBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem("Ouvrir"));

        JMenu optionMenu = new JMenu("Options");
        optionMenu.add(new JMenuItem("Configuration"));
        fileMenu.add(optionMenu);

        menuBar.add(fileMenu);
        return menuBar;
    }
}
