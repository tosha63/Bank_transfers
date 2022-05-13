package shtanko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shtanko.entity.card.Card;
import shtanko.entity.transfer.Transfer;
import shtanko.type.TransferStatus;

@Service
public class AdminService {
    private final TransferService transferService;
    private final CardService cardService;

    @Autowired
    public AdminService(TransferService transferService, CardService cardService) {
        this.transferService = transferService;
        this.cardService = cardService;
    }

    public void blockTransfer(Long id) {
        Transfer transfer = transferService.getTransferById(id);
        transfer.setTransferStatus(TransferStatus.ADMIN_BLOCK);
        transferService.saveTransfer(transfer);
    }


    public void unblockTransfer(Long id) {
        Transfer transfer = transferService.getTransferById(id);
        transfer.setTransferStatus(TransferStatus.UNBLOCK);
        transferService.saveTransfer(transfer);
        Card withdrawCard = cardService.getCard(transfer.getWithdrawNumberCard());
        Card putCard = cardService.getCard(transfer.getPutNumberCard());
        if (transfer.getCommission() != null) {
            Long fullCommission = (long) (transfer.getCommission() * transfer.getSumTransfer());
            Long fullSumWithdraw = transfer.getSumTransfer() + fullCommission;
            withdrawCard.setBalanceCard(withdrawCard.getBalanceCard() - fullSumWithdraw);
            withdrawCard.account.setBalanceAccount(withdrawCard.account.getBalanceAccount() - fullSumWithdraw);
        } else {
            withdrawCard.setBalanceCard(withdrawCard.getBalanceCard() - transfer.getSumTransfer());
            withdrawCard.account.setBalanceAccount(withdrawCard.account.getBalanceAccount() - transfer.getSumTransfer());
        }
        if (putCard != null) {
            putCard.setBalanceCard(putCard.getBalanceCard() + transfer.getSumTransfer());
            putCard.account.setBalanceAccount(putCard.account.getBalanceAccount() + transfer.getSumTransfer());
            cardService.saveCard(putCard);
        }
        cardService.saveCard(withdrawCard);
    }
}
