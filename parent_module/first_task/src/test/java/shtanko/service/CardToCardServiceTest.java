package shtanko.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shtanko.entity.account.Account;
import shtanko.entity.card.Card;
import shtanko.entity.card.DebitCard;
import shtanko.exception.NotEnoughMoneyException;
import shtanko.exception.TransferIsLockedException;
import shtanko.frod.FrodMonitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardToCardServiceTest extends TransferServiceTest {

    @InjectMocks
    private CardToCardService cardToCardService;

    @Mock
    private FrodMonitor frodMonitor;

    @Mock
    private CardService cardService;

    @Test
    void createTransferThrowNotEnoughMoneyException() {
        getUserTest();
        getCardTest();
        user.getCards().get(0).setBalanceCard(1000L);
        when(transferDto.getSumTransfer()).thenReturn(5000L);
        assertThrows(NotEnoughMoneyException.class, () -> cardToCardService.createTransfer(transferDto));
    }

    @Test
    void createTransferThrowTransferIsLockedException() {
        getUserTest();
        getCardTest();
        user.getCards().get(0).setBalanceCard(100000L);
        when(transferDto.getSumTransfer()).thenReturn(60000L);
        when(transferDto.getPutNumberCard()).thenReturn(1234567890000001L);
        assertThrows(TransferIsLockedException.class, () -> cardToCardService.createTransfer(transferDto));
    }

    @Test
    void createTransferYourBank() {
        getUserTest();
        getCardTest();
        user.getCards().get(0).setBalanceCard(21000L);
        Account withdrawAccount = new Account();
        withdrawAccount.setBalanceAccount(21000L);
        user.getCards().get(0).setAccount(withdrawAccount);
        when(transferDto.getSumTransfer()).thenReturn(20000L);
        when(frodMonitor.checkFrod(20000L)).thenReturn(true);
        when(transferDto.getWithdrawNumberCard()).thenReturn(1234567890000000L);
        when(transferDto.getPutNumberCard()).thenReturn(1234567890000001L);
        Card putDebitCard = new DebitCard();
        putDebitCard.setBalanceCard(1000L);
        Account putAccount = new Account();
        putAccount.setBalanceAccount(1000L);
        putDebitCard.setAccount(putAccount);
        when(cardService.getCard(1234567890000001L)).thenReturn(putDebitCard);
        cardToCardService.createTransfer(transferDto);
        assertEquals(21000L, putDebitCard.getBalanceCard());
        assertEquals(21000L, putDebitCard.getAccount().getBalanceAccount());
        assertEquals(1000L, user.getCards().get(0).getBalanceCard());
        assertEquals(1000L, user.getCards().get(0).getAccount().getBalanceAccount());
    }

    @Test
    void createTransferAnotherBank() {
        getUserTest();
        getCardTest();
        user.getCards().get(0).setBalanceCard(21000L);
        Account withdrawAccount = new Account();
        withdrawAccount.setBalanceAccount(21000L);
        user.getCards().get(0).setAccount(withdrawAccount);
        when(transferDto.getSumTransfer()).thenReturn(20000L);
        when(frodMonitor.checkFrod(20000L)).thenReturn(true);
        when(transferDto.getWithdrawNumberCard()).thenReturn(1234567890000000L);
        when(transferDto.getPutNumberCard()).thenReturn(5555555550000001L);
        cardToCardService.createTransfer(transferDto);
        assertEquals(800, user.getCards().get(0).getBalanceCard());
        assertEquals(800, user.getCards().get(0).getAccount().getBalanceAccount());
    }
}