package ActionScript;

import javax.swing.*;
import java.awt.*;

/**
 * @author Meleshko T.Y.
 * @version 1.0
 * @since JDK8
 */

public class MainAction extends JFrame {

    public static void main(String[] args) {

        Dimension frameSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("2D Graphics");

        int windowWidth = Workspace.getWindowSize("width");
        int windowHeight = Workspace.getWindowSize("height");
        int helpWindowWidth = Workspace.getInstrumentWindowSize("width");

        JFrame helpFrame = new JFrame("Instruments");
        helpFrame.setResizable(false);

        helpFrame.setType(Type.UTILITY);
        helpFrame.setAlwaysOnTop(true);
        helpFrame.getContentPane().add(new InstrumentsPanel());
        helpFrame.pack();
        helpFrame.setLocation(
                (frameSize.width - windowWidth)/2 + windowWidth + 30,
                (frameSize.height - windowHeight)/2 + 5
        );

        JFrame generatorFrame = new JFrame("Generator");
        generatorFrame.setResizable(false);

        generatorFrame.setType(Type.UTILITY);
        generatorFrame.setAlwaysOnTop(true);
        generatorFrame.getContentPane().add(new GeneratorPanel());
        generatorFrame.pack();
        generatorFrame.setLocation(
                (frameSize.width - windowWidth)/2 - helpWindowWidth - 20,
                (frameSize.height - windowHeight)/2 + 5
        );

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new CreateGUI());
        frame.pack();
        frame.setLocation(
                (frameSize.width - windowWidth)/2,
                (frameSize.height - windowHeight)/2
        );

        helpFrame.setVisible(true);
        generatorFrame.setVisible(true);
        frame.setVisible(true);

    }

}