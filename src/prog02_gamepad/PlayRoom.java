/*
3. Создать класс PlayRoom
• Создать main метод.
• Создать массив с играми на физ. носителях (4 игры). (пользуемся методом
getDisk)
• Создать массив с играми из виртуального магазина (4 игры). (пользуемся
методом getVirtualGame)
• Создать GameConsole.
 */
package prog02_gamepad;

import java.util.Arrays;
import java.util.Scanner;

public class PlayRoom {
    public static final int CMD_CONSOLE_ON = 1;
    public static final int CMD_CONSOLE_OFF = 2;
    public static final int CMD_GAME_LOAD = 3;
    public static final int CMD_GAME_PLAY = 4;
    public static final int CMD_FIRST_GAMEPAD_ON = 11;
    public static final int CMD_FIRST_GAMEPAD_OFF = 12;
    public static final int CMD_SECOND_GAMEPAD_ON = 21;
    public static final int CMD_SECOND_GAMEPAD_OFF = 22;
    public static final int CMD_INFO = 30;

    public static final int CMD_END = 0;


    public static void main(String[] args) {

        Game.GameDisk[] gameDisks = loadGameDisks();
        Game.VirtualGame[] virtualGames = loadVirtualGames();

        GameConsole gameConsole = new GameConsole(Brand.Sony, "XS98973-99");

        //игры до сортировки
        System.out.println("игры до сортировки");
        System.out.println("--------------");
        printArrObjects(gameDisks);
        System.out.println();
        printArrObjects(virtualGames);
        System.out.println();

        /*
        4. Реализация анонимного класса (Comparator)
        • В методе main, отсортировать массив с физическими дисками по жанру.
        • В методе main, отсортировать массив с виртуальными играми по рейтингу.
        Для сравнения примитивов можно воспользоваться методом :
        Integer.compare(a.getRating(), b.getRating())
        */
        //сортировка
        Arrays.sort(gameDisks, (gameDisks1, gameDisks2) -> { String a = gameDisks1.getData().getGanre().name();
                                                             String b = gameDisks2.getData().getGanre().name();
                                                             return a.compareTo(b);
                                                            });

        Arrays.sort(virtualGames, (virtualGame1, virtualGame2) -> { int a = virtualGame1.getRating();
                                                                    int b = virtualGame2.getRating();
                                                                    return Integer.compare(a, b);
                                                            });
        //игры после сортировки
        System.out.println("игры после сортировки");
        System.out.println("--------------");
        printArrObjects(gameDisks);
        System.out.println();
        printArrObjects(virtualGames);
        System.out.println();

        //
        boolean end = false;
        int command = 0;
        Scanner sc = new Scanner(System.in);
        while (!end) {
            System.out.println(".............................................");
            System.out.println("КОМАНДЫ ИГРОВОЙ КОНСОЛИ");
            System.out.printf("Приставка:           %-2d включить    %-2d отключить \n", CMD_CONSOLE_ON, CMD_CONSOLE_OFF);
            System.out.printf("Игра:                %-2d загрузить   %-2d играть    \n", CMD_GAME_LOAD, CMD_GAME_PLAY);
            System.out.printf("Левый  джойстик:     %-2d включить    %-2d отключить \n", CMD_FIRST_GAMEPAD_ON, CMD_FIRST_GAMEPAD_OFF);
            System.out.printf("Правый джойстик:     %-2d включить    %-2d отключить \n", CMD_SECOND_GAMEPAD_ON, CMD_SECOND_GAMEPAD_OFF);
            System.out.printf("Состояние приставки: %-2d                            \n", CMD_INFO);
            System.out.printf("Выход:               %-2d                            \n", CMD_END);
            System.out.println(".............................................");
            command = inputInt(sc,"Введите команду: ");

            switch (command) {
                case CMD_CONSOLE_ON:
                    gameConsole.powerOn();
                    break;
                case CMD_CONSOLE_OFF:
                    gameConsole.powerOff();
                    break;
                case CMD_GAME_LOAD:
                    Game game = inputGame(sc, gameDisks, virtualGames);
                    gameConsole.loadGame(game);
                    break;
                case CMD_GAME_PLAY:
                    gameConsole.playGame();
                    break;
                case CMD_FIRST_GAMEPAD_ON:
                    gameConsole.getFirstGamepad().powerOn();
                    break;
                case CMD_FIRST_GAMEPAD_OFF:
                    gameConsole.getFirstGamepad().powerOff();
                    break;
                case CMD_SECOND_GAMEPAD_ON:
                    gameConsole.getSecondGamepad().powerOn();
                    break;
                case CMD_SECOND_GAMEPAD_OFF:
                    gameConsole.getSecondGamepad().powerOff();
                    break;
                case CMD_INFO:
                    printInfoConsole(gameConsole);
                    break;
                case CMD_END:
                    end = true;
                    break;
                default:
                    System.out.println("Неизвестная команда");
            }

            inputForContinue(sc);
            System.out.println();

        }


    }

    public static Game.GameDisk[] loadGameDisks() {
        return new Game.GameDisk[] { Game.getDisk("Herors II", Ganre.STRATEGY, "Legendary turn based strategy"),
                                     Game.getDisk("Half-Life 2", Ganre.ACTION, "Super sports game"),
                                     Game.getDisk("Need for Speed 9", Ganre.RACE, "Crazy car racing"),
                                     Game.getDisk("Counter-Strike", Ganre.ACTION, "Cool shooter"),
                                    };
    }

    public static Game.VirtualGame[] loadVirtualGames() {
        return new Game.VirtualGame[] { Game.getVirtualGame("ZAZ RACING 2021", Ganre.RACE, 4),
                                        Game.getVirtualGame("Hitman", Ganre.ACTION, 3),
                                        Game.getVirtualGame("IL-2 stormtrooper", Ganre.ACTION, 5),
                                        Game.getVirtualGame( "UEFA 2021", Ganre.SPORT,4),

                                       };
    }


    //распечатать массив любых объектов
    public static <T> void printArrObjects(T[] arr) {
        int i = 1;
        for (T value : arr) {
            System.out.println(i + ". " + value);
            i++;
        }
    }


    //выбор игры
    public static Game inputGame(Scanner sc, Game.GameDisk[] gameDisks,Game.VirtualGame[] virtualGames) {
        System.out.println(".....");
        int type = 0;
        while (type != 1 && type != 2) {
            type = inputInt(sc, "1 - диск, 2 - виртуальная игра: ");
        }
        System.out.println(type);
        System.out.println();
        int max = 0;
        if(type == 1) {
            printArrObjects(gameDisks);
            max = gameDisks.length;
        }
        else {
            printArrObjects(virtualGames);
            max = virtualGames.length;
        }

        int num = 0;
        while (num < 1 || num > max) {
            num = inputInt(sc, "Номер игры: ");
        }
        num--;

        if(type == 1) {
            return gameDisks[num].getData();
        }
        else {
            return virtualGames[num].getData();
        }
    }

    public static int inputInt(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            String str = sc.next();
            try {
                return Integer.parseInt(str);
            }
            catch (NumberFormatException ignored) {
            }
        }
    }

    public static void inputForContinue(Scanner sc) {
        while (true) {
            System.out.print("Для продолжения введите 'Y': ");
            String str = sc.next();
            if(str.compareToIgnoreCase("Y") == 0) {
                return;
            }
        }
    }

    public static void printInfoGamepad(GameConsole.Gamepad gamepad) {
        System.out.println("---");
        String str = gamepad.getLocation() + " джойстик:";
        String strPower = (gamepad.isOn()) ? "включен" : "отключен";

        System.out.printf("%-18s %s \n", str, strPower);

        str = (gamepad.isOn()) ?  "#" + gamepad.getConnectedNumber() : "-";
        System.out.printf("номер подключения: %s \n", str);
        System.out.printf("заряд:             %.1f %% \n", gamepad.getChargeLevel());
    }

    public static void printInfoConsole(GameConsole gameConsole) {
        System.out.println();
        System.out.println("---");
        String str = (gameConsole.isOn()) ? "включена" : "отключена";
        System.out.println("Приставка:     " + str);
        str = (gameConsole.getActiveGame() == null) ? "не загружена" : gameConsole.getActiveGame().getName();
        System.out.printf("игра:          %s \n", str);
        str = (gameConsole.isPlayed()) ? "идет игра" : "играть пока не начали";
        System.out.println("               " + str);

        //firstGamepad
        printInfoGamepad(gameConsole.getFirstGamepad());

        //secondGamepad
        printInfoGamepad(gameConsole.getSecondGamepad());

        System.out.println("---");
        System.out.println();
        ;

    }



}
