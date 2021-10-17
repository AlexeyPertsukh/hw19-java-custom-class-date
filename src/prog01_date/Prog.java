package prog01_date;

import java.time.DateTimeException;
import java.util.Scanner;

public class Prog {

    private String cmd;
    private boolean endProg;
    private final Scanner sc;

    public Prog() {
        sc = new Scanner(System.in);
    }

    public void go() {
        printOnStart();
        while(!endProg) {
            printMenu();
            inputCommand();
            processCommand();
        }
        printOnEnd();
    }

    private void inputCommand() {
        System.out.print("Ввведите команду: ");
        cmd = sc.next();
    }

    private void processCommand() {
        String key;

        //хэлп
        key = Command.HELP.getKey();
        if (cmd.equalsIgnoreCase(key)) {
            printHelp();
            return;
        }

        //день недели
        key = Command.DAY_OF_WEEK.getKey();
        if (cmd.equalsIgnoreCase(key)) {
            dayOfWeek();
            return;
        }

        //количество дней в году
        key = Command.DAYS_IN_YEAR.getKey();
        if (cmd.equalsIgnoreCase(key)) {
            daysInYear();
            return;
        }

        //количество дней в месяце
        key = Command.DAYS_IN_MONTH.getKey();
        if (cmd.equalsIgnoreCase(key)) {
            daysInMonth();
            return;
        }

        //порядковый номер дня в году по дате
        key = Command.NUM_DAY_OF_YEAR.getKey();
        if (cmd.equalsIgnoreCase(key)) {
            numDayOfYear();
            return;
        }

        //дата по порядковому номеру дня в году
        key = Command.DATE_BY_DAY.getKey();
        if (cmd.equalsIgnoreCase(key)) {
            dateByDay();
            return;
        }

        //день программиста
        key = Command.DAY_PROGRAMMER.getKey();
        if (cmd.equalsIgnoreCase(key)) {
            dayProgrammer();
            return;
        }

        //кол-во дней между датами
        key = Command.DAYS_BETWEEN.getKey();
        if (cmd.equalsIgnoreCase(key)) {
            daysBetweenDates();
            return;
        }

        //выход
        key = Command.END.getKey();
        if (cmd.equalsIgnoreCase(key)) {
            endProg = true;
            return;
        }

        //
        System.out.println("Неизвестная команда");
        Util.inputCharToContinue(sc);
    }


    private void printOnStart() {
        System.out.println(Const.PROGRAM_NAME + " v." + Const.PROGRAM_VERSION);
        System.out.println("..........................");
        System.out.println();
    }

    private void printOnEnd() {
        System.out.println();
        System.out.println(Const.COPYRIGHT);
        System.out.println(Const.AUTHOR);
    }

    private void printMenu() {
        for (Command tmp : Command.values()) {
            System.out.printf("%-5s - %s \n", tmp.getKey(), tmp.getNameRus());
        }
        System.out.println("------------------------");
        System.out.println();

    }

    private void printHelp() {
        System.out.println();
        System.out.println(Const.PROGRAM_NAME + " v." + Const.PROGRAM_VERSION);
        String fileName = ReaderLocalFile.getFilenameWithAbsolutePatch(Const.LOCAL_PATCH, Const.FILENAME_HELP);
        ReaderLocalFile.printFromFile(fileName);
        System.out.println();
        Util.inputCharToContinue(sc);
    }

    private void daysInYear() {
        int year = Util.nextInt(sc,"Год: ");
        try {
            Date date = Date.of(1,1, year);
            System.out.printf("Количество дней в %d г.: %d \n", year,   date.daysInYear());
            Util.inputCharToContinue(sc);
        }
        catch (DateTimeException ex)
        {
            Util.inputCharToContinue(sc, "Введен некорректный номер года");
        }
    }

    private void daysInMonth() {
        int year = Util.nextInt(sc,"Год: ");
        int month = Util.nextInt(sc,"Месяц: ");
        try {
            System.out.printf("Количество дней в месяце: %d \n", Date.Month.getNumDays(month, year));
            Util.inputCharToContinue(sc);
        }
        catch (DateTimeException ex)
        {
            Util.inputCharToContinue(sc, "Введен некорректный номер года или месяца");
        }
    }

    //порядковый номер дня в году
    private void numDayOfYear() {
        Date date = Util.inputDate();
        System.out.printf("Порядковый номер дня в году: %d \n", date.getDayOfYear());
        System.out.println("Проверка: https://allcalc.ru/node/1106");
        Util.inputCharToContinue(sc);
    }

    private void dateByDay(int year, int numDay) {
        try {
            Date date = Date.dateByDay(year, numDay);
            System.out.printf("%d-й день в %d году: %s  \n", numDay, year, date);
            System.out.println("Проверка: https://allcalc.ru/node/1988");
            Util.inputCharToContinue(sc);
        }
        catch (DateTimeException ex) {
            Util.inputCharToContinue(sc, Const.MSG_DATE_INCORRECT);
        }
    }

    private void dateByDay() {
        int year = Util.nextInt(sc, "Год: ");
        int numDay = Util.nextInt(sc, "Порядковый номер дня в году: ");
        dateByDay(year, numDay);
    }

    private void dayProgrammer() {
        int year = Util.nextInt(sc, "Год: ");
        System.out.println("256-й день в году - ДЕНЬ ПРОГРАММИСТА " );
        dateByDay(year, 256);
    }

    //количество дней между двумя датами
    private void daysBetweenDates() {
        Date dateFrom = Util.inputDate("Введите первую дату");
        Date dateTo = Util.inputDate("Введите вторую дату");

        int days = dateFrom.daysBetween(dateTo);
        System.out.printf("Количество дней между %s - %s : %d \n", dateFrom, dateTo, days);
        System.out.println("Проверка: https://planetcalc.ru/273/");
        Util.inputCharToContinue(sc);

    }

    //день недели
    private void dayOfWeek() {
        Date date = Util.inputDate();
        Date.DayOfWeek dayOfWeek = date.getDayOfWeek();
        System.out.println("День недели: " + dayOfWeek.getName());
        System.out.println("Проверка: https://planetcalc.ru/79/");
        Util.inputCharToContinue(sc);
    }


}
