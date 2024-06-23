package com.spring.todo.global.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String sessionEmail = (session != null) ? (String) session.getAttribute("userEmail") : null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userEmail".equals(cookie.getName()) && cookie.getValue() != null) {
                    String cookieEmail = cookie.getValue();
                    if (sessionEmail != null && sessionEmail.equals(cookieEmail)) {
                        return true;
                    }
                }
            }
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
        return false;
    }
}