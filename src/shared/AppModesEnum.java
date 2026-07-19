package shared;

public enum AppModesEnum {
    UNKNOWN("-1"),
    CREATE("1"),
    EDIT("2"),
    TOGGLE("3"),
    SEARCH("4"),
    LIST_OUTPUT("5"),
    EXIT("ex");


    private final String value;

    public String getText() {
        return this.value;
    }

    AppModesEnum(String value) {
        this.value = value;
    }

    public static AppModesEnum fromString(String value) {
        for (AppModesEnum mode : values()) {
            if (mode.value.equalsIgnoreCase(value)) {
                return mode;
            }
        }

        return UNKNOWN;
    }
}
