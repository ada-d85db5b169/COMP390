package com.yyz.comp390.jwt;

import com.yyz.comp390.context.BaseContext;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private JwtProperties jwtProperties;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        log.info("\nJWT Interceptor preHandle\n");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // Get token
        String token = request.getHeader(jwtProperties.getTokenName());
        if(token.startsWith("Bearer")) {
            token = token.substring(7);
        }

        // Set claims
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            Long userId = Long.valueOf(claims.get("id").toString());
            log.info("User id: {}", userId);
            BaseContext.setCurrentId(userId);
            return true;
        } catch (Exception ex) {
            response.setStatus(401);
            return false;
        }
    }
}
