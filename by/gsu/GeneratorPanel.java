package ActionScript.by.gsu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneratorPanel extends JPanel {

    /* Генератор новых тел */

    private static JLabel label1 = new JLabel("Количество тел:", JLabel.RIGHT);

    private static JLabel label2 = new JLabel("X:", JLabel.RIGHT);
    private static JLabel label3 = new JLabel("Y:", JLabel.RIGHT);
    private static JLabel label4 = new JLabel("Ширина тела:", JLabel.RIGHT);
    private static JLabel label5 = new JLabel("Высота тела:", JLabel.RIGHT);
    private static JLabel label6 = new JLabel("Угол:", JLabel.RIGHT);
    private static JLabel label8 = new JLabel("Масса:", JLabel.RIGHT);
    private static JLabel label9 = new JLabel("Скорость:", JLabel.RIGHT);

    private static JTextField edit1 = new JTextField("1");

    private static JTextField edit2 = new JTextField("0");
    private static JTextField edit3 = new JTextField("0");
    private static JTextField edit4 = new JTextField("70");
    private static JTextField edit5 = new JTextField("70");
    private static JTextField edit6 = new JTextField("60");
    private static JTextField edit8 = new JTextField("1");
    private static JTextField edit9 = new JTextField("1.2");

    private static ButtonGroup bgrp = new ButtonGroup();
    private static JRadioButton isCircleSelected = new JRadioButton("Шар");
    private static JRadioButton isSquareSelected = new JRadioButton("Куб");

    private static JCheckBox isRandomCB = new JCheckBox("Случайное расположение");
    private static JCheckBox isDefaultCB = new JCheckBox("По умолчанию");
    private static JCheckBox isDefaultMass = new JCheckBox("Высчитать массу автоматически");

    private static JButton generateButton = new JButton("Сгенерировать");

    /* Удалить тела */

    private static JLabel label7 = new JLabel("ID тела:", JLabel.RIGHT);

    private static JTextField edit7 = new JTextField("1");

    private static JButton deleteButton = new JButton("Удалить");
    private static JButton clearFormButton = new JButton("Очистить форму");

    /* события и слушатели */
    private ActionListener generateListener = new GeneratorPanel.GenerateActionListener();
    private ActionListener deleteListener = new GeneratorPanel.DeleteActionListener();
    private ActionListener clearFormListener = new GeneratorPanel.ClearFormActionListener();
    private ActionListener checkEditsListener = new GeneratorPanel.CheckEditsActionListener();
    private ActionListener autoMassListener = new GeneratorPanel.AutoMassActionListener();

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

            if (!InstrumentsPanel.checkSymbol(edit1, ',') && !InstrumentsPanel.checkSymbol(edit2, ',') && !InstrumentsPanel.checkSymbol(edit3, ',') &&
                    !InstrumentsPanel.checkSymbol(edit4, ',') && !InstrumentsPanel.checkSymbol(edit5, ',') && !InstrumentsPanel.checkSymbol(edit6, ',')
                    && !InstrumentsPanel.checkSymbol(edit8, ',') && !InstrumentsPanel.checkSymbol(edit9, ',')) {
                if (!InstrumentsPanel.checkSymbol(edit1, '.') && !InstrumentsPanel.checkSymbol(edit2, '.') && !InstrumentsPanel.checkSymbol(edit3, '.') &&
                        !InstrumentsPanel.checkSymbol(edit4, '.') && !InstrumentsPanel.checkSymbol(edit5, '.') && !InstrumentsPanel.checkSymbol(edit6, '.')
                        && !InstrumentsPanel.checkSymbol(edit8, '.')) {
                    if (isDefaultCB.isSelected()) {
                        if (arraySize == -1) {
                            int bodiesToGenerate = Integer.parseInt(edit1.getText());
                            if (bodiesToGenerate > 0) {
                                int diameter = Integer.parseInt(edit4.getText());
                                int angle = Integer.parseInt(edit6.getText());
                                double mass = Integer.parseInt(edit8.getText());
                                double speed = Double.parseDouble(edit9.getText());
                                int spaceSizeX = BodiesGenerator.calculateSpaceSize(true, diameter, diameter)[0];
                                int spaceSizeY = BodiesGenerator.calculateSpaceSize(true, diameter, diameter)[1];
                                int spaceSquare = spaceSizeX * spaceSizeY;
                                if (isDefaultMass.isSelected()) {
                                    mass = diameter / 10;
                                }
                                if (bodiesToGenerate <= spaceSquare) {
                                    BodiesGenerator.fillArrayAutomatically(bodiesToGenerate, "circle", diameter, diameter, angle, mass, speed);
                                    if (isRandomCB.isSelected()) {
                                        BodiesGenerator.fillSpace(true);
                                    } else {
                                        BodiesGenerator.fillSpace(false);
                                    }
                                    InstrumentsPanel.updateInstrumentsEdits(true);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Сгенерировать можно максимум " + spaceSquare + " тел(а)!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Сгенерировать можно минимум 1 тело!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Форма должна быть очищена!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        String type = "circle";
                        int x = Integer.parseInt(edit2.getText());
                        int y = Integer.parseInt(edit3.getText());
                        int diameter = Integer.parseInt(edit4.getText());
                        int angle = Integer.parseInt(edit6.getText());
                        double mass = Integer.parseInt(edit8.getText());
                        double speed = Double.parseDouble(edit9.getText());

                        int criticalX = Workspace.getFieldWalls("right");
                        int criticalY = Workspace.getFieldWalls("bottom");
                        boolean isIntersect = false;

                        if (isDefaultMass.isSelected()) {
                            mass = diameter / 10;
                        }
                        if (isSquareSelected.isSelected()) {
                            type = "square";
                        }

                        for (int i = 0; i < arraySize; i++) {
                            Bodies body = BodiesGenerator.getBodyByID(i);
                            double centerX1 = Animations.getBodyCenter(x, diameter);
                            double centerY1 = Animations.getBodyCenter(y, diameter);
                            double centerX2 = Animations.getBodyCenter(body.x, body.width);
                            double centerY2 = Animations.getBodyCenter(body.y, body.height);

                            if (type.equals("circle") && body.type.equals("circle")) {
                                double sumRadius = diameter / 2 + body.width / 2;
                                if (VectorMath.getDistance(centerX1, centerY1, centerX2, centerY2) <= sumRadius) {
                                    isIntersect = true;
                                }
                            }
                        }

                        if (!isIntersect) {
                            if (x + diameter <= criticalX || y + diameter <= criticalY) {
                                if (x >= 0 && y >= 0 && x <= criticalX - diameter && y <= criticalY - diameter) {
                                    BodiesGenerator.fillArrayManually(type, x, y, diameter, diameter, angle, mass, speed);
                                    InstrumentsPanel.updateInstrumentsEdits(true);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Неверно заданы координаты!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Ширина тела превышает размеры формы!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Тело пересекается с другими телами!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ошибка чисел! Требуются целые числа во всех полях кроме скорости!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ошибка знаков! Запятые запрещены!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class DeleteActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int arraySize = BodiesGenerator.getBodiesArraySize();

            if (!InstrumentsPanel.checkSymbol(edit7, ',') && !InstrumentsPanel.checkSymbol(edit7, '.')) {
                if (arraySize != -1) {
                    int bodyToDelete = Integer.parseInt(edit7.getText()) - 1;
                    if (Integer.parseInt(edit7.getText()) > 0) {
                        if (BodiesGenerator.getBodyByID(bodyToDelete) != null) {
                            BodiesGenerator.deleteBody(bodyToDelete);
                            BodiesGenerator.updateBodiesID();
                            InstrumentsPanel.updateInstrumentsEdits(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Неверные данные!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "ID должен быть больше нуля!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "На форме нет ни одного тела!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ошибка знаков! Число должно быть целым и не содержать запятых!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class ClearFormActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int arraySize = BodiesGenerator.getBodiesArraySize();

            if (arraySize != -1) {
                for (int i = 0; i < arraySize; i++) {
                    BodiesGenerator.clearArray();
                    InstrumentsPanel.updateInstrumentsEdits(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "На форме нет ни одного тела!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class CheckEditsActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isDefaultCB.isSelected()) {
                edit1.setEnabled(true);
                edit2.setEnabled(false);
                edit3.setEnabled(false);
                isRandomCB.setEnabled(true);
            } else {
                edit1.setEnabled(false);
                edit2.setEnabled(true);
                edit3.setEnabled(true);
                isRandomCB.setEnabled(false);
            }
        }
    }

    public class AutoMassActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isDefaultMass.isSelected()) {
                edit8.setEnabled(false);
            } else {
                edit8.setEnabled(true);
            }
        }
    }

    private void addPanelComponents() {
        generateButton.setFocusPainted(false);
        deleteButton.setFocusPainted(false);
        isCircleSelected.setFocusPainted(false);
        isSquareSelected.setFocusPainted(false);
        isDefaultCB.setFocusPainted(false);
        isDefaultMass.setFocusPainted(false);
        clearFormButton.setFocusPainted(false);

        setLayout(null);

        int wS = Workspace.getGeneratorWindowSize("width");

        /* Создание */

        label1.setBounds(10, 10, wS - 100, 25);
        add(label1);
        edit1.setBounds(wS - 80, 10, 70, 25);
        add(edit1);

        label2.setBounds(10, 45, 15, 25);
        add(label2);
        edit2.setBounds(35, 45, 50, 25);
        edit2.setEnabled(false);
        add(edit2);

        label3.setBounds(95, 45, 15, 25);
        add(label3);
        edit3.setBounds(120, 45, 50, 25);
        edit3.setEnabled(false);
        add(edit3);

        label4.setBounds(10, 80, wS - 100, 25);
        add(label4);
        edit4.setBounds(wS - 80, 80, 70, 25);
        add(edit4);

        label5.setBounds(10, 115, wS - 100, 25);
        add(label5);
        edit5.setBounds(wS - 80, 115, 70, 25);
        edit5.setEnabled(false);
        add(edit5);

        label6.setBounds(10, 150, wS - 100, 25);
        add(label6);
        edit6.setBounds(wS - 80, 150, 70, 25);
        add(edit6);

        label8.setBounds(10, 185, wS - 100, 25);
        add(label8);
        edit8.setBounds(wS - 80, 185, 70, 25);
        edit8.setEnabled(false);
        add(edit8);

        label9.setBounds(10, 220, wS - 100, 25);
        add(label9);
        edit9.setBounds(wS - 80, 220, 70, 25);
        add(edit9);

        isDefaultMass.setBounds(10, 255, Workspace.getGeneratorWindowSize("width") - 20, 25);
        isDefaultMass.setSelected(true);
        isDefaultMass.addActionListener(autoMassListener);
        add(isDefaultMass);

        bgrp.add(isCircleSelected);
        bgrp.add(isSquareSelected);
        isCircleSelected.setBounds(10, 290, 80, 25);
        isCircleSelected.setSelected(true);
        add(isCircleSelected);
        isSquareSelected.setBounds(90, 290, 80, 25);
        isSquareSelected.setEnabled(false);
        add(isSquareSelected);

        isRandomCB.setBounds(10, 325, Workspace.getGeneratorWindowSize("width") - 20, 25);
        isRandomCB.setSelected(true);
        add(isRandomCB);

        isDefaultCB.setBounds(10, 360, Workspace.getGeneratorWindowSize("width") - 20, 25);
        isDefaultCB.setSelected(true);
        isDefaultCB.addActionListener(checkEditsListener);
        add(isDefaultCB);

        generateButton.setBounds(10, 395, Workspace.getGeneratorWindowSize("width") - 20, 25);
        generateButton.addActionListener(generateListener);
        add(generateButton);

        /* Удаление */

        label7.setBounds(10, 465, wS - 100, 25);
        add(label7);
        edit7.setBounds(wS - 80, 465, 70, 25);
        add(edit7);

        deleteButton.setBounds(10, 500, Workspace.getGeneratorWindowSize("width") - 20, 25);
        add(deleteButton);
        deleteButton.addActionListener(deleteListener);

        clearFormButton.setBounds(10, 535, Workspace.getGeneratorWindowSize("width") - 20, 25);
        add(clearFormButton);
        clearFormButton.addActionListener(clearFormListener);
    }
}
