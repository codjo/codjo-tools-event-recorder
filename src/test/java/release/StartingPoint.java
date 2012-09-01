/*
 * codjo.net
 *
 * Common Apache License 2.0
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
