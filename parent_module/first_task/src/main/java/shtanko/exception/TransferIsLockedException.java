package shtanko.exception;

public class TransferIsLockedException extends RuntimeException {
    public TransferIsLockedException() {
        super("Перевод заблокирован! Превышен лимит суммы перевода");
    }
}
