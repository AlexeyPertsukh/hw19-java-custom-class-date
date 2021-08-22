/*
1.
Создать объект класса Date, используя вложенные классы Год, Месяц, День.
Методы: задать дату, вывести на консоль день недели по заданной дате.
День недели представить в виде перечисления. Рассчитать количество дней, в заданном временном промежутке.
Т.е. в классе Date реализовать следующее:
public Date(int day, int month, int year)
public DayOfWeek getDayOfWeek()
public int getDayOfYear()
public int daysBetween(Date date)
В классе Year должна осуществляться проверка на високостность (можно реализовать в конструкторе)
в результате, установить значение для соотв. атрибута года.
В классе Month можно сделать метод который позволит узнать количество дней для того или иного месяца [1-12].
Метод можно использовать для подсчета дней в других методах.
public int getDays(int monthNumber, boolean leapYear)
Перечисление должно иметь конструктор с параметром, для того чтобы задать индексы дней недели [0-6].
0 – Понедельник.
Так же можно создать статический метод
public static DayOfWeek valueOf (int index)
Для того чтобы можно было после математических расчетов использовать данный метод для преобразования
индекса дня недели в соотв. элемент перечисления.
При желании можно сделать собственную архитектуру для решения поставленной задачи.
Главное продемонстрировать в решении использование вложенных классов и перечисления.
 */
package prog01_date;

public class Main {

    public static void main(String[] args) {
        Prog prog = new Prog();
        prog.go();
    }
}
