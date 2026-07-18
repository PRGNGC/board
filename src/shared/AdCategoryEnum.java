package shared;

public enum AdCategoryEnum {
    CLOTHES("Одежда"),
    TECHNICS("Техника"),
    BOOKS("Книги"),
    UNKNOWN("Другое");

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
