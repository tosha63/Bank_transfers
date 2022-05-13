package shtanko.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static String getLogin() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
