package shtanko.frod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import shtanko.entity.transfer.Transfer;

@Configuration
public class FrodMonitor {
    private Transfer transfer;

    private final int LIMIT_TRANSFER = 50000;

    @Bean
    public Transfer getTransfer() {
        return new Transfer();
    }

    @Autowired
    @Lazy
    public FrodMonitor(Transfer transfer) {
        this.transfer = transfer;
    }

    public boolean checkFrod(Long sumTransfer) {
        return sumTransfer <= LIMIT_TRANSFER;
    }
}
