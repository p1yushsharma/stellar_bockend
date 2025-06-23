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

   @Value("${jwt.JWT_SECRET}")
    
   private String secret;


    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        if (secret == null || secret.trim().isEmpty()) {
            throw new IllegalArgumentException("JWT secret must be configured");
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public Claims extractClaims(String token) {
        return parseToken(token);
    }

    public String extractUserRole(String token) {
        Claims claims = extractClaims(token);
        return claims.get("role", String.class);
    }

    public String extractUserId(String token) {
        Claims claims = extractClaims(token);
        Object userIdClaim = claims.get("userId");
        return userIdClaim != null ? userIdClaim.toString() : null;
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
