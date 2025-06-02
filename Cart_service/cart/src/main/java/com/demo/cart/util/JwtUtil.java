package com.demo.cart.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;   
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;                 
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret) 
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUserId(String token) {
        Claims claims = extractClaims(token);
        return claims.get("userId", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    @PostConstruct
    public void validateSecret() {
        if (secret == null || secret.trim().isEmpty()) {
            throw new IllegalArgumentException("JWT secret must be configured");
        }
    }
}
