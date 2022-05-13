package shtanko.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import shtanko.entity.account.Account;
import shtanko.entity.transfer.Transfer;
import shtanko.exception.NoSuchAccountException;
import shtanko.exception.NotEnoughMoneyException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountToAccountServiceTest extends TransferServiceTest {

    @InjectMocks
    private AccountToAccountService accountService;

    @Test
    void createTransferThrowNoEnoughMoneyException() {
        getUserTest();
        Account withDrawAccount = new Account();
        withDrawAccount.setNumberAccount("20000000000000001111");
        withDrawAccount.setBalanceAccount(1000L);
        Account putAccount = new Account();
        putAccount.setNumberAccount("20000000000000000000");
        List<Account> accounts = new ArrayList<>();
        Collections.addAll(accounts, withDrawAccount, putAccount);
        when(transferDto.getPutNumberAccount()).thenReturn("20000000000000000000");
        when(transferDto.getWithdrawNumberAccount()).thenReturn("20000000000000001111");
        when(transferDto.getSumTransfer()).thenReturn(5000L);
        when(user.getAccounts()).thenReturn(accounts);
        assertThrows(NotEnoughMoneyException.class, () -> accountService.createTransfer(transferDto));
    }

    @Test
    void createTransferThrowNoSuchAccountException() {
        getUserTest();
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account());
        when(user.getAccounts()).thenReturn(accounts);
        when(transferDto.getPutNumberAccount()).thenReturn("20000000000000000000");
        when(transferDto.getWithdrawNumberAccount()).thenReturn("20000000000000001111");
        assertThrows(NoSuchAccountException.class, () -> accountService.createTransfer(transferDto));
    }

    @Test
    void createTransfer() {
        getUserTest();
        Account withDrawAccount = new Account();
        withDrawAccount.setNumberAccount("20000000000000001111");
        withDrawAccount.setBalanceAccount(50000L);
        Account putAccount = new Account();
        putAccount.setNumberAccount("20000000000000000000");
        putAccount.setBalanceAccount(1000L);
        List<Account> accounts = new ArrayList<>();
        Collections.addAll(accounts, withDrawAccount, putAccount);
        when(transferDto.getPutNumberAccount()).thenReturn("20000000000000000000");
        when(transferDto.getWithdrawNumberAccount()).thenReturn("20000000000000001111");
        when(transferDto.getSumTransfer()).thenReturn(5000L);
        when(user.getAccounts()).thenReturn(accounts);
        Transfer transfer = accountService.createTransfer(transferDto);
        assertEquals(45000L, withDrawAccount.getBalanceAccount());
        assertEquals(6000L, putAccount.getBalanceAccount());
    }
}