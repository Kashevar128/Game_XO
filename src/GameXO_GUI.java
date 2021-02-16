import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameXO_GUI extends JFrame {
    final private static String empty = "_";
    final private static String dot_x = "X";
    final private static String dot_0 = "O";

    private JButton[][] map;
    private String[][] mapStr;
    private final ImageIcon DOT_X = new ImageIcon("src/res/X.jpg");
    private final ImageIcon DOT_O = new ImageIcon("src/res/O.jpg");
    private final ImageIcon EMPTY = new ImageIcon("src/res/Empty.jpg");

    private JMenu createFileMenu() {
        JMenu file = new JMenu("Файл");
        JMenuItem open = new JMenuItem("Новая игра");
        JMenuItem exit = new JMenuItem("Выход");
        open.addActionListener(actionEvent -> {
            try {
                new GameXO_GUI("Крестики - нолики");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    private JPanel getMap() throws InterruptedException {
        map = new JButton[3][3];
        mapStr = initializingTheMap(3);
        JPanel panel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                map[i][j] = new JButton();
                map[i][j].setIcon(EMPTY);
                JButton tmp = map[i][j];
                int x = i;
                int y = j;
                tmp.addActionListener(actionEvent -> {
                    tmp.setEnabled(false);
                    tmp.setDisabledIcon(DOT_X);
                    mapStr[x][y] = dot_x;
//                    try {
//                        int[] arr = comparison( turnAi(mapStr), mapStr);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }

                    for (int k = 0; k < 3; k++) {
                        System.out.println();
                        for (int l = 0; l < 3; l++) {
                            System.out.print(mapStr[k][l]);
                        }
                    }


                });
                panel.add(map[i][j]);
            }
        }
        return panel;
    }

    public GameXO_GUI(String title) throws HeadlessException, InterruptedException {
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

//    public static int[] comparison(String[][]newMapStr, String[][]mapStr) {
//        for (int i = 0; i < mapStr.length; i++) {
//            for (int j = 0; j < mapStr.length; j++) {
//                if(!newMapStr[i][j].equals(mapStr[i][j])) {
//                    return new int[] {i, j};
//                }
//            }
//        }
//    }

    public static String[][] initializingTheMap(int size) {
        String[][] map = new String[size][size];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j] = empty;
            }
        }
        return map;
    }

    public static boolean emptyField(String[][] map, int x, int y) {
        if (x >= 0 && x < map.length && y >= 0 && y < map.length) {
            return map[x][y].equals(empty);
        } else {
            return false;
        }
    }

    public static boolean victoryCondition(String[][] map, String markPlayer) {

        for (String[] strings : map) {
            int count_x = 0;
            for (int j = 0; j < map.length; j++) {
                if (strings[j].equals(markPlayer)) count_x++;
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
                    if (map[i][j].equals(markPlayer)) count_y++;
                    if (count_y == 3) {
                        return true;
                    }
                }
            }
            loopCounter++;
        }

        int count_a = 0, count_b = 0;
        for (int i = 1; i < map.length; i++) {
            for (int j = 1; j < map.length; j++) {
                if (i - j == 0 && map[i][j].equals(markPlayer)) count_a++;
                if (i + j == map.length && map[i][j].equals(markPlayer)) count_b++;
            }
        }
        return count_a == 3 || count_b == 3;
    }

    public static String[][] turnAi(String[][] map) throws InterruptedException {
        System.out.println();
        Thread.sleep(1000);
        System.out.println("Ход компьютера");
        Thread.sleep(2000);

        boolean flag = false;

        label:
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (emptyField(map, i, j)) {
                    map[i][j] = dot_0;
                    if (victoryCondition(map, dot_0)) {
                        flag = true;
                        break label;
                    } else map[i][j] = empty;
                }
            }
        }

        if (!flag) {
            label:
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map.length; j++) {
                    if (emptyField(map, i, j)) {
                        map[i][j] = dot_x;
                        if (victoryCondition(map, dot_x)) {
                            map[i][j] = dot_0;
                            flag = true;
                            break label;
                        } else map[i][j] = empty;
                    }
                }
            }
        }

        if (!flag) {
            boolean flag2 = false;
            while (!flag2) {
                int x = (int) (Math.random() * 3);
                int y = (int) (Math.random() * 3);
                if (emptyField(map, x, y)) {
                    map[x][y] = dot_0;
                    flag2 = true;
                }
            }
        }

        return map;
    }


    public static void main(String[] args) throws InterruptedException {
        new GameXO_GUI("Крестики - нолики");
    }
}
