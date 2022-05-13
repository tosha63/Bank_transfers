package shtanko.entity.card;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "credit_cards")
public class CreditCard extends Card {

    private Long creditLimit;

    private Double rate;

    private Double percentFine;

    public Long getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Long creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getPercentFine() {
        return percentFine;
    }

    public void setPercentFine(Double percentFine) {
        this.percentFine = percentFine;
    }

}
