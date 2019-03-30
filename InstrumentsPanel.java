package ActionScript;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstrumentsPanel extends JPanel {

    private static JLabel label1 = new JLabel("ID тел(а):");
    private static JLabel label2 = new JLabel("Скорость тела:");
    private static JLabel label3 = new JLabel("Ширина тела:");
    private static JLabel label4 = new JLabel("Высота тела:");
    private static JLabel label5 = new JLabel("Угол:");

    private static JTextField edit1 = new JTextField("1");
    private static JTextField edit2 = new JTextField("1.2");
    private static JTextField edit3 = new JTextField("70");
    private static JTextField edit4 = new JTextField("70");
    private static JTextField edit5 = new JTextField("60");

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

    private static boolean checkSymbol(JTextField edit, char smbl) {
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
        }
    }

    public class AcceptActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int arraySize = BodiesGenerator.getBodiesArraySize();

            if (arraySize != -1) {
                if (!checkSymbol(edit1, ',') && !checkSymbol(edit2, ',') && !checkSymbol(edit3, ',') && !checkSymbol(edit4, ',') && !checkSymbol(edit5, ',')) {
                    if (!checkSymbol(edit1, '.') && !checkSymbol(edit3, '.') && !checkSymbol(edit4, '.') && !checkSymbol(edit5, ',')) {
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
                                        body.setBodyAngle(body.getBodyAngle(), Double.parseDouble(edit2.getText()));
                                        body.width = Integer.parseInt(edit3.getText());
                                        body.height = Integer.parseInt(edit3.getText());
                                        body.setBodyAngle(Integer.parseInt(edit5.getText()));
                                    }
                                } else {
                                    int startResult = Integer.parseInt(stringToParse.substring(0, stringToParse.indexOf('-')));
                                    int endResult = Integer.parseInt(stringToParse.substring(stringToParse.indexOf('-') + 1, stringToParse.length()));
                                    if (startResult > 0 && endResult < arraySize + 1 && startResult < endResult) {
                                        for (int i = startResult - 1; i < endResult; i++) {
                                            Bodies body = BodiesGenerator.getBodyByID(i);
                                            body.setBodyAngle(body.getBodyAngle(), Double.parseDouble(edit2.getText()));
                                            body.width = Integer.parseInt(edit3.getText());
                                            body.height = Integer.parseInt(edit3.getText());
                                            body.setBodyAngle(Integer.parseInt(edit5.getText()));
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
        JTextField editsArray[] = {edit1, edit2, edit3, edit4, edit5};
        JLabel labelsArray[] = {label1, label2, label3, label4, label5};
        int j = 0, k = 0;

        acceptButton.setFocusPainted(false);

        setBorder(new EmptyBorder(5, 10, 10, 10));
        setLayout(new GridLayout(16, 1, 0, 6));

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                add(labelsArray[j]);
                j++;
            } else {
                add(editsArray[k]);
                k++;
            }
        }
        edit4.setEnabled(false);

        acceptButton.addActionListener(acceptListener);
        add(acceptButton);
    }
}
