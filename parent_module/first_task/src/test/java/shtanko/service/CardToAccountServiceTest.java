package shtanko.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shtanko.entity.account.Account;
import shtanko.exception.NotEnoughMoneyException;
import shtanko.repository.AccountRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardToAccountServiceTest extends TransferServiceTest {

    @InjectMocks
    private CardToAccountService cardToAccountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void createTransferThrowNotEnoughMoney() {
        getUserTest();
        getCardTest();
        when(transferDto.getSumTransfer()).thenReturn(20000L);
        user.getCards().get(0).setBalanceCard(1000L);
        assertThrows(NotEnoughMoneyException.class, () -> cardToAccountService.createTransfer(transferDto));
    }

    @Test
    void createTransferSuccess() {
        getUserTest();
        getCardTest();
        when(transferDto.getSumTransfer()).thenReturn(20000L);
        user.getCards().get(0).setBalanceCard(50000L);
        Account withdrawAccount = new Account();
        withdrawAccount.setBalanceAccount(50000L);
        user.getCards().get(0).setAccount(withdrawAccount);
        when(transferDto.getPutNumberAccount()).thenReturn("200000000000000000000");
        Account account = new Account();
        account.setBalanceAccount(1000L);
        when(accountRepository.findByNumberAccount("200000000000000000000")).thenReturn(account);
        cardToAccountService.createTransfer(transferDto);
        assertEquals(30000L, user.getCards().get(0).getBalanceCard());
        assertEquals(30000L, user.getCards().get(0).getAccount().getBalanceAccount());
        assertEquals(21000L, account.getBalanceAccount());
    }
}