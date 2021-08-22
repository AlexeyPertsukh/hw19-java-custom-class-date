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
        Command command;

        //хэлп
        key = Command.HELP.getKey();
        if (My.cmpStr(cmd, key)) {
            printHelp();
            return;
        }

        //день недели
        key = Command.DAY_OF_WEEK.getKey();
        if (My.cmpStr(cmd, key)) {
            dayOfWeek();
            return;
        }

        //количество дней в году
        key = Command.DAYS_IN_YEAR.getKey();
        if (My.cmpStr(cmd, key)) {
            daysInYear();
            return;
        }

        //количество дней в месяце
        key = Command.DAYS_IN_MONTH.getKey();
        if (My.cmpStr(cmd, key)) {
            daysInMonth();
            return;
        }

        //порядковый номер дня в году по дате
        key = Command.NUM_DAY_OF_YEAR.getKey();
        if (My.cmpStr(cmd, key)) {
            numDayOfYear();
            return;
        }

        //дата по порядковому номеру дня в году
        key = Command.DATE_BY_DAY.getKey();
        if (My.cmpStr(cmd, key)) {
            dateByDay();
            return;
        }

        //день программиста
        key = Command.DAY_PROGRAMMER.getKey();
        if (My.cmpStr(cmd, key)) {
            dayProgrammer();
            return;
        }

        //кол-во дней между датами
        key = Command.DAYS_BETWEEN.getKey();
        if (My.cmpStr(cmd, key)) {
            daysBetweenDates();
            return;
        }

        //выход
        key = Command.END.getKey();
        if (My.cmpStr(cmd, key)) {
            endProg = true;
            return;
        }

        //
        System.out.println("Неизвестная команда");
        My.inputCharToContinue(sc);
    }


    //
    private Date inputDate(String msg) {
        if(msg.isEmpty()) {
            msg = "Введите дату";
        }
        Date date;
        while(true) {
            System.out.print(msg + " (ДД.ММ.ГГГГ): ");
            String strDate = sc.next();
            try {
                date =  Date.parse(strDate);
                return date;
            }
            catch (DateTimeException ex) {
                System.out.println(Const.MSG_DATE_INCORRECT);
            }
        }
    }

    private Date inputDate() {
        return inputDate("");
    }

    private void printOnStart() {
        System.out.println(Const.PROG_NAME + " v." + Const.PROG_VERSION);
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
        System.out.println(Const.PROG_NAME + " v." + Const.PROG_VERSION);
        String fileName = ForFiles.getFilenameWithAbsolutePatch(Const.LOCAL_PATCH, Const.FILENAME_HELP);
        ForFiles.printFromFile(fileName);
        System.out.println();
        My.inputCharToContinue(sc);
    }

    private void daysInYear() {
        int year = My.nextInt(sc,"Год: ");
        try {
            Date date = new Date(1,1, year);
            System.out.printf("Количество дней в %d г.: %d \n", year,   date.daysInYear());
            My.inputCharToContinue(sc);
        }
        catch (DateTimeException ex)
        {
            My.inputCharToContinue(sc, "Введен некорректный номер года");
        }
    }

    private void daysInMonth() {
        int year = My.nextInt(sc,"Год: ");
        int month = My.nextInt(sc,"Месяц: ");
        try {
            Date date = new Date(1, month, year);
            System.out.printf("Количество дней в месяце: %d \n", date.daysInMonth());
            My.inputCharToContinue(sc);
        }
        catch (DateTimeException ex)
        {
            My.inputCharToContinue(sc, "Введен некорректный номер года или месяца");
        }
    }

    //порядковый номер дня в году
    private void numDayOfYear() {
        Date date = inputDate();
        System.out.printf("Порядковый номер дня в году: %d \n", date.getDayOfYear());
        System.out.println("Проверка: https://allcalc.ru/node/1106");
        My.inputCharToContinue(sc);
    }

    private void dateByDay(int year, int numDay) {
        try {
            Date date = Date.dateByDay(year, numDay);
            System.out.printf("%d-й день в %d году: %s  \n", numDay, year, date);
            System.out.println("Проверка: https://allcalc.ru/node/1988");
            My.inputCharToContinue(sc);
        }
        catch (DateTimeException ex) {
            My.inputCharToContinue(sc, Const.MSG_DATE_INCORRECT);
        }
    }

    private void dateByDay() {
        int year = My.nextInt(sc, "Год: ");
        int numDay = My.nextInt(sc, "Порядковый номер дня в году: ");
        dateByDay(year, numDay);
    }

    private void dayProgrammer() {
        int year = My.nextInt(sc, "Год: ");
        System.out.println("256-й день в году - ДЕНЬ ПРОГРАММИСТА " );
        dateByDay(year, 256);
    }

    //количество дней между двумя датами
    private void daysBetweenDates() {
        Date dateFrom = inputDate("Введите первую дату");
        Date dateTo = inputDate("Введите вторую дату");

        int days = dateFrom.daysBetween(dateTo);
        System.out.printf("Количество дней между %s - %s : %d \n", dateFrom, dateTo, days);
        System.out.println("Проверка: https://planetcalc.ru/273/");
        My.inputCharToContinue(sc);

    }

    //день недели
    private void dayOfWeek() {
        Date date = inputDate();
        Date.DayOfWeek dayOfWeek = date.getDayOfWeek();
        System.out.println("День недели: " + dayOfWeek.getName());
        System.out.println("Проверка: https://planetcalc.ru/79/");
        My.inputCharToContinue(sc);
    }


}
