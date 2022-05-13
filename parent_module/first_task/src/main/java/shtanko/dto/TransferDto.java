package shtanko.dto;

import shtanko.dto.groups.AccountToAccount;
import shtanko.dto.groups.CardToAccount;
import shtanko.dto.groups.CardToCard;

import javax.validation.constraints.*;

public class TransferDto {

    @NotNull(message = "Поле не должно быть пустым", groups = {CardToCard.class, CardToAccount.class})
    @Min(value = 1000000000000000L, message = "Поле должно состоять из 16-ти цифр", groups = {CardToCard.class, CardToAccount.class})
    @Max(value = 9999999999999999L, message = "Поле должно состоять из 16-ти цифр", groups = {CardToCard.class, CardToAccount.class})
    private Long withdrawNumberCard;

    @NotNull(message = "Поле не должно быть пустым", groups = CardToCard.class)
    @Min(value = 1000000000000000L, message = "Поле должно состоять из 16-ти цифр", groups = CardToCard.class)
    @Max(value = 9999999999999999L, message = "Поле должно состоять из 16-ти цифр", groups = CardToCard.class)
    private Long putNumberCard;

    @Pattern(regexp = "[0-9]{20}", message = "Номер счёта должен состоять из 20-ти цифр", groups = AccountToAccount.class)
    @NotEmpty(message = "Поле не должно быть пустым", groups = AccountToAccount.class)
    private String withdrawNumberAccount;

    @Pattern(regexp = "[0-9]{20}", message = "Номер счёта должен состоять из 20-ти цифр", groups = {AccountToAccount.class, CardToAccount.class})
    @NotEmpty(message = "Поле не должно быть пустым", groups = {AccountToAccount.class, CardToAccount.class})
    private String putNumberAccount;

    @NotNull(message = "Поле не должно быть пустым", groups = {AccountToAccount.class, CardToAccount.class, CardToCard.class})
    @Positive(message = "Число должно быть положительным", groups = {AccountToAccount.class, CardToAccount.class, CardToCard.class})
    @Min(value = 1, message = "Минимальная сумма перевода 1", groups = {AccountToAccount.class, CardToAccount.class, CardToCard.class})
    @Max(value = 1000000, message = "Максимальная сумма перевода 1000000", groups = {AccountToAccount.class, CardToAccount.class, CardToCard.class})
    private Long sumTransfer;

    public Long getWithdrawNumberCard() {
        return withdrawNumberCard;
    }

    public void setWithdrawNumberCard(Long withdrawNumberCard) {
        this.withdrawNumberCard = withdrawNumberCard;
    }

    public Long getPutNumberCard() {
        return putNumberCard;
    }

    public void setPutNumberCard(Long putNumberCard) {
        this.putNumberCard = putNumberCard;
    }

    public String getWithdrawNumberAccount() {
        return withdrawNumberAccount;
    }

    public void setWithdrawNumberAccount(String withdrawNumberAccount) {
        this.withdrawNumberAccount = withdrawNumberAccount;
    }

    public String getPutNumberAccount() {
        return putNumberAccount;
    }

    public void setPutNumberAccount(String putNumberAccount) {
        this.putNumberAccount = putNumberAccount;
    }

    public Long getSumTransfer() {
        return sumTransfer;
    }

    public void setSumTransfer(Long sumTransfer) {
        this.sumTransfer = sumTransfer;
    }
}
