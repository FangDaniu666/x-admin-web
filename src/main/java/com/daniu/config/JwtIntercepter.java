package com.daniu.config;

import com.daniu.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
@Slf4j
public class JwtIntercepter implements HandlerInterceptor {
    @Autowired
    private JwtUtils jwt;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-Token");
        if (token != null) {
            try {
                jwt.parseToken(token);
                log.info("Token is valid");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.sendError(401, "Unauthorized");
        return false;
    }
}
