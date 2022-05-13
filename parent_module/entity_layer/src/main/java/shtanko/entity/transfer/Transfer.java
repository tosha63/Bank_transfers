package shtanko.entity.transfer;

import shtanko.entity.user.User;
import shtanko.type.Currency;
import shtanko.type.TransferStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long withdrawNumberCard;

    private Long putNumberCard;

    private String withdrawNumberAccount;

    private String putNumberAccount;

    private Double commission;

    private Long sumTransfer;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private LocalDateTime localDateTime;

    @Enumerated(EnumType.STRING)
    private TransferStatus transferStatus;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Long getSumTransfer() {
        return sumTransfer;
    }

    public void setSumTransfer(Long sumTransfer) {
        this.sumTransfer = sumTransfer;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TransferStatus getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(TransferStatus transferStatus) {
        this.transferStatus = transferStatus;
    }
}
