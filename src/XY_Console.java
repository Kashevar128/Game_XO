import javax.swing.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class XY_Console {
    final private static int size = 3;
    final public static String EMPTY_VOID = "_";
    final public static String DOT_X = "X";
    final public static String DOT_O = "O";
    final public static String USER_PLAYER = "ИГРОК";
    final public static String AI_PLAYER = "КОМПЬЮТЕР";

    public static int getSize() {
        return size;
    }

    public static String[][] initializingTheMap(int size) {
        size++;
        String[][] map = new String[size][size];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (i == 0) {
                    map[i][j] = j + "  ";
                } else if (j == 0) {
                    map[i][0] = Integer.toString(i);
                } else map[i][j] = EMPTY_VOID;

            }
        }
        map[0][0] = " ";
        return map;
    }

    public static void mapDemonstration(String[][] map) {
        System.out.print("   ");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (i == 0) {
                    System.out.print(map[i][j] + " ");
                } else if (j == 0) {
                    System.out.print(map[i][j] + " " + " | ");
                } else System.out.print(map[i][j] + " | ");

            }
            System.out.println();
        }
    }

    public static boolean emptyField(String[][] map, int x, int y) {
        if (x > 0 && x < map.length && y > 0 && y < map.length) {
            return map[x][y].equals(EMPTY_VOID);
        } else {
            return false;
        }
    }

    public static String[][] turnUser(String[][] map) throws InterruptedException {
        boolean flag = false;
        System.out.println();
        while (!flag) {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("Ход игрока.");
                System.out.println("Чтобы закончить игру введите: 0");
                System.out.println("Введите по очереди через Enter координаты клетки - x и y");

                int y = sc.nextInt();
                exit(y);
                System.out.println("Координата по горизонтали: " + y);

                int x = sc.nextInt();
                exit(x);
                System.out.println("Координата по вертикали: " + x);


                Thread.sleep(1000);
                System.out.println();

                if (emptyField(map, x, y)) {
                    map[x][y] = DOT_X;
                    flag = true;
                } else {
                    System.out.println("Клетка занята или выходит за пределы игрового поля. Переходите");
                }

            } catch (InputMismatchException e) {
                System.out.println("Ошибка! Введен неверный формат данных.");
                continue;
            }
        }
        return map;
    }

    public static String[][] turnAi(String[][] map) throws InterruptedException {
        System.out.println();
        Thread.sleep(1000);
        System.out.println("Ход компьютера");
        Thread.sleep(2000);

        boolean flag = false;

        label:
        for (int i = 1; i < map.length; i++) {
            for (int j = 1; j < map.length; j++) {
                if (emptyField(map, i, j)) {
                    map[i][j] = DOT_O;
                    if (victoryCondition(map, DOT_O)) {
                        flag = true;
                        break label;
                    } else map[i][j] = EMPTY_VOID;
                }
            }
        }

        if (!flag) {
            label:
            for (int i = 1; i < map.length; i++) {
                for (int j = 1; j < map.length; j++) {
                    if (emptyField(map, i, j)) {
                        map[i][j] = DOT_X;
                        if (victoryCondition(map, DOT_X)) {
                            map[i][j] = DOT_O;
                            flag = true;
                            break label;
                        } else map[i][j] = EMPTY_VOID;
                    }
                }
            }
        }

        if (!flag) {
            boolean flag2 = false;
            while (!flag2) {
                int x = (int) (Math.random() * 3 + 1);
                int y = (int) (Math.random() * 3 + 1);
                if (emptyField(map, x, y)) {
                    map[x][y] = DOT_O;
                    flag2 = true;
                }
            }
        }

        return map;
    }

    public static boolean overflow(String[][] map) {
        int count_c = 0;
        for (int i = 1; i < map.length; i++) {
            for (int j = 1; j < map.length; j++) {
                if (!map[i][j].equals(EMPTY_VOID)) count_c++;
            }
        }
        return count_c == (size * size);
    }

    public static boolean victoryCondition(String[][] map, String markPlayer) {

        for (int i = 1; i < map.length; i++) {
            int count_x = 0;
            for (int j = 1; j < map.length; j++) {
                if (map[i][j].equals(markPlayer)) count_x++;
                if (count_x == 3) {
                    return true;
                }
            }
        }

        int loopCounter = 1;
        while (loopCounter < map.length) {
            int count_y = 0;
            for (int i = 1; i < map.length; i++) {
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

    public static int repeat(String player) {
        System.out.println(player + " ПОБЕДИЛ!");
        System.out.println("Еще раз?\nДля продолжения нажмите: 1\nДля выхода нажмите: 0");
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                int a = sc.nextInt();
                if (a == 0 || a == 1) {
                    return a;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка! Введен неверный формат данных.");
                continue;
            }
        }
    }

    public static int repeat() {
        System.out.println("НИЧЬЯ!");
        System.out.println("Еще раз?\nДля продолжения нажмите: 1\nДля выхода нажмите: 0");
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                int a = sc.nextInt();
                if (a == 0 || a == 1) {
                    return a;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка! Введен неверный формат данных, программа будет завершена.");
                exit(0);
            }
        }
    }

    public static String[][] exitOrRestart(int a, String[][] map) {
        String[][] newMap;
        if (a == 1) {
            newMap = initializingTheMap(size);
            mapDemonstration(newMap);
            return newMap;
        } else if (a == 0) {
            exit(0);
        }
        return map;
    }

    public static int moveAI(String[][] map) {
        try {
            turnAi(map);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mapDemonstration(map);
        if (victoryCondition(map, DOT_O)) {
            return repeat(AI_PLAYER);
        } else if (overflow(map)) {
            return repeat();
        }
        return -1;
    }

    public static int moveUser(String[][] map) {
        try {
            turnUser(map);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mapDemonstration(map);
        if (victoryCondition(map, DOT_X)) {
            return repeat(USER_PLAYER);
        } else if (overflow(map)) {
            return repeat();
        }
        return -1;
    }

    public static int firstMove() throws InterruptedException {
        System.out.println("Определение очередности первого хода...");
        Thread.sleep(2000);
        int rand = (int) (Math.random() * 2 + 1);
        if (rand == 1) {
            System.out.println("Вы ходите первым");
            System.out.println();
            Thread.sleep(1000);
            return rand;
        }
        System.out.println("Компьютер ходит первым.");
        Thread.sleep(1000);
        return rand;
    }

    public static void exit(int a) {
        if (a == 0) {
            System.out.println("Выход из игры...");
            System.exit(0);
        }
    }


    public static void main(String[] args) throws InterruptedException {

        System.out.println("Добро пожаловать в игру крестики - нолики!\nВы играете крестиками.");
        System.out.println();
        Thread.sleep(1000);
        String[][] map = initializingTheMap(size);
        int rand = 0;
        try {
            rand = firstMove();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mapDemonstration(map);
        switch (rand) {
            case 1:
                while (true) {
                    map = exitOrRestart(moveUser(map), map);
                    map = exitOrRestart(moveAI(map), map);
                }

            case 2:
                while (true) {
                    map = exitOrRestart(moveAI(map), map);
                    map = exitOrRestart(moveUser(map), map);
                }
        }
    }
}
