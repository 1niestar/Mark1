package com.iniestar.mark1.config.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();
        log.info("[Login success...]");

        if(null != session) {
            log.info("Session ID [{}]", session.getId());
            log.info("User ID [{}]", authentication.getName());
            request.setAttribute("userName", authentication.getName());
            request.getRequestDispatcher("/index").forward(request, response);
        }
    }
}
