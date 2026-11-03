package com.eduforge.training.filter;

import com.eduforge.training.utils.CurrentHolder;
import com.eduforge.training.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.UUID;

public class TokenFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(TokenFilter.class);

    private JwtUtils jwtUtils;
    private ObjectMapper objectMapper = new ObjectMapper();

    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();

        MDC.put("traceId", UUID.randomUUID().toString().replace("-", ""));

        if (requestURI.contains("/login") || requestURI.contains("/swagger-ui") ||
            requestURI.contains("/api-docs") || requestURI.contains("/actuator") ||
            requestURI.contains("/uploads") || requestURI.equals("/") ||
            requestURI.contains("/test") || requestURI.contains("/error")) {
            log.info("放行请求: {}", requestURI);
            filterChain.doFilter(request, response);
            MDC.remove("traceId");
            return;
        }

        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            log.warn("令牌为空, 请求URI: {}", requestURI);
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new AuthError("Unauthorized", "令牌不能为空")));
            MDC.remove("traceId");
            return;
        }

        try {
            Claims claims = jwtUtils.parseToken(token);
            Integer userId = Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(userId);
            log.info("当前登录用户ID: {}", userId);
        } catch (Exception e) {
            log.warn("令牌验证失败, 请求URI: {}, 错误: {}", requestURI, e.getMessage());
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new AuthError("Unauthorized", "令牌无效或已过期")));
            MDC.remove("traceId");
            return;
        }

        filterChain.doFilter(request, response);
        CurrentHolder.remove();
        MDC.remove("traceId");
    }

    private static class AuthError {
        private String error;
        private String message;

        public AuthError(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }
}
