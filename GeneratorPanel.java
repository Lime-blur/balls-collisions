package ActionScript;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneratorPanel extends JPanel {

    private static JLabel label1 = new JLabel("Количество тел:");

    private static JTextField edit1 = new JTextField("1");

    private static JButton generateButton = new JButton("Сгенерировать");
    private static JButton deleteButton = new JButton("Удалить");

    /* события и слушатели */
    private ActionListener generateListener = new GeneratorPanel.GenerateActionListener();
    private ActionListener deleteListener = new GeneratorPanel.DeleteActionListener();

    /* конструктор */
    public GeneratorPanel() {
        setPreferredSize(new Dimension(
                Workspace.getInstrumentWindowSize("width"),
                Workspace.getInstrumentWindowSize("height")
        ));
        addPanelComponents();
    }

    public class GenerateActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int arraySize = BodiesGenerator.getBodiesArraySize();

            if (arraySize == -1) {
                if (Integer.parseInt(edit1.getText()) > 0) {
                    BodiesGenerator.fillArrayAutomatically(Integer.parseInt(edit1.getText()), "circle", 70, 70, 60);
                    BodiesGenerator.fillSpace(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Сгенерировать можно минимум 1 шар!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public class DeleteActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int arraySize = BodiesGenerator.getBodiesArraySize();

            if (arraySize != -1) {
                if (Integer.parseInt(edit1.getText()) > 0) {
                    BodiesGenerator.deleteBody(Integer.parseInt(edit1.getText())-1);
                } else {
                    JOptionPane.showMessageDialog(null, "Удалить можно минимум 1 шар!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void addPanelComponents() {
        JTextField editsArray[] = {edit1};
        JLabel labelsArray[] = {label1};
        int j = 0, k = 0;

        generateButton.setFocusPainted(false);
        deleteButton.setFocusPainted(false);

        setBorder(new EmptyBorder(5, 10, 10, 10));
        setLayout(new GridLayout(16, 1, 0, 6));

        for (int i = 0; i < 2; i++) {
            if (i % 2 == 0) {
                add(labelsArray[j]);
                j++;
            } else {
                add(editsArray[k]);
                k++;
            }
        }

        generateButton.addActionListener(generateListener);
        deleteButton.addActionListener(deleteListener);
        add(generateButton);
        add(deleteButton);
    }
}
