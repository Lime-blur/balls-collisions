package ActionScript.by.gsu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * наследник класса JPanel.
 * отображает всё содержимое текущего окна.
 */

public class CreateGUI extends JPanel{

    /* интерфейс пользователя */
    private JButton button1 = new JButton("Запустить");
    private JButton button2 = new JButton("Остановить");
    private JButton button3 = new JButton("Сбросить");
    private JButton button4 = new JButton("Обновить форму");
    private JCheckBox checkBox1 = new JCheckBox("Показывать ID тел", true);
    private JCheckBox checkBox2 = new JCheckBox("Менять цвет тел при соударении", false);

    /* события и слушатели */
    private ActionListener runListener = new RunActionListener();
    private ActionListener stopListener = new StopActionListener();
    private ActionListener resetListener = new ResetActionListener();
    private ActionListener reloadListener = new ReloadActionListener();

    /* конструктор */
    public CreateGUI() {
        setPreferredSize(new Dimension(Workspace.getWindowSize("width"), Workspace.getWindowSize("height")));
        setBackground(Color.GRAY);
        createButtons();
    }

    /**
     * перересовывает содержимое формы.
     */

    private void repaintAll() { repaint(); }

    /**
     * запуск физического процесса.
     * класс создаёт объект класса {@code RenderActionListener} и реализует метод run.
     */

    public class RunActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { new RenderActionListener().run(); }
    }

    /**
     * остановка физического процесса.
     * класс создаёт объект класса {@code RenderActionListener} и реализует метод stop.
     */

    public class StopActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { new RenderActionListener().stop(); }
    }

    /**
     * обновление формы.
     */

    public class ReloadActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { new RenderActionListener().reload(); }
    }

    /**
     * сброс физического процесса.
     * класс создаёт объект класса {@code RenderActionListener} и реализует метод reset.
     */

    public class ResetActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { new RenderActionListener().reset(); }
    }

    /**
     * класс реализующий анимацию.
     */

    public class RenderActionListener implements ActionListener {

        private Timer timer;

        /**
         * создание таймера.
         */

        private RenderActionListener() { timer = new Timer(Animations.getBodyDelayValue(), this); }

        /**
         * перезапуск процесса.
         */

        private void reload()
        {
            if (!Animations.isBodyRunning()) {
                timer.start();
                Animations.setBodyReloading(true);
            }
        }

        /**
         * запуск процесса.
         */

        private void run() {
            int arraySize = BodiesGenerator.getBodiesArraySize();

            if (arraySize != -1) {
                if (!Animations.isBodyRunning()) {
                    Animations.setBodyRunning(true);
                    timer.start();
                    for (int i = 0; i < arraySize; i++) {
                        Bodies body = BodiesGenerator.getBodyByID(i);
                        body.setBodyAngle(body.angle, body.bodySpeed);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "На форме не создано ни одного объекта!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }

        /**
         * остановка процесса.
         */

        private void stop() {
            int arraySize = BodiesGenerator.getBodiesArraySize();

            if (arraySize != -1) {
                if (Animations.isBodyRunning()) {
                    Animations.setBodyRunning(false);
                }
            } else {
                JOptionPane.showMessageDialog(null, "На форме не создано ни одного объекта!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }

        /**
         * сброс процесса.
         */

        private void reset() {
            int arraySize = BodiesGenerator.getBodiesArraySize();

            if (arraySize != -1) {
                if (Animations.isBodyRunning()) {
                    for (int i = 0; i < arraySize; i++) {
                        Bodies body = BodiesGenerator.getBodyByID(i);
                        body.y = body.i_y;
                        body.x = body.i_x;
                    }
                    Animations.setBodyRunning(false);
                } else {
                    timer.start();
                    Animations.setBodyReloading(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "На форме не создано ни одного объекта!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }

        /**
         * главный метод, реализующий анимацию.
         */

        @Override
        public void actionPerformed(ActionEvent e) {
            int arraySize = BodiesGenerator.getBodiesArraySize();

            if (arraySize != -1) {
                if (Animations.isBodyRunning()) {
                    for (int b = 0; b < arraySize; b++) {
                        Bodies body = BodiesGenerator.getBodyByID(b);
                        Animations.checkBodyPosition(body);
                        body.x += body.vectorX;
                        body.y += body.vectorY;
                    }
                    for (int i = 0; i < arraySize; i++) {
                        for (int j = i + 1; j < arraySize; j++) {
                            Bodies body1 = BodiesGenerator.getBodyByID(i);
                            Bodies body2 = BodiesGenerator.getBodyByID(j);
                            Animations.checkBodiesPositions(body1, body2);
                        }
                    }
                } else {
                    if (Animations.isBodyReloading()) {
                        for (int i = 0; i < arraySize; i++) {
                            Bodies body = BodiesGenerator.getBodyByID(i);
                            body.y = body.i_y;
                            body.x = body.i_x;
                        }
                        Animations.setBodyReloading(false);
                    }
                    timer.stop();
                }
            }
            repaintAll();
        }
    }

    /**
     * создание кнопок.
     * метод задаёт кнопкам параметры, расположение и добавляет их на форму.
     */

    public void createButtons() {
        button1.setFocusPainted(false);
        button2.setFocusPainted(false);
        button3.setFocusPainted(false);
        button4.setFocusPainted(false);
        checkBox1.setFocusPainted(false);
        checkBox2.setFocusPainted(false);

        setBorder(new EmptyBorder(10, 10, 10, 10));

        setLayout(null);
        button1.setBounds(Workspace.getWindowSize("width") - 143, 10, 135, 30);
        button1.setBackground(Color.GRAY);
        button1.addActionListener(runListener);
        add(button1);

        button2.setBounds(Workspace.getWindowSize("width") - 143, 45, 135, 30);
        button2.setBackground(Color.GRAY);
        button2.addActionListener(stopListener);
        add(button2);

        button3.setBounds(Workspace.getWindowSize("width") - 143, 80, 135, 30);
        button3.setBackground(Color.GRAY);
        button3.addActionListener(resetListener);
        add(button3);

        button4.setBounds(Workspace.getWindowSize("width") - 143, Workspace.getWindowSize("height") - 40, 135, 30);
        button4.setBackground(Color.GRAY);
        button4.addActionListener(reloadListener);
        add(button4);

        checkBox1.setBounds(5, 460, 135, 30);
        checkBox1.setBackground(Color.DARK_GRAY);
        checkBox1.setForeground(Color.WHITE);
        add(checkBox1);

        checkBox2.setBounds(160, 460, 225, 30);
        checkBox2.setBackground(Color.DARK_GRAY);
        checkBox2.setForeground(Color.WHITE);
        add(checkBox2);
    }

    /**
     * переопределение метода {@code paintComponent}.
     * метод, отрисовывающий графические элементы.
     */

    double totalImpulse = 0, totalEnergy = 0;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        /* поле */
        g.setColor(Color.WHITE);
        g.fillRect(
                0,
                0,
                Workspace.getWindowSize("width") - 150,
                Workspace.getWindowSize("height") - Workspace.getFloorSize("height")
        );

        /* пол */
        g.setColor(Color.DARK_GRAY);
        g.fillRect(
                Workspace.getFloorPosition("x"),
                Workspace.getFloorPosition("y"),
                Workspace.getFloorSize("width") - 150,
                Workspace.getFloorSize("height")
        );

        /* перегородка */
        g.setColor(Color.RED);
        g.drawLine(
                Workspace.getWindowSize("width") - 150,
                0,
                Workspace.getWindowSize("width") - 150,
                Workspace.getWindowSize("height")
        );

        /* тела */

        int arraySize = BodiesGenerator.getBodiesArraySize();

        if (arraySize != -1) {
            for (int i = 0; i < arraySize; i++) {
                Bodies body = BodiesGenerator.getBodyByID(i);
                if (body.type.equals("square")) {
                    g.setColor(Color.RED);
                    g.drawRect(
                            (int) body.x,
                            (int) body.y,
                            (int) body.width,
                            (int) body.height
                    );
                }
                if (body.type.equals("circle")) {
                    if (checkBox2.isSelected()) {
                        if (body.isCollide) {
                            g.setColor(Color.BLUE);
                        } else {
                            g.setColor(Color.RED);
                        }
                    } else {
                        g.setColor(Color.RED);
                    }
                    g.fillOval(
                            (int) body.x,
                            (int) body.y,
                            (int) body.width,
                            (int) body.height
                    );
                    if (checkBox1.isSelected()) {
                        g.setColor(Color.WHITE);
                        g.drawString(
                                String.valueOf(body.id + 1),
                                (int) Animations.getBodyCenter(body.x, body.width),
                                (int) Animations.getBodyCenter(body.y, body.height)
                        );
                    }
                }
                totalImpulse = totalImpulse + Physics.getBodyImpulse(body);
                totalEnergy = totalEnergy + Physics.getBodyKineticEnergy(body);
            }
        }

        g.setColor(Color.ORANGE);
        g.setFont(new Font("default", Font.BOLD, 12));
        g.drawString("Импульс системы: " + String.valueOf(Math.rint(100.0 * totalImpulse) / 100.0), 400, 470);
        g.drawString("Кинетич. энергия системы: " + String.valueOf(Math.rint(100.0 * totalEnergy) / 100.0), 400, 490);

        totalImpulse = 0; totalEnergy = 0;
    }

}
