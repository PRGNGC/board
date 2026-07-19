package shared;

public enum TextPriceEnum {
    FREE("Бесплатно"),
    DISCUSS("Договорная");

    private final String value;

    public String getText() {
        return this.value;
    }

    TextPriceEnum(String value) {
        this.value = value;
    }

    public static TextPriceEnum fromString(String value) {
        for (TextPriceEnum mode : values()) {
            if (mode.value.equalsIgnoreCase(value)) {
                return mode;
            }
        }

        return DISCUSS;
    }
}
