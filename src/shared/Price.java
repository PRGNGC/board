package shared;

public class Price {
    private final Double numericValue;
    private final TextPriceEnum textValue;

    // Приватный конструктор, чтобы контролировать создание
    public Price(Double numericValue, TextPriceEnum textValue) {
        this.numericValue = numericValue;
        this.textValue = textValue;
    }

    // Фабричные методы для удобного создания
    public static Price ofNumber(Double value) {
        return new Price(value, null);
    }

    public static Price ofText(TextPriceEnum text) {
        return new Price(null, text);
    }

    // Методы проверки и получения
    public boolean isNumeric() {
        return numericValue != null;
    }

    public boolean isText() {
        return textValue != null;
    }

    public Double getNumericValue() {
        return numericValue;
    }

    public TextPriceEnum getStringValue() {
        return textValue;
    }
}
