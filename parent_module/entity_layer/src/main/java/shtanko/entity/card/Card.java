package shtanko.entity.card;

import shtanko.entity.account.Account;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cards")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Long numberCard;

    public Long balanceCard;

    public LocalDate validity;

    public String cvv;

    public String password;

    @OneToOne
    public Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(Long numberCard) {
        this.numberCard = numberCard;
    }

    public Long getBalanceCard() {
        return balanceCard;
    }

    public void setBalanceCard(Long balanceCard) {
        this.balanceCard = balanceCard;
    }

    public LocalDate getValidity() {
        return validity;
    }

    public void setValidity(LocalDate validity) {
        this.validity = validity;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
