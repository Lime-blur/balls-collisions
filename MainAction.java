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
        int generatorWindowWidth = Workspace.getGeneratorWindowSize("width");

        JFrame helpFrame = new JFrame("Instruments");

        helpFrame.setType(Type.UTILITY);
        helpFrame.setAlwaysOnTop(true);
        helpFrame.getContentPane().add(new InstrumentsPanel());
        helpFrame.pack();
        helpFrame.setLocation(
                (frameSize.width - windowWidth) / 2 + windowWidth + 25,
                (frameSize.height - windowHeight) / 2
        );

        JFrame generatorFrame = new JFrame("Generator");

        generatorFrame.setType(Type.UTILITY);
        generatorFrame.setAlwaysOnTop(true);
        generatorFrame.getContentPane().add(new GeneratorPanel());
        generatorFrame.pack();
        generatorFrame.setLocation(
                (frameSize.width - windowWidth) / 2 - generatorWindowWidth - 25,
                (frameSize.height - windowHeight) / 2
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