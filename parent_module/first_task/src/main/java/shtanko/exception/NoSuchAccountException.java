package shtanko.exception;

public class NoSuchAccountException extends RuntimeException {
    public NoSuchAccountException(String numberAccount) {
        super("Введен неверный номер счёта " + numberAccount + "! Данного счёта у вас нет!");
    }
}
