package shtanko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shtanko.entity.user.User;
import shtanko.service.UserService;
import shtanko.util.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/main")
    public String displayStartedPageUser(Model model) {
        String login = SecurityUtil.getLogin();
        User currentUser = userService.getUser(login);
        model.addAttribute("firstName", currentUser.getFirstName());
        model.addAttribute("lastName", currentUser.getLastName());
        model.addAttribute("listCards", currentUser.getCards());
        model.addAttribute("listAccount", currentUser.getAccounts());
        logger.info("Пользователь зашел на главную страницу");
        return "user/main";
    }

    @GetMapping("/logout")
    public String displayLogoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        logger.info("Выход на страницу авторизации");
        return "redirect:/login?logout";
    }

    @GetMapping("/login")
    public String displayLoginPage() {
        return "user/login";
    }
}
