package ActionScript;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneratorPanel extends JPanel {

    private static JLabel label1 = new JLabel("Количество тел:");
    private static JLabel label2 = new JLabel("ID тела:");

    private static JTextField edit1 = new JTextField("1");
    private static JTextField edit2 = new JTextField("1");

    private static JButton generateButton = new JButton("Сгенерировать");
    private static JButton deleteButton = new JButton("Удалить");

    /* события и слушатели */
    private ActionListener generateListener = new GeneratorPanel.GenerateActionListener();
    private ActionListener deleteListener = new GeneratorPanel.DeleteActionListener();

    /* конструктор */
    public GeneratorPanel() {
        setPreferredSize(new Dimension(
                Workspace.getGeneratorWindowSize("width"),
                Workspace.getGeneratorWindowSize("height")
        ));
        addPanelComponents();
    }

    public class GenerateActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int arraySize = BodiesGenerator.getBodiesArraySize();

            if (arraySize == -1) {
                int bodiesToGenerate = Integer.parseInt(edit1.getText());
                if (bodiesToGenerate > 0) {
                    int radius = 70;
                    int spaceSizeX = BodiesGenerator.calculateSpaceSize(true, radius, radius)[0];
                    int spaceSizeY = BodiesGenerator.calculateSpaceSize(true, radius, radius)[1];
                    int spaceSquare = spaceSizeX * spaceSizeY;
                    if (bodiesToGenerate <= spaceSquare) {
                        BodiesGenerator.fillArrayAutomatically(bodiesToGenerate, "circle", radius, radius, 60);
                        BodiesGenerator.fillSpace(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Сгенерировать можно максимум " + spaceSquare + " тел!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Сгенерировать можно минимум 1 шар!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Форма должна быть очищена!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class DeleteActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int arraySize = BodiesGenerator.getBodiesArraySize();

            if (arraySize != -1) {
                int bodyToDelete = Integer.parseInt(edit2.getText()) - 1;
                if (Integer.parseInt(edit2.getText()) > 0) {
                    if (BodiesGenerator.getBodyByID(bodyToDelete) != null) {
                        BodiesGenerator.deleteBody(bodyToDelete);
                        BodiesGenerator.updateBodiesID();
                    } else {
                        JOptionPane.showMessageDialog(null, "Неверные данные!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ID должен быть больше нуля!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "На форме нет ни одного тела!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addPanelComponents() {
        generateButton.setFocusPainted(false);
        deleteButton.setFocusPainted(false);

        setLayout(null);

        generateButton.setBounds(10, 10, Workspace.getGeneratorWindowSize("width") - 20, 30);
        generateButton.addActionListener(generateListener);
        add(generateButton);

        deleteButton.addActionListener(deleteListener);
    }
}
