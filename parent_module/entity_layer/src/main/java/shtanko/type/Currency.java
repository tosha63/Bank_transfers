package shtanko.type;

public enum Currency {
    RUB("Российский рубль"),
    USD("Доллар"),
    EUR("Евро");

    private final String currencyName;

    Currency(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }
}
