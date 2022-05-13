package shtanko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shtanko.dto.TransferDto;
import shtanko.entity.account.Account;
import shtanko.entity.transfer.Transfer;
import shtanko.entity.user.User;
import shtanko.exception.NoSuchAccountException;
import shtanko.exception.NotEnoughMoneyException;
import shtanko.model.Transferable;
import shtanko.repository.TransferRepository;

import java.util.List;

@Service("ACCOUNT_TO_ACCOUNT")
public class AccountToAccountService extends TransferService implements Transferable {

    private final TransferRepository transferRepository;

    @Autowired
    public AccountToAccountService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    @Transactional
    public Transfer createTransfer(TransferDto transferDto) {
        User currentUser = getUser();
        Transfer transfer = getTransfer(transferDto, currentUser);
        transfer.setPutNumberAccount(transferDto.getPutNumberAccount());
        transfer.setWithdrawNumberAccount(transferDto.getWithdrawNumberAccount());
        Account withDrawAccount = new Account();
        Account putAccount = new Account();
        List<Account> accounts = currentUser.getAccounts();
        for (Account account : accounts) {
            if (transferDto.getWithdrawNumberAccount().equals(account.getNumberAccount())) {
                withDrawAccount = account;
            } else if (transferDto.getPutNumberAccount().equals(account.getNumberAccount())) {
                putAccount = account;
            } else {
                throw new NoSuchAccountException(withDrawAccount.getNumberAccount() == null ?
                        transferDto.getWithdrawNumberAccount() : transferDto.getPutNumberAccount());
            }
        }
        if (!(withDrawAccount.getBalanceAccount() >= transferDto.getSumTransfer())) {
            throw new NotEnoughMoneyException();
        }
        withDrawAccount.setBalanceAccount(withDrawAccount.getBalanceAccount() - transferDto.getSumTransfer());
        putAccount.setBalanceAccount(putAccount.getBalanceAccount() + transferDto.getSumTransfer());
        return transferRepository.save(transfer);
    }
}
