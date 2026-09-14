package com.javier.movier.utils;

import com.javier.movier.user.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UtilService {

    public User currentUser() {
        return (User) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
    }

    public String getOrCreateVisitorId(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("visitor_id")) {
                    return c.getValue();
                }
            }
        }

        String newId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("visitor_id", newId);
        cookie.setPath("/");
        cookie.setMaxAge(365 * 24 * 60 * 60); // 1 yil
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return newId;
    }
}
