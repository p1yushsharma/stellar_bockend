package com.demo.auth.service;
import com.demo.auth.dto.SignupRequest;
import com.demo.auth.dto.OAuthUser;
import com.demo.auth.dto.OAuthLoginRequest;
import com.demo.auth.entity.User;
import com.demo.auth.repository.UserRepository;
import com.demo.auth.util.FacebookTokenVerifier;
import com.demo.auth.util.GoogleTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final GoogleTokenVerifier googleTokenVerifier;
    private final FacebookTokenVerifier facebookTokenVerifier;

    public void signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User saveOAuthUserIfAbsent(String email, String username, String provider) {
        return userRepository.findByEmail(email).orElseGet(() -> {
            User user = User.builder()
                    .email(email)
                    .username(username)
                    .password("") 
                    .role("USER")
                    .build();
            return userRepository.save(user);
        });
    }

    public User processOAuthLogin(OAuthLoginRequest request) {
        String provider = request.getProvider();
        String token = request.getToken();

        String email;
        String username;

        switch (provider.toLowerCase()) {
            case "google":
                var googleUser = googleTokenVerifier.verify(token);
                email = googleUser.getEmail();
                username = googleUser.getName();
                break;
            case "facebook":
                var facebookUser = facebookTokenVerifier.verify(token);
                email = facebookUser.getEmail();
                username = facebookUser.getName();
                break;
            default:
                throw new RuntimeException("Unsupported OAuth provider: " + provider);
        }

        return saveOAuthUserIfAbsent(email, username, provider);
    }
}
