package com.demo.cart.util;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        logger.debug("Authorization header: {}", authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            logger.debug("Extracted token: {}", token);

            if (jwtUtil.isTokenValid(token)) {
                logger.debug("Token is valid");

                String userId = jwtUtil.extractUserId(token);
                String role = jwtUtil.extractUserRole(token);
                logger.debug("Extracted userId: {}, role: {}", userId, role);

                String springRole = "ROLE_" + role;
               UsernamePasswordAuthenticationToken authToken =
               new UsernamePasswordAuthenticationToken(
               userId,
               null,
               List.of(new SimpleGrantedAuthority(springRole))
               );


                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                logger.debug("SecurityContext set for user: {}", userId);
            } else {
                logger.warn("Invalid token received"+ token);
            }
        } else {
            logger.debug("No Bearer token found in Authorization header");
        }

        filterChain.doFilter(request, response);
    }
}
