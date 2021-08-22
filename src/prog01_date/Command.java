package prog01_date;

public enum Command {
    HELP("?","помощь" ),
    DAY_OF_WEEK("DW","день недели"),
    DAYS_IN_YEAR("DY","количество дней в году"),
    DAYS_IN_MONTH("DM","количество дней в месяце"),
    NUM_DAY_OF_YEAR("NDY","порядковый номер дня в году"),
    DATE_BY_DAY("DBD","дата по порядковому номеру дня в году"),
    DAY_PROGRAMMER("DPROG","день программиста"),
    DAYS_BETWEEN("BET","количество дней между датами"),
    END("END","выход"),
    ;

    private final String key;
    private final String nameRus;

    Command(String key, String nameRus) {
        this.key = key;
        this.nameRus = nameRus;

    }

    public String getKey() {
        return key;
    }

    public String getNameRus() {
        return nameRus;
    }

}
