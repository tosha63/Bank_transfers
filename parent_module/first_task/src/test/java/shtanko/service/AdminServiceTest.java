package shtanko.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shtanko.entity.account.Account;
import shtanko.entity.card.Card;
import shtanko.entity.card.DebitCard;
import shtanko.entity.transfer.Transfer;
import shtanko.type.TransferStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private TransferService transferService;

    @Mock
    private CardService cardService;

    @Captor
    private ArgumentCaptor<Transfer> argumentCaptor;

    @Test
    void blockTransferTest() {
        Transfer transfer = new Transfer();
        transfer.setTransferStatus(TransferStatus.BLOCK);
        when(transferService.getTransferById(anyLong())).thenReturn(transfer);
        adminService.blockTransfer(anyLong());
        verify(transferService).saveTransfer(argumentCaptor.capture());
        Transfer transferCaptor = argumentCaptor.getValue();
        assertEquals(transferCaptor.getTransferStatus(), transfer.getTransferStatus());
    }

    @Test
    void unblockTransferWithCommissionNull() {
        Transfer transfer = new Transfer();
        transfer.setTransferStatus(TransferStatus.BLOCK);
        transfer.setWithdrawNumberCard(1234567890000000L);
        transfer.setPutNumberCard(1234567890000011L);
        transfer.setSumTransfer(1000L);
        Card withdrawCard = new DebitCard();
        withdrawCard.setNumberCard(1234567890000000L);
        withdrawCard.setBalanceCard(20000L);
        Account withdrawAccount = new Account();
        withdrawAccount.setBalanceAccount(20000L);
        withdrawCard.setAccount(withdrawAccount);
        Card putCard = new DebitCard();
        putCard.setNumberCard(1234567890000011L);
        putCard.setBalanceCard(2000L);
        Account putAccount = new Account();
        putAccount.setBalanceAccount(2000L);
        putCard.setAccount(putAccount);
        when(transferService.getTransferById(anyLong())).thenReturn(transfer);
        when(cardService.getCard(transfer.getWithdrawNumberCard())).thenReturn(withdrawCard);
        when(cardService.getCard(transfer.getPutNumberCard())).thenReturn(putCard);
        adminService.unblockTransfer(anyLong());
        verify(transferService).saveTransfer(argumentCaptor.capture());
        Transfer transferCaptor = argumentCaptor.getValue();
        assertEquals(19000L, withdrawCard.getBalanceCard());
        assertEquals(19000L, withdrawCard.getAccount().getBalanceAccount());
        assertEquals(3000L, putCard.getBalanceCard());
        assertEquals(transferCaptor.getTransferStatus(), transfer.getTransferStatus());
    }

    @Test
    void unblockTransferWithCommissionNotNull() {
        Transfer transfer = new Transfer();
        transfer.setTransferStatus(TransferStatus.BLOCK);
        transfer.setWithdrawNumberCard(1234567890000000L);
        transfer.setPutNumberCard(1234567890000011L);
        transfer.setSumTransfer(1000L);
        transfer.setCommission(0.1);
        Card withdrawCard = new DebitCard();
        withdrawCard.setNumberCard(1234567890000000L);
        withdrawCard.setBalanceCard(20000L);
        Account withdrawAccount = new Account();
        withdrawAccount.setBalanceAccount(20000L);
        withdrawCard.setAccount(withdrawAccount);
        Card putCard = new DebitCard();
        putCard.setNumberCard(1234567890000011L);
        putCard.setBalanceCard(2000L);
        Account putAccount = new Account();
        putAccount.setBalanceAccount(2000L);
        putCard.setAccount(putAccount);
        when(cardService.getCard(transfer.getWithdrawNumberCard())).thenReturn(withdrawCard);
        when(cardService.getCard(transfer.getPutNumberCard())).thenReturn(putCard);
        when(transferService.getTransferById(anyLong())).thenReturn(transfer);
        adminService.unblockTransfer(anyLong());
        verify(transferService).saveTransfer(argumentCaptor.capture());
        Transfer transferCaptor = argumentCaptor.getValue();
        assertEquals(18900L, withdrawCard.getBalanceCard());
        assertEquals(18900L, withdrawCard.getAccount().getBalanceAccount());
        assertEquals(transferCaptor.getTransferStatus(), transfer.getTransferStatus());
    }
}