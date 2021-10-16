/*
Проверка:  дата по номеру дня в году
https://allcalc.ru/node/1988

Проверка: Соответствие даты и дня недели
https://wpcalc.com/kalkulyator-dnej-mesyaca/

Проверка: количество дней между датами
https://planetcalc.ru/273/

Проверка: порядковый номер дня
https://allcalc.ru/node/1106

Проверка: день недели
https://planetcalc.ru/79/


 */

package prog01_date;

import java.time.DateTimeException;

public class Date {

    private Day dateDay;
    private Month dateMonth;
    private Year dateYear;

    public Date(int day, int month, int year) {
        set(day, month, year);
    }

    //перевод строки в Date
    //формат строки: "DD.MM.YYYYY"
    public static Date parse(String str) {
        String[] arr = str.split("\\.");

        //если в массиве не 3 строки, то неправильный формат строки
        if(arr.length != 3) {
            String msg = String.format("Invalid format for Date (valid values is \"DD.MM.YYYY\"): %s", str);
            throw new DateTimeException( msg);
        }

        //если в массиве строк не только числа, то неправильный формат строки
        for (String tmp : arr) {
            if(!Util.isInteger(tmp)) {
                String msg = String.format("Invalid format for Date (valid values is \"DD.MM.YYYY\"): %s", tmp);
                throw new DateTimeException( msg);
            }
        }

        int day = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int year = Integer.parseInt(arr[2]);

        return new Date(day, month, year);
    }

    //уставновка новой даты
    //сначала создаем временные объекты- если все ок, записываем их в постоянные объекты даты
    //для того, что бы старая дата не переписывалась при вводе некорректных значений новой даты
    private void set(int day, int month, int year) {
        Year tmpYear = new Year(year);
        Month tmpMonth = new Month(month);
        int maxDay = tmpMonth.getDays(tmpYear.isLeapYear());
        Day tmpDay = new Day(day, maxDay);

        //не произошло исключение при создании объектов выше- все ок
        dateDay = tmpDay;
        dateMonth = tmpMonth;
        dateYear = tmpYear;
    }

    //номер дня с начала года
    public int getDayOfYear() {
        int numDayOfYear = 0;
        for (int i = 1; i < dateMonth.value ; i++) {
            numDayOfYear += dateMonth.getDays(i, dateYear.isLeapYear());
        }
        return numDayOfYear + dateDay.getValue();
    }

    //кол-во дней в году
    public int daysInYear() {
        return dateYear.getDays();
    }


    //кол-во дней в месяце
    public int daysInMonth() {
        return dateMonth.getDays(dateMonth.getValue(), dateYear.isLeapYear());
    }

    //дата по порядковому номеру дня в году
    public static Date dateByDay(int year, int numDay) {
        int cnt = 0;
        int day = 0;
        int month = 0;
        Month tmpMoth =  new Month(1);
        boolean isLeap = new Year(year).isLeapYear();
        for (int i = 1; i <= 12 ; i++) {
            if(numDay > cnt + tmpMoth.getDays(i, isLeap) ) {
                month = i;
                cnt += tmpMoth.getDays(i, isLeap);
            }
            else {
                month++;
                day = numDay - cnt;
                break;
            }
        }

        return new Date(day, month, year);
    }

    //количество дней с начала отcчета - 1.1.1
    private int daysFromStart() {
        int fullEars = dateYear.getValue() - 1;
        int leapYears = (fullEars / 4) - (fullEars / 100) + (fullEars / 400);
        return (fullEars * 365) + leapYears + getDayOfYear();
    }

    public int daysBetween(Date date) {
        int daysFrom = daysFromStart();
        int daysTo = date.daysFromStart();
        if(daysFrom > daysTo) {
            int tmp = daysFrom;
            daysFrom = daysTo;
            daysTo = tmp;
        }
        return daysTo - daysFrom;
    }

    //день недели
    public DayOfWeek getDayOfWeek() {
        int day = daysFromStart();
        day = (day % 7) - 1;
        if(day < 0) {
            day = 6;
        }
        return DayOfWeek.valueOf(day);
    }

    public int getDay() {
        return dateDay.getValue();
    }

    public int getMonth() {
        return dateMonth.getValue();
    }

    public int getYear() {
        return dateYear.getValue();
    }

    @Override
    public String toString() {
        return String.format("%02d.%02d.%02d", dateDay.getValue(), dateMonth.getValue(), dateYear.getValue());
    }

    //==== вложенные классы ====
    public enum DayOfWeek {
        MONDAY("понедельник"),
        TUESDAY("вторник"),
        WEDNESDAY("среда"),
        THURSDAY("четверг"),
        FRIDAY("пятница"),
        SATURDAY("суббота"),
        SUNDAY("воскресенье"),
        ;

        private final String name;

        DayOfWeek(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static DayOfWeek valueOf (int index) {
            DayOfWeek[] dayOfWeeks = DayOfWeek.values();
            return dayOfWeeks[index];
        }
    }

    //год
    private static class Year
    {
        private final static String INVALID_VALUE_YEAR = "Invalid value for Year (valid values > 0): ";
        private int value;

        public Year(int value) {
            setValue(value);
        }

        public void setValue(int value) {
            if(value > 0) {
                this.value = value;
            }
            else {
                throw new DateTimeException(INVALID_VALUE_YEAR + value);
            }
        }

        public int getValue() {
            return value;
        }

        public int getDays() {
            return getDays(value);
        }

        public int getDays(int year) {
            if(year < 1) {
                throw new DateTimeException(INVALID_VALUE_YEAR + year);
            }

            if(isLeapYear(year)) {
                return 366;
            }
            else {
                return 365;
            }
        }

        //Високосный год: делиться без остатка на 4 и не делиться на 100 без остатка
        //                или
        //                делится на 400 без остатка
        public boolean isLeapYear() {
            return isLeapYear(value);
        }

        public boolean isLeapYear(int year) {
            return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
        }
    }

    //год
    private static class Month
    {
        private final static String INVALID_VALUE_MONTH = "Invalid value for Month (valid values 1 - 12): ";
        private int value;

        private final int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        public Month(int value) {
            setValue(value);
        }

        public void setValue(int value) {
            if((value >= 1) && (value <= 12)) {
                this.value = value;
            }
            else {
                throw new DateTimeException(INVALID_VALUE_MONTH + value);
            }
        }


        public int getValue() {
            return value;
        }

        public int getDays(int numMonth, boolean isLeapYear) {
            if (numMonth < 1 || numMonth > 12) {
                throw new DateTimeException(INVALID_VALUE_MONTH + numMonth);
            }
            int days = monthDays[numMonth - 1];
            int FEBRUARY = 2;
            if((numMonth == FEBRUARY) && (isLeapYear)) {
                days++;
            }
            return days;
        }

        public int getDays(boolean isLeapYear) {
            return getDays(value, isLeapYear);
        }
    }

    //день
    private static class Day
    {
        private int value;

        public Day(int value) {
            this(value, 31);
        }

        public Day(int value, int maxValue) {
            setValue(value, maxValue);
        }


        public void setValue(int value, int maxValue) {
            if(value >= 1 && value <= maxValue) {
                this.value = value;
            }
            else {
                String msg = String.format("Invalid value for Day (valid values 1 - %d): %d", maxValue, value);
                throw new DateTimeException(msg);
            }
        }

        public int getValue() {
            return value;
        }
    }

}
