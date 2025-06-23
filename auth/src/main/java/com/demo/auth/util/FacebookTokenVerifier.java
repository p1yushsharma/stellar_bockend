package com.demo.auth.util;

import com.demo.auth.dto.OAuthUser;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.JSONObject;

@Component
public class FacebookTokenVerifier {

    private static final String DEBUG_TOKEN_URL = "https://graph.facebook.com/me";
    private static final String FIELDS = "id,name,email";

    public OAuthUser verify(String accessToken) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(DEBUG_TOKEN_URL)
                    .queryParam("fields", FIELDS)
                    .queryParam("access_token", accessToken)
                    .toUriString();

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            JSONObject json = new JSONObject(response);
            String email = json.has("email") ? json.getString("email") : null;
            String name = json.has("name") ? json.getString("name") : null;

            if (email == null || name == null) {
                throw new RuntimeException("Missing email or name in Facebook token response.");
            }

            return OAuthUser.builder()
                    .email(email)
                    .name(name)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify Facebook token: " + e.getMessage());
        }
    }
}
