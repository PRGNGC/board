package shared;

public enum InitialAppModesEnum {
    UNKNOWN("-1"),
    AUTH("1"),
    REGISTER("2"),
    EXIT("ex");

    private final String value;

    InitialAppModesEnum(String value) {
        this.value = value;
    }

    public static InitialAppModesEnum fromString(String value) {
        for (InitialAppModesEnum mode : values()) {
            if (mode.value.equalsIgnoreCase(value)) {
                return mode;
            }
        }

        return UNKNOWN;
    }
}
