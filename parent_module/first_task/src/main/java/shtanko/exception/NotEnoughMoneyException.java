package shtanko.exception;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException() {
        super("Недостаточно средств для осуществления перевода");
    }
}
