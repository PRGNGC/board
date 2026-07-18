package shared;

public enum AdCategoryEnum {
    CLOTHES("������"),
    TECHNICS("�������"),
    BOOKS("�����"),
    UNKNOWN("������");

    private final String value;

    public String getText() {
        return this.value;
    }

    AdCategoryEnum(String value) {
        this.value = value;
    }

    public static AdCategoryEnum fromString(String value) {
        for (AdCategoryEnum mode : values()) {
            if (mode.value.equalsIgnoreCase(value)) {
                return mode;
            }
        }

        return UNKNOWN;
    }
}
