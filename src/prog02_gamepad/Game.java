/*
2. Реализация вложенного статического класса
• Создать класс Game.
Описать поля (пометить final):
name (название игры),
ganre (жанр игры, например ACTION, SPORT, RACE. Можно оформить enumом),
type (тип носителя, например VIRTUAL, PHYSICAL. Можно оформить enumом прямо внутри класса Game),
• Создать приватный конструктор. В конструктор передать поля: name, ganre,
type.
• Создать getter-ы для полей.

• Создать вложенный (статический) класс GameDisk.
• Создать поле description (описание игры, пометить final)
• Создать поле Game data (final);
• В приватный конструктор передать поля: name, ganre, description.
• При вызове конструктора создавать экземпляр класса Game и
передавать в него параметры name, ganre и фиксированный type
соответствующий этому носителю.
• Также инициализировать поле с описанием.
• Создать геттеры
• Создать вложенный (статический) класс VirtualGame.
• Создать поле rating (рейтинг игры от 0 до 5)
• Создать поле Game data (final);
• В приватный конструктор передать поля: name, ganre.
• При вызове конструктора создавать экземпляр класса Game и
передавать в него параметры name, ganre и фиксированный type
соответствующий этому носителю.
• Создать необходимые геттеры/сеттеры

• В классе Game создать статический метод GameDisk getDisk(…){…} для
получения экземпляра класса (Паттерн static factory)
• В метод передать параметры name, ganre, description
• Внутри метода создать и вернуть экземпляр класса GameDisk

• Аналогично, в классе Game создать статический метод VirtualGame
getVirtualGame (…){…}
• В метод передать параметры name, ganre
• Внутри метода создать и вернуть экземпляр класса VirtualGame.
 */
package prog02_gamepad;

public class Game {
    private final String name;
    private final Ganre ganre;
    private final Type  type;

    private Game(String name, Ganre ganre, Type type) {
        this.name = name;
        this.ganre = ganre;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Ganre getGanre() {
        return ganre;
    }

    public Type getType() {
        return type;
    }

    public static GameDisk getDisk(String name, Ganre ganre, String description) {
        return new GameDisk(name, ganre, description);
    }

    public static VirtualGame getVirtualGame(String name, Ganre ganre, int rating) {
        return new VirtualGame(name, ganre, rating);
    }

    public static class GameDisk {
        private final String description;
        public final Game data;

        private GameDisk(String name, Ganre ganre, String description) {
            this.description = description;
            data = new Game(name, ganre, Type.PHYSICAL);
        }

        public String getDescription() {
            return description;
        }

        public Game getData() {
            return data;
        }

        @Override
        public String toString() {
            return String.format("%-20s, %-10s, %-10s, %s", data.getName(), data.getGanre(), data.getType(), description);
        }
    }

    public static class VirtualGame {
        private final int rating;
        private final Game data;

        public VirtualGame(String name, Ganre ganre, int rating) {
            data = new Game(name, ganre, Type.VIRTUAL);
            this.rating = rating;
        }

        public int getRating() {
            return rating;
        }

        public Game getData() {
            return data;
        }

        @Override
        public String toString() {
            return String.format("%-20s, %-10s, %-10s, рейтинг: %d", data.getName(), data.getGanre(), data.getType(), rating);
        }
    }



    private enum Type {
        VIRTUAL, PHYSICAL;
    }


}
