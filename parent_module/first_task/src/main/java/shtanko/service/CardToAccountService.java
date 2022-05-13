package shtanko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shtanko.dto.TransferDto;
import shtanko.entity.account.Account;
import shtanko.entity.card.Card;
import shtanko.entity.transfer.Transfer;
import shtanko.entity.user.User;
import shtanko.exception.NotEnoughMoneyException;
import shtanko.model.Transferable;
import shtanko.repository.AccountRepository;
import shtanko.repository.TransferRepository;

@Service("CARD_TO_ACCOUNT")
public class CardToAccountService extends TransferService implements Transferable {

    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;

    @Autowired
    public CardToAccountService(AccountRepository accountRepository, TransferRepository transferRepository) {
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
    }

    @Override
    @Transactional
    public Transfer createTransfer(TransferDto transferDto) {
        User currentUser = getUser();
        Transfer transfer = getTransfer(transferDto, currentUser);
        transfer.setPutNumberAccount(transferDto.getPutNumberAccount());
        transfer.setWithdrawNumberCard(transferDto.getWithdrawNumberCard());
        Card withdrawDebitCard = getCard(transferDto, currentUser);
        if (!(withdrawDebitCard.getBalanceCard() >= transferDto.getSumTransfer())) {
            throw new NotEnoughMoneyException();
        }
        Account putAccount = accountRepository.findByNumberAccount(transferDto.getPutNumberAccount());
        withdrawDebitCard.setBalanceCard(withdrawDebitCard.getBalanceCard() - transferDto.getSumTransfer());
        withdrawDebitCard.account.setBalanceAccount(withdrawDebitCard.account.getBalanceAccount() - transferDto.getSumTransfer());
        if (putAccount != null) {
            putAccount.setBalanceAccount(putAccount.getBalanceAccount() + transferDto.getSumTransfer());
        }
        return transferRepository.save(transfer);
    }
}
