package com.demo.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OAuthUser {
    private String email;
    private String name;
    private String provider;
    private String providerId;
}
