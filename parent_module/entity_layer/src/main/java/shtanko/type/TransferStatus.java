package shtanko.type;

public enum TransferStatus {
    BLOCK("Заблокирован"),
    UNBLOCK("Разблокирован"),
    ADMIN_BLOCK("Заблокирован администратором");

    private final String name;

    TransferStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
