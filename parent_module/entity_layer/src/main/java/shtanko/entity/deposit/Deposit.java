package shtanko.entity.deposit;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "deposits")
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tariffName;

    private Long balanceDeposit;

    private LocalDateTime validity;

    private Double percentRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    public Long getBalanceDeposit() {
        return balanceDeposit;
    }

    public void setBalanceDeposit(Long balanceDeposit) {
        this.balanceDeposit = balanceDeposit;
    }

    public LocalDateTime getValidity() {
        return validity;
    }

    public void setValidity(LocalDateTime validity) {
        this.validity = validity;
    }

    public Double getPercentRate() {
        return percentRate;
    }

    public void setPercentRate(Double percentRate) {
        this.percentRate = percentRate;
    }
}
