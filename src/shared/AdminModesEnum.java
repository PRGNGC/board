package shared;

public enum AdminModesEnum {
    UNKNOWN("-1"),
    TOGGLE_USER("1"),
    TOGGLE_AD("2"),
    EXIT("ex");

    private final String value;

    AdminModesEnum(String value) {
        this.value = value;
    }

    public static AdminModesEnum fromString(String value) {
        for (AdminModesEnum mode : values()) {
            if (mode.value.equalsIgnoreCase(value)) {
                return mode;
            }
        }

        return UNKNOWN;
    }
}
