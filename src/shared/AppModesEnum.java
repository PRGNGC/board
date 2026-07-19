package shared;

public enum AppModesEnum {
<<<<<<< HEAD
=======
    UNKNOWN("-1"),
>>>>>>> b38ca39b0a4dc419a51bf4e3eb37c8255d1f5894
    CREATE("1"),
    EDIT("2"),
    TOGGLE("3"),
    SEARCH("4"),
    LIST_OUTPUT("5"),
<<<<<<< HEAD
    EXIT("ex"),
    UNKNOWN("0");

    private final String value;

    public String getText() {
        return this.value;
    }

=======
    EXIT("ex");

    private final String value;

>>>>>>> b38ca39b0a4dc419a51bf4e3eb37c8255d1f5894
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
<<<<<<< HEAD
=======

>>>>>>> b38ca39b0a4dc419a51bf4e3eb37c8255d1f5894
}
