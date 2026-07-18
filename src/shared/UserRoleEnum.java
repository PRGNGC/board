package shared;

public enum UserRoleEnum {
    ADMIN("�����"),
    USER("������������"),
    NULL("null");

    private final String value;

    public String getText() {
        return this.value;
    }

    UserRoleEnum(String value) {
        this.value = value;
    }

    public static UserRoleEnum fromString(String value) {
        for (UserRoleEnum mode : values()) {
            if (mode.value.equalsIgnoreCase(value)) {
                return mode;
            }
        }

        return NULL;
    }
}
