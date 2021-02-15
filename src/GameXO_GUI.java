import javax.swing.*;
import java.awt.*;

public class GameXO_GUI extends JFrame{

    private JButton [][] map;

    private final ImageIcon DOT_X = new ImageIcon("src/res/X.jpg");
    private final ImageIcon DOT_O = new ImageIcon("src/res/O.jpg");
    private final ImageIcon EMPTY = new ImageIcon("src/res/Empty.jpg");

    public String getDOT_X() {
        return DOT_X.toString();
    }

    public String getDOT_O() {
        return DOT_O.toString();
    }

    public String getEMPTY() {
        return EMPTY.toString();
    }

    private JMenu createFileMenu() {
        JMenu file = new JMenu("Файл");
        JMenuItem open = new JMenuItem("Новая игра");
        JMenuItem exit = new JMenuItem("Выход");
        open.addActionListener(actionEvent -> {
            new GameXO_GUI("Крестики - нолики");
            dispose();
        });
        exit.addActionListener(actionEvent -> {
            dispose();
        });
        file.add(open);
        file.addSeparator();
        file.add(exit);
        return file;
    }

    private JPanel getMap() {
        map = new JButton[3][3];
        JPanel panel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                map[i][j] = new JButton();
                map[i][j].setIcon(EMPTY);
                JButton tmp = map[i][j];
                tmp.addActionListener(actionEvent -> {
                    tmp.setEnabled(false);
                    tmp.setDisabledIcon(DOT_X);
                    try {
                        turnAi(map, DOT_O, EMPTY, DOT_X);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                });
                panel.add(map[i][j]);
            }
        }
        return panel;
    }

    public GameXO_GUI(String title) throws HeadlessException {
        super(title);
        JMenuBar bar = new JMenuBar();
        bar.add(createFileMenu());
        setJMenuBar(bar);
        add(getMap());
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new GameXO_GUI("Крестики - нолики");

    }

    public static JButton[][] turnAi(JButton[][] map, ImageIcon DOT_O, ImageIcon EMPTY, ImageIcon DOT_X) throws InterruptedException {
        System.out.println();
        Thread.sleep(1000);
        System.out.println("Ход компьютера");
        Thread.sleep(2000);

        boolean flag = false;

        label:
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (emptyField(map, i, j, EMPTY)) {
                    map[i][j].setIcon(DOT_O);
                    if (victoryCondition(map, DOT_O)) {
                        flag = true;
                        break label;
                    } else map[i][j].setIcon(EMPTY);
                }
            }
        }

        if (!flag) {
            label:
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map.length; j++) {
                    if (emptyField(map, i, j, EMPTY)) {
                        map[i][j].setIcon(DOT_X);
                        if (victoryCondition(map, DOT_X)) {
                            map[i][j].setIcon(DOT_O);
                            flag = true;
                            break label;
                        } else map[i][j].setIcon(EMPTY);
                    }
                }
            }
        }

        if (!flag) {
            boolean flag2 = false;
            while (!flag2) {
                int x = (int) (Math.random() * 3);
                int y = (int) (Math.random() * 3);
                if (emptyField(map, x, y, EMPTY)) {
                    map[x][y].setIcon(DOT_O);
                    flag2 = true;
                }
            }
        }

        return map;
    }

    public static boolean emptyField(JButton[][] map, int x, int y, ImageIcon EMPTY) {
        return map[x][y].getIcon().toString().equals(EMPTY.toString());
    }

    public static boolean victoryCondition(JButton[][] map, ImageIcon name) {
        for (int i = 0; i < map.length; i++) {
            int count_x = 0;
            for (int j = 0; j < map.length; j++) {
                if (map[i][j].getIcon().toString().equals(name.toString())) count_x++;
                if (count_x == 3) {
                    return true;
                }
            }
        }

        int loopCounter = 0;
        while (loopCounter < map.length) {
            int count_y = 0;
            for (int i = 0; i < map.length; i++) {
                for (int j = loopCounter; j <= loopCounter; j++) {
                    if (map[i][j].getIcon().toString().equals(name.toString())) count_y++;
                    if (count_y == 3) {
                        return true;
                    }
                }
            }
            loopCounter++;
        }

        int count_a = 0, count_b = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (i - j == 0 && map[i][j].getIcon().toString().equals(name.toString())) count_a++;
                if (i + j == map.length && map[i][j].getIcon().toString().equals(name.toString())) count_b++;
            }
        }
        if (count_a == 3 || count_b == 3) return true;


        return false;


    }
}
