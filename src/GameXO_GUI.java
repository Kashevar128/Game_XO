import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class GameXO_GUI extends JFrame {
    final private static String empty = "_";
    final private static String dot_x = "X";
    final private static String dot_0 = "O";
    final private static String user = "ИГРОК";
    final private static String computer = "Компьютер";
    final private static String massageWin = " ПОБЕДИЛ!!!";
    final private static String massageDraw = "НИЧЬЯ))";

    private JButton[][] map;
    private static String[][] mapStr;
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

    private JPanel getMap() {
        map = new JButton[3][3];
        mapStr = initializingTheMap(3);
        String[][] oldMapStr = new String[3][3];
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
                    if (!overflow(mapStr)) {
                        if (victoryCondition(mapStr, dot_x)) {
                            frameDialog(user, massageWin);
                            setEnabled(false);
                            return;
                        }
                    } else {
                        frameDialog(" ", massageDraw);
                        setEnabled(false);
                        return;
                    }

                    arrayCopy(oldMapStr, mapStr);
                    try {
                        comparison(turnAi(mapStr), oldMapStr, map, DOT_O);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!overflow(mapStr)) {
                        if (victoryCondition(mapStr, dot_0)) {
                            frameDialog(computer, massageWin);
                            setEnabled(false);
                        }
                    } else {
                        frameDialog(" ", massageDraw);
                        setEnabled(false);
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

    public static void comparison(String[][] newMapStr, String[][] mapStr, JButton[][] map, ImageIcon icon) {

        for (int i = 0; i < mapStr.length; i++) {
            for (int j = 0; j < mapStr.length; j++) {
                if (!newMapStr[i][j].equals(mapStr[i][j])) {
                    map[i][j].setEnabled(false);
                    map[i][j].setDisabledIcon(icon);
                }
            }
        }
    }

    public static void frameDialog(String name, String msg) {
        JFrame jfrm = new JFrame();
        JPanel panel = new JPanel();
        jfrm.setSize(400, 130);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel jLab = new JLabel(name + msg);
        jLab.setFont(new Font("Arial", Font.BOLD, 30));
        JButton button1 = new JButton("Новая игра");
        JButton button2 = new JButton("Выйти из игры");
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(jLab);
        panel.add(button1);
        panel.add(button2);
        jfrm.add(panel);
        jfrm.setAlwaysOnTop(true);
        jfrm.setLocationRelativeTo(null);
        jfrm.setResizable(false);
        jfrm.setVisible(true);

    }

    public static String[][] arrayCopy(String[][] arr, String[][] arr1) {
        for (int k = 0; k < mapStr.length; k++) {
            for (int l = 0; l < mapStr.length; l++) {
                arr[k][l] = arr1[k][l];
            }
        }
        return arr;
    }

    public static String[][] initializingTheMap(int size) {
        String[][] map = new String[size][size];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j] = "_";
            }
        }
        return map;
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
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (i - j == 0 && map[i][j].equals(markPlayer)) count_a++;
                if (i + j == map.length - 1 && map[i][j].equals(markPlayer)) count_b++;
            }
        }
        return count_a == 3 || count_b == 3;
    }

    public static boolean overflow(String[][] map) {
        int count_c = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (!map[i][j].equals(empty)) count_c++;
            }
        }
        return count_c == (map.length * map.length);
    }

    public static String[][] turnAi(String[][] map) {
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

    public static boolean emptyField(String[][] map, int x, int y) {
        return map[x][y].equals(empty);
    }


    public static void main(String[] args) throws InterruptedException {
        new GameXO_GUI("Крестики - нолики");
    }
}
