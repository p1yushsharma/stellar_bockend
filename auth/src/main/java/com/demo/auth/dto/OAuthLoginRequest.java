package com.demo.auth.dto;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OAuthLoginRequest {
    private String provider; 
    private String token;    
}
