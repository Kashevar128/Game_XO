import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameXO_GUI extends JFrame {
    final public static String empty = "_";
    final public static String dot_x = "X";
    final public static String dot_0 = "O";
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
                String tmpStr = mapStr[i][j];
                tmp.addActionListener(actionEvent -> {
                    tmp.setEnabled(false);
                    tmp.setDisabledIcon(DOT_X);
                    turnUser(tmpStr);
                    System.out.println();
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

    public static String[][] initializingTheMap(int size) {
        String[][] map = new String[size][size];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j] = empty;
            }
        }
        return map;
    }

    public String turnUser(String tmpStr) {
        return tmpStr = dot_x;
    }

    public static void main(String[] args) throws InterruptedException {
        new GameXO_GUI("Крестики - нолики");
    }
}
