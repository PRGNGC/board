package shared;

public enum UserStateEnum {
    ACTIVE("Активный"),
    IDLE("Не активный"),
    NULL("null");

    private final String value;

    public String getText() {
        return this.value;
    }

    UserStateEnum(String value) {
        this.value = value;
    }

    public static UserStateEnum fromString(String value) {
        for (UserStateEnum mode : values()) {
            if (mode.value.equalsIgnoreCase(value)) {
                return mode;
            }
        }

        return NULL;
    }
}
