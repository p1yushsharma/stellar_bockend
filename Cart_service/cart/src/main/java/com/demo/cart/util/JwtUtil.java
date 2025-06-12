package com.demo.cart.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        if (secret == null || secret.trim().isEmpty()) {
            throw new IllegalArgumentException("JWT secret must be configured");
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUserRole(String token) {
        Claims claims = extractClaims(token);
        return claims.get("role", String.class);
    }

    public String extractUserId(String token) {
        Claims claims = extractClaims(token);
        Object userIdClaim = claims.get("userId");
        if (userIdClaim == null) {
            return null;
        }
        return userIdClaim.toString();
    }

    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (JwtException e) {
            System.out.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }
}
