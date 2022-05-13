package shtanko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shtanko.dto.TransferDto;
import shtanko.dto.groups.AccountToAccount;
import shtanko.dto.groups.CardToAccount;
import shtanko.dto.groups.CardToCard;
import shtanko.entity.transfer.Transfer;
import shtanko.entity.user.User;
import shtanko.exception.NoSuchAccountException;
import shtanko.exception.NoSuchCardException;
import shtanko.exception.NotEnoughMoneyException;
import shtanko.exception.TransferIsLockedException;
import shtanko.model.TransferType;
import shtanko.model.Transferable;
import shtanko.service.TransferService;
import shtanko.service.UserService;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@Validated
@Controller
public class TransferController {
    private final UserService userService;
    private final Map<String, Transferable> transfers;

    private final Logger logger = LoggerFactory.getLogger(TransferController.class);

    @Autowired
    public TransferController(UserService userService, Map<String, Transferable> transfers) {
        this.userService = userService;
        this.transfers = transfers;
    }

    @GetMapping("/user/transferHistory")
    public String displayTransferHistoryUser(Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(login);
        model.addAttribute("history", user.getTransfers()
                .stream()
                .sorted(Comparator.comparing(Transfer::getLocalDateTime).reversed())
                .collect(Collectors.toList()));
        logger.info("Пользователь перешел на страницу истории переводов");
        return "transfers/transferHistory";
    }

    @GetMapping("/user/transfer")
    public String changeTransfer() {
        logger.info("Пользователь перешел на страницу выбора перевода");
        return "transfers/transfer";

    }

    @GetMapping("/user/transfer/card")
    public String displayTransferCardToCard(Model model) {
        model.addAttribute("transferDto", new TransferDto());
        logger.info("Пользователь перешел на страницу перевода с карты на карту");
        return "transfers/transferCard";
    }

    @PostMapping("/user/transfer/card")
    public String handleTransferCardToCard(@Validated(CardToCard.class) TransferDto transferDto,
                                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "transfers/transferCard";
        }
        try {
            Transferable transferable = transfers.get(TransferType.CARD_TO_CARD.name());
            transferable.createTransfer(transferDto);
            logger.info("Пользователь осуществил перевод");
        } catch (NotEnoughMoneyException | TransferIsLockedException | NoSuchCardException e) {
            model.addAttribute("error", e.getMessage());
            logger.info("При выполнении перевода возникла ошибка " + e.getMessage());
            return "transfers/transferCard";
        }
        return "transfers/transferSuccess";
    }

    @GetMapping("/user/transfer/account")
    public String displayTransferAccountToAccount(Model model) {
        model.addAttribute("transferDto", new TransferDto());
        logger.info("Пользователь перешел на страницу перевода со счёта на счёт");
        return "transfers/transferAccount";
    }

    @PostMapping("/user/transfer/account")
    public String handleTransferAccountToAccount(@Validated(AccountToAccount.class) TransferDto transferDto,
                                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "transfers/transferAccount";
        }
        try {
            Transferable transferable = transfers.get(TransferType.ACCOUNT_TO_ACCOUNT.name());
            transferable.createTransfer(transferDto);
            logger.info("Пользователь осуществил перевод");
        } catch (NotEnoughMoneyException | NoSuchAccountException e) {
            model.addAttribute("error", e.getMessage());
            logger.info("При выполнении перевода возникла ошибка " + e.getMessage());
            return "transfers/transferAccount";
        }
        return "transfers/transferSuccess";
    }

    @GetMapping("/user/transfer/cardToAcc")
    public String displayTransferCardToAccount(Model model) {
        model.addAttribute("transferDto", new TransferDto());
        logger.info("Пользователь перешел на страницу перевода с карты на счёт");
        return "transfers/transferCardToAcc";
    }

    @PostMapping("/user/transfer/cardToAcc")
    public String handleTransferCardToAccount(@Validated(CardToAccount.class) TransferDto transferDto,
                                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "transfers/transferCardToAcc";
        }

        try {
            Transferable transferable = transfers.get(TransferType.CARD_TO_ACCOUNT.name());
            transferable.createTransfer(transferDto);
            logger.info("Пользователь осуществил перевод");
        } catch (NoSuchCardException | NotEnoughMoneyException e) {
            model.addAttribute("error", e.getMessage());
            logger.info("При выполнении перевода возникла ошибка " + e.getMessage());
            return "transfers/transferCardToAcc";
        }
        return "transfers/transferSuccess";
    }
}
