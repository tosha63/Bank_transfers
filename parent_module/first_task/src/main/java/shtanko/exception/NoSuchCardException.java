package shtanko.exception;

public class NoSuchCardException extends RuntimeException {
    public NoSuchCardException(String numberCard) {
        super("Введен неверный номер карты " + numberCard + "! Данной карты у вас нет!");
    }
}
