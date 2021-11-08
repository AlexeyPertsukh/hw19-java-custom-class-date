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

    @SuppressWarnings("unused")
    private Date() {
    }

    private Date(Day dateDay, Month dateMonth, Year dateYear) {
        this.dateDay = dateDay;
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
    }

    public static Date of(int day, int month, int year) throws DateTimeException {
        Year tmpYear = new Year(year);
        Month tmpMonth = Month.valueOf(month);
        int maxDay = tmpMonth.getNumDays(year);
        Day tmpDay = new Day(day, maxDay);

        //не произошло исключение при создании объектов выше- все ок

        return new Date(tmpDay, tmpMonth, tmpYear);
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

        return Date.of(day, month, year);
    }


    //номер дня с начала года
    public int getDayOfYear() {
        int numDayOfYear = 0;
        for (int i = 1; i < dateMonth.getNum() ; i++) {
            Month month = Month.valueOf(i);
            numDayOfYear += month.getNumDays(dateYear.getValue());
        }
        return numDayOfYear + dateDay.getValue();
    }

    //кол-во дней в году
    public int daysInYear() {
        return dateYear.getDays();
    }


    //кол-во дней в месяце
    public int daysInMonth() {
        return dateMonth.getNumDays(dateYear.getValue());
    }

    //дата по порядковому номеру дня в году
    public static Date dateByDay(int year, int numDay) {
        int cnt = 0;
        int day = 0;
        int cntMonth = 0;

        for (int i = 1; i <= 12 ; i++) {
            Month month = Month.valueOf(i);
            if(numDay > cnt + month.getNumDays(year) ) {
                cntMonth = i;
                cnt += month.getNumDays(year);
            }
            else {
                cntMonth++;
                day = numDay - cnt;
                break;
            }
        }

        return Date.of(day, cntMonth, year);
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
        System.out.println("***"+day);
        day = (day % 7) - 1;
        if(day < 0) {
            day = 6;
        }
        return DayOfWeek.valueOf(day);
    }

    @SuppressWarnings("unused")
    public int getNumDay() {
        return dateDay.getValue();
    }

    @SuppressWarnings("unused")
    public int getNumMonth() {
        return dateMonth.getNum();
    }

    @SuppressWarnings("unused")
    public int getNumYear() {
        return dateYear.getValue();
    }

    @Override
    public String toString() {
        return String.format("%02d.%02d.%02d", dateDay.getValue(), dateMonth.getNum(), dateYear.getValue());
    }

    //==== вложенные классы ====

    public enum Month {
        JANUARY("Январь", 31),
        FEBRUARY("Февраль", 28),
        MARCH("Март", 31),
        APRIL("Апрель", 30),
        MAY("Май", 31),
        JUNE("Июнь", 30),
        JULY("Июль", 31),
        AUGUST("Август", 31),
        SEPTEMBER("Сентябрь", 30),
        OCTOBER("Октябрь", 31),
        NOVEMBER("Ноябрь", 30),
        DECEMBER("Декабрь", 31),
        ;

        private final static String INVALID_VALUE_MONTH = "Invalid value for Month (valid values 1 - 12): ";

        private final String name;
        private final int numDays;

        Month(String name, int numDays) {
            this.name = name;
            this.numDays = numDays;
        }

        @SuppressWarnings("unused")
        public String getName() {
            return name;
        }

        public int getNum() {
            Month[] enums = values();
            for (int i = 0; i < enums.length; i++) {
                if(this == enums[i]) {
                    return i + 1;
                }
            }
            return 0;
        }

        public int getNumDays(int year) {
            if(isFebruaryInLeapYear(this, year)) {
                return numDays + 1;
            } else {
                return numDays;
            }

        }

        public static int getNumDays(int numMonth, int year) {
            validate(numMonth);
            Month[] enums = values();
            return enums[numMonth - 1].getNumDays(year);
        }

        public static Month valueOf(int numMonth) {
            validate(numMonth);
            Month[] enums = values();
            return enums[numMonth - 1];
        }

        private static boolean isFebruaryInLeapYear(Month month, int year ) {
            return month == FEBRUARY && Year.isLeapYear(year);
        }

        private static void validate(int numMonth) {
            if (numMonth < 1 || numMonth > 12) {
                throw new DateTimeException(INVALID_VALUE_MONTH + numMonth);
            }
        }
    }


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

    //
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

        public static boolean isLeapYear(int year) {
            return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
        }
    }

    //
    private static class Day
    {
        private int value;

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
