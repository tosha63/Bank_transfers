package shtanko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shtanko.dto.TransferDto;
import shtanko.entity.card.Card;
import shtanko.entity.transfer.Transfer;
import shtanko.entity.user.User;
import shtanko.exception.NoSuchCardException;
import shtanko.repository.TransferRepository;
import shtanko.repository.UserRepository;
import shtanko.type.TransferStatus;
import shtanko.util.SecurityUtil;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferService {
    private TransferRepository transferRepository;
    private UserRepository userRepository;

    @Autowired
    public void setTransferRepository(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Transfer> getAll() {
        return transferRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Transfer::getLocalDateTime).reversed())
                .collect(Collectors.toList());
    }

    public Transfer getTransferById(Long id) {
        return transferRepository.findById(id).orElse(null);
    }

    public void saveTransfer(Transfer transfer) {
        transferRepository.save(transfer);
    }

    public User getUser() {
        return userRepository.findByLogin(SecurityUtil.getLogin());
    }

    public Transfer getTransfer(TransferDto transferDto, User currentUser) {
        Transfer transfer = new Transfer();
        transfer.setSumTransfer(transferDto.getSumTransfer());
        transfer.setLocalDateTime(LocalDateTime.now());
        transfer.setUser(currentUser);
        transfer.setTransferStatus(TransferStatus.UNBLOCK);
        return transfer;
    }

    public Card getCard(TransferDto transferDto, User currentUser) {
        List<Card> cards = currentUser.getCards();
        for (Card card : cards) {
            if (card.getNumberCard().equals(transferDto.getWithdrawNumberCard())) {
                return card;
            }
        }
        throw new NoSuchCardException(transferDto.getWithdrawNumberCard().toString());
    }
}
