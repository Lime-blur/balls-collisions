package ActionScript.by.gsu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstrumentsPanel extends JPanel {

    private static JLabel label1 = new JLabel("ID тел(а):", JLabel.RIGHT);
    private static JLabel label2 = new JLabel("Скорость:", JLabel.RIGHT);
    private static JLabel label3 = new JLabel("Ширина тела:", JLabel.RIGHT);
    private static JLabel label4 = new JLabel("Высота тела:", JLabel.RIGHT);
    private static JLabel label5 = new JLabel("Угол:", JLabel.RIGHT);
    private static JLabel label6 = new JLabel("Масса:", JLabel.RIGHT);

    private static JTextField edit1 = new JTextField("1");
    private static JTextField edit2 = new JTextField("1.2");
    private static JTextField edit3 = new JTextField("70");
    private static JTextField edit4 = new JTextField("70");
    private static JTextField edit5 = new JTextField("60");
    private static JTextField edit6 = new JTextField("1");

    private static JButton acceptButton = new JButton("Применить");

    /* события и слушатели */
    private ActionListener acceptListener = new InstrumentsPanel.AcceptActionListener();

    /* конструктор */
    public InstrumentsPanel() {
        setPreferredSize(new Dimension(
                Workspace.getInstrumentWindowSize("width"),
                Workspace.getInstrumentWindowSize("height")
        ));
        addPanelComponents();
    }

    public static boolean checkSymbol(JTextField edit, char smbl) {
        if (edit.getText().indexOf(smbl) == -1) {
            return false;
        } else {
            return true;
        }
    }

    public static void updateInstrumentsEdits(boolean updateRange) {
        int arraySize = BodiesGenerator.getBodiesArraySize();
        if (updateRange) {
            if (arraySize != -1 && arraySize != 1) {
                edit1.setText("1-" + arraySize);
            } else {
                edit1.setText("1");
            }
        }
    }

    public static void updateInstrumentsEdits() {
        int arraySize = BodiesGenerator.getBodiesArraySize();
        if (arraySize != -1) {
            Bodies body = BodiesGenerator.getBodyByID(1);
            edit1.setText("1-" + arraySize);
            edit2.setText("" + (int) body.bodySpeed);
            edit3.setText("" + (int) body.width);
            edit4.setText("" + (int) body.height);
            edit5.setText("" + (int) body.angle);
            edit6.setText("" + (int) body.mass);
        }
    }

    public static void updateInstrumentsEdits(Bodies body) {
        int arraySize = BodiesGenerator.getBodiesArraySize();
        if (arraySize != -1) {
            edit1.setText("1-" + arraySize);
            edit2.setText("" + (int) body.bodySpeed);
            edit3.setText("" + (int) body.width);
            edit4.setText("" + (int) body.height);
            edit5.setText("" + (int) body.angle);
            edit5.setText("" + (int) body.mass);
        }
    }

    public class AcceptActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int arraySize = BodiesGenerator.getBodiesArraySize();

            if (arraySize != -1) {
                if (!checkSymbol(edit1, ',') && !checkSymbol(edit2, ',') && !checkSymbol(edit3, ',') && !checkSymbol(edit4, ',') && !checkSymbol(edit5, ',') && !checkSymbol(edit6, ',')) {
                    if (!checkSymbol(edit1, '.') && !checkSymbol(edit3, '.') && !checkSymbol(edit4, '.') && !checkSymbol(edit5, '.') && !checkSymbol(edit6, '.')) {
                        if (Double.parseDouble(edit2.getText()) >= 0) {
                            int spaceWidth = Workspace.getFieldWalls("right") - Workspace.getFieldWalls("left");
                            int spaceHeight = Workspace.getFieldWalls("bottom") - Workspace.getFieldWalls("top");
                            if (Integer.parseInt(edit3.getText()) < spaceWidth && Integer.parseInt(edit4.getText()) < spaceHeight) {
                                String stringToParse = edit1.getText();
                                if (stringToParse.indexOf('-') == -1) {
                                    int stringResult = Integer.parseInt(stringToParse);
                                    if (stringResult < 1 || stringResult > arraySize) {
                                        JOptionPane.showMessageDialog(null, "Максимальное количество тел: " + arraySize, "Ошибка", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        Bodies body = BodiesGenerator.getBodyByID(stringResult - 1);
                                        body.setBodyAngle(body.angle, Double.parseDouble(edit2.getText()));
                                        body.width = Integer.parseInt(edit3.getText());
                                        body.height = Integer.parseInt(edit3.getText());
                                        body.setBodyAngle(Integer.parseInt(edit5.getText()));
                                        body.mass = Integer.parseInt(edit6.getText());
                                    }
                                } else {
                                    int startResult = Integer.parseInt(stringToParse.substring(0, stringToParse.indexOf('-')));
                                    int endResult = Integer.parseInt(stringToParse.substring(stringToParse.indexOf('-') + 1, stringToParse.length()));
                                    if (startResult > 0 && endResult < arraySize + 1 && startResult < endResult) {
                                        for (int i = startResult - 1; i < endResult; i++) {
                                            Bodies body = BodiesGenerator.getBodyByID(i);
                                            body.setBodyAngle(body.angle, Double.parseDouble(edit2.getText()));
                                            body.width = Integer.parseInt(edit3.getText());
                                            body.height = Integer.parseInt(edit3.getText());
                                            body.setBodyAngle(Integer.parseInt(edit5.getText()));
                                            body.mass = Integer.parseInt(edit6.getText());
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Ошибка диапозона! Максимальное количество тел: " + arraySize, "Ошибка", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Ошибка измерений! Максимальная ширина: " + spaceWidth + ", максимальная высота: " + spaceHeight, "Ошибка", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Ошибка скорости! Минимальное значение: 0", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Ошибка чисел! Требуются целые числа во всех полях кроме скорости!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ошибка знаков! Запятые запрещены!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void addPanelComponents() {
        acceptButton.setFocusPainted(false);

        setLayout(null);

        int wS = Workspace.getInstrumentWindowSize("width");

        /* Создание */

        label1.setBounds(10, 10, wS - 100, 25);
        add(label1);
        edit1.setBounds(wS - 80, 10, 70, 25);
        add(edit1);

        label2.setBounds(10, 45, wS - 100, 25);
        add(label2);
        edit2.setBounds(wS - 80, 45, 70, 25);
        add(edit2);

        label3.setBounds(10, 80, wS - 100, 25);
        add(label3);
        edit3.setBounds(wS - 80, 80, 70, 25);
        add(edit3);

        label4.setBounds(10, 115, wS - 100, 25);
        add(label4);
        edit4.setBounds(wS - 80, 115, 70, 25);
        add(edit4);
        edit4.setEnabled(false);

        label5.setBounds(10, 150, wS - 100, 25);
        add(label5);
        edit5.setBounds(wS - 80, 150, 70, 25);
        add(edit5);

        label6.setBounds(10, 185, wS - 100, 25);
        add(label6);
        edit6.setBounds(wS - 80, 185, 70, 25);
        add(edit6);

        acceptButton.setBounds(10, 220, Workspace.getGeneratorWindowSize("width") - 20, 25);
        acceptButton.addActionListener(acceptListener);
        add(acceptButton);
    }
}
