package shtanko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shtanko.service.AdminService;
import shtanko.service.TransferService;

@Controller
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final AdminService adminService;
    private final TransferService transferService;

    @Autowired
    public AdminController(AdminService adminService, TransferService transferService) {
        this.adminService = adminService;
        this.transferService = transferService;
    }

    @GetMapping("/admin/transferHistoryUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String displayTransferHistoryUsers(Model model) {
        model.addAttribute("historyTransferUsers", transferService.getAll());
        logger.info("Админ зашел на страницу переводов пользовтелей");
        return "transfers/adminTransfers";
    }

    @PostMapping("/admin/transferHistoryUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String handleTransferHistoryUsers(@RequestParam("status") String status,
                                             @RequestParam("idTransfer") Long id, Model model) {
        if (status.equals("lock")) {
            adminService.blockTransfer(id);
            model.addAttribute("historyTransferUsers", transferService.getAll());
            logger.info("Админ заблокировал перевод");
        } else if (status.equals("unlock")) {
            adminService.unblockTransfer(id);
            model.addAttribute("historyTransferUsers", transferService.getAll());
            logger.info("Админ разблокировал перевод");
        }
        return "transfers/adminTransfers";
    }
}
