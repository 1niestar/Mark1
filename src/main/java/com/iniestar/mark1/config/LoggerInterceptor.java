package com.iniestar.mark1.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Enumeration;

@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String[] urlPatterns = new String[] {"/", "/error", "/fonts", ".js", ".css", ".ico"};
        String uri = request.getRequestURI();

        if(Arrays.stream(urlPatterns).anyMatch(uri::contains)) {
         // do nothing
        } else {
            log.info("=============================================================");
            log.info("Accept url: " + uri);
            log.info("Params: ");
            Enumeration parameterNames = request.getParameterNames();
            while(parameterNames.hasMoreElements()) {
                String name = (String) parameterNames.nextElement();
                log.info("{}:{}" , name, request.getParameter(name));
            }
            log.info("=============================================================");
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
