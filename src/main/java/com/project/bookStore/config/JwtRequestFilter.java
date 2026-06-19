package com.project.bookStore.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;

    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenWithBearer = request.getHeader("Authorization");

        // FIX: Hand the request over to the next filter before returning!
        if (tokenWithBearer == null || !tokenWithBearer.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // This lets /login pass through safely
            return;
        }

        String token = tokenWithBearer.substring("Bearer ".length());

        Authentication authentication = jwtUtil.validateToken(token);

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}


