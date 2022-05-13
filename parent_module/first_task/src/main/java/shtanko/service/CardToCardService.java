package shtanko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shtanko.dto.TransferDto;
import shtanko.entity.card.Card;
import shtanko.entity.transfer.Transfer;
import shtanko.entity.user.User;
import shtanko.exception.NotEnoughMoneyException;
import shtanko.exception.TransferIsLockedException;
import shtanko.frod.FrodMonitor;
import shtanko.model.Transferable;
import shtanko.repository.TransferRepository;
import shtanko.type.TransferStatus;
import shtanko.util.TransferUtil;

@Service("CARD_TO_CARD")
public class CardToCardService extends TransferService implements Transferable {

    private final FrodMonitor frodMonitor;
    private final CardService cardService;
    private final TransferRepository transferRepository;
    private final Double COMMISSION = 0.01;

    @Autowired
    public CardToCardService(FrodMonitor frodMonitor, CardService cardService,
                             TransferRepository transferRepository) {
        this.frodMonitor = frodMonitor;
        this.cardService = cardService;
        this.transferRepository = transferRepository;
    }

    @Override
    @Transactional(noRollbackFor = TransferIsLockedException.class)
    public Transfer createTransfer(TransferDto transferDto) {
        User currentUser = getUser();
        Transfer transfer = getTransfer(transferDto, currentUser);
        transfer.setWithdrawNumberCard(transferDto.getWithdrawNumberCard());
        transfer.setPutNumberCard(transferDto.getPutNumberCard());
        Card withdrawDebitCard = getCard(transferDto, currentUser);
        if (!(withdrawDebitCard.getBalanceCard() >= transferDto.getSumTransfer())) {
            throw new NotEnoughMoneyException();
        }
        if (!frodMonitor.checkFrod(transferDto.getSumTransfer())) {
            transfer.setTransferStatus(TransferStatus.BLOCK);
            if (!TransferUtil.compareBin(transferDto.getWithdrawNumberCard(), transferDto.getPutNumberCard())) {
                transfer.setCommission(COMMISSION);
            }
            transferRepository.save(transfer);
            throw new TransferIsLockedException();
        }
        if (TransferUtil.compareBin(transferDto.getWithdrawNumberCard(), transferDto.getPutNumberCard())) {
            Card putDebitCard = cardService.getCard(transferDto.getPutNumberCard());
            withdrawDebitCard.setBalanceCard(withdrawDebitCard.getBalanceCard() - transferDto.getSumTransfer());
            withdrawDebitCard.account.setBalanceAccount(withdrawDebitCard.account.getBalanceAccount() - transferDto.getSumTransfer());
            putDebitCard.setBalanceCard(putDebitCard.getBalanceCard() + transferDto.getSumTransfer());
            putDebitCard.account.setBalanceAccount(putDebitCard.account.getBalanceAccount() + transferDto.getSumTransfer());
        } else {
            transfer.setCommission(COMMISSION);
            Long sumCommission = (long) (transferDto.getSumTransfer() * COMMISSION);
            Long fullSumWithdraw = sumCommission + transferDto.getSumTransfer();
            if (withdrawDebitCard.getBalanceCard() < fullSumWithdraw) {
                throw new NotEnoughMoneyException();
            } else {
                withdrawDebitCard.setBalanceCard(withdrawDebitCard.getBalanceCard() - fullSumWithdraw);
                withdrawDebitCard.account.setBalanceAccount(withdrawDebitCard.account.getBalanceAccount() - fullSumWithdraw);
            }
        }
        return transferRepository.save(transfer);
    }
}
