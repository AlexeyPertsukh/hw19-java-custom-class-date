/*
2. (лр)
Класс  GameConsole
brand (название производителя, например Sony, Microsoft. Можно оформить
enum-ом),
model (название модели, например XBOX 360, PS4 PRO),
serial (серийный номер приставки, например XC123QeWR),
firstGamepad (объект для первого джойстика, который будет реализован как
внутренний класс),
secondGamepad (объект для второго джойстика),
isOn (флаг состояния. True – вкл, false - выкл)

• Создать внутренний (нестатический) класс Gamepad.
Описать поля:
brand (название производителя, например Sony, Microsoft).
consoleSerial (серийный номер консоли, к которой подключен джойстик),
connectedNumber (порядковый номер джойстика),
color (цвет джойстика, можно оформить enum-ом),
chargeLevel (значение процента заряда, по умолчанию поставить 100.0)
isOn (флаг состояния. True – вкл, false - выкл).

• Создать конструктор для класса GameConsole. Передать в него 2 параметра
(brand, serial)
• Создать конструктор для класса Gamepad. Передать в него параметр (brand и
connectedNumber), а полю consoleSerial присвоить значение серийного
номера приставки.
• Внутри конструктора создать и присвоить 2 джойстика (firstGamepad,
secondGamepad). Причем brand можно передать уже существующие для самой
консоли, а connectedNumber фиксированными значениями 1 и 2.
• Для полей которые не должны меняться (определите их сами :) ), применить
модификатор final, и создать геттеры.
• Для остальных полей создать геттеры и сеттеры.

------
5* Доработать класс GameConsole
• Создать интерфейс Powered с методами powerOn и PowerOff
• Реализовать данный интерфейс для джойстика и консоли
• Добавить функционал, который включает приставку, когда включается
джойстик.
• Добавить функционал, который делает «второй» джойстик «первым», если
первый был выключен.
• Добавить поле Game activeGame
• Добавить метод loadGame(Game). В нем вывести сообщение «Игра
{название} загружается»
• Добавить метод playGame(). В нем выводить информацию о текущей игре
«Играем в {игра}» и информацию о заряде только активных джойстиков.
Внимание! При каждом вызове метода – уменьшать заряд джойстика на
10%. Когда заряд уменьшиться до 0 – нужно выключить джойстик.
• Добавить приватный метод void checkStatus(). Который будет вызываться
каждый раз когда вызывается метод playGame().
Добавить новое поле для класса GameConsole – waitingCounter;
Если оба джойстика выключены – выводить сообщение «Подключите
джойстик» и увеличивать счетчик на 1. Если хотя-бы один джойстик
активен – сбрасывать в 0.
Если счетчик превысил 5 циклов ожидания – «Выключить» приставку и
бросить исключение с текстом «Приставка завершает работу из-за
отсутствия активности» (Класс-исключение создать свой.)

 */
package prog02_gamepad;

public class GameConsole implements Powered{
    private final Brand brand;
    private final String serial;
    private final Gamepad firstGamepad;
    private final Gamepad secondGamepad;
    private String model;
    private boolean isOn;
    private int waitingCounter;
    private boolean isPlayed;

    private Game activeGame;


    public GameConsole(Brand brand, String serial) {
        this.brand = brand;
        this.serial = serial;
        firstGamepad = new Gamepad(brand, 1, "левый");
        secondGamepad = new Gamepad(brand, 2, "правый");
    }

    public Brand getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getSerial() {
        return serial;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public boolean isOn() {
        return isOn;
    }

    public Gamepad getFirstGamepad() {
        return firstGamepad;
    }

    public Gamepad getSecondGamepad() {
        return secondGamepad;
    }

    public Game getActiveGame() {
        return activeGame;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void loadGame(Game game) {
        if(!isOn) {
            System.out.println("Невозможно загрузить игру - приставка отключена");
            return;
        }
        activeGame = game;
        System.out.printf("Игра \"%s\" загружена в приставку \n", activeGame.getName());
        isPlayed = false;
    }

    /*
    • Добавить метод playGame(). В нем выводить информацию о текущей игре
    «Играем в {игра}» и информацию о заряде только активных джойстиков.
    Внимание! При каждом вызове метода – уменьшать заряд джойстика на
    10%. Когда заряд уменьшиться до 0 – нужно выключить джойстик.
     */
    public void playGame() {
        if(!isOn) {
            System.out.println("Играть не получится - сначала включите приставку");
            return;
        }

        if(activeGame == null) {
            System.out.println("Играть не получится - сначала загрузите игру");
            return;
        }

        isPlayed = true;
        System.out.printf("Играем в \"%s\" \n", activeGame.getName());

        for (int i = 0; i < 2; i++) {
            Gamepad gamepad = (i == 0) ? firstGamepad : secondGamepad;
            if(gamepad.isOn) {
                gamepad.addChargeLevel(-10);
                System.out.println(gamepad.chargeInfo());
            }
        }

        try {
            checkStatus();
        } catch (MyException ex) {
            System.out.println(ex.getMessage());
        }

    }

    /*
    Добавить приватный метод void checkStatus(). Который будет вызываться
    каждый раз когда вызывается метод playGame().
    Добавить новое поле для класса GameConsole – waitingCounter;
    Если оба джойстика выключены – выводить сообщение «Подключите
    джойстик» и увеличивать счетчик на 1. Если хотя-бы один джойстик
    активен – сбрасывать в 0.
    Если счетчик превысил 5 циклов ожидания – «Выключить» приставку и
    бросить исключение с текстом «Приставка завершает работу из-за
    отсутствия активности» (Класс-исключение создать свой.)
     */
    private void checkStatus() throws MyException {
          if(!firstGamepad.isOn && !secondGamepad.isOn) {
              System.out.println("Подключите джойстик");
              waitingCounter++;
          }
          else {
              waitingCounter = 0;
          }

          if(waitingCounter > 5) {
              powerOff();
              throw new MyException("Причина отключения: отсутствия активности");
          }
    }

    //
    private void gamepadConnectNumberControl() {
        if(!firstGamepad.isOn() && secondGamepad.isOn()) {
            firstGamepad.setConnectedNumber(2);
            secondGamepad.setConnectedNumber(1);
        }
        else if(!secondGamepad.isOn()) {
            firstGamepad.setConnectedNumber(1);
            secondGamepad.setConnectedNumber(2);
        }
    }

    @Override
    public void powerOn() {
        if (isOn) {
            return;
        }
        isOn = true;
        System.out.println("Приставка включена");
    }

    @Override
    public void powerOff() {
        isOn = false;
        isPlayed = false;
        activeGame = null;
        System.out.println("Приставка отключена");
    }

    //
    public class Gamepad implements Powered {
        private final Brand brand;
        private final String consoleSerial;
        private int connectedNumber;
        private Color color;
        private double chargeLevel; //уровень заряда
        private boolean isOn;
        private final String location;

        public Gamepad(Brand brand, int connectedNumber, String location) {
            this.brand = brand;
            this.connectedNumber = connectedNumber;
            this.location = location;
            consoleSerial = GameConsole.this.serial; //полю consoleSerial присвоить значение серийного номера приставки
            chargeLevel = 100;
        }

        public Brand getBrand() {
            return brand;
        }

        public String getConsoleSerial() {
            return consoleSerial;
        }

        public int getConnectedNumber() {
            return connectedNumber;
        }

        public Color getColor() {
            return color;
        }

        public double getChargeLevel() {
            return chargeLevel;
        }

        public String getLocation() {
            return location;
        }

        public boolean isOn() {
            return isOn;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public void setConnectedNumber(int connectedNumber) {
            this.connectedNumber = connectedNumber;
        }

        public void addChargeLevel(double level) {
            chargeLevel += level;

            if (chargeLevel < 0) {
                chargeLevel = 0;
            }

            if (chargeLevel > 100) {
                chargeLevel = 100;
            }

            if(chargeLevel == 0) {
                powerOff();
            }
        }

        public String chargeInfo() {
            return String.format("%s джойстик (#%d), заряд : %.1f %% ", location, connectedNumber, chargeLevel);
        }

        private void printConnectStatus() {
            String strConnectStatus = (isOn) ? "включен" : "отключен";
            System.out.printf("%s джойстик (#%d) %s \n", location, connectedNumber, strConnectStatus);
        }

        @Override
        public void powerOn() {
            isOn = true;
            GameConsole.this.powerOn();
            gamepadConnectNumberControl();
            printConnectStatus();
        }

        @Override
        public void powerOff() {
            isOn = false;
            printConnectStatus();
            gamepadConnectNumberControl();
        }
    }

}
