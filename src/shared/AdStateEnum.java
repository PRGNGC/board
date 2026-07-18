package shared;

public enum AdStateEnum {
    ACTIVE("��������"),
    IDLE("�� ��������"),
    NULL("null");

    private final String value;

    public String getText() {
        return this.value;
    }

    AdStateEnum(String value) {
        this.value = value;
    }

    public static AdStateEnum fromString(String value) {
        for (AdStateEnum mode : values()) {
            if (mode.value.equalsIgnoreCase(value)) {
                return mode;
            }
        }

        return NULL;
    }
}
