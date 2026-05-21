package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final JwtTokenProvider tokenProvider;

    @Autowired
    public AuthController(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/api/auth/login")
    public void login(HttpServletResponse response) throws IOException {
        // Redirect to standard Spring Security Google OAuth2 login endpoint
        response.sendRedirect("/oauth2/authorization/google");
    }

    /**
     * Helper endpoint for development testing to get a valid signed JWT
     * without going through the real Google OAuth2 consent screen.
     */
    @GetMapping("/api/auth/mock-login")
    public ResponseEntity<Map<String, String>> mockLogin(
            @RequestParam(value = "email", defaultValue = "user@gmail.com") String email,
            @RequestParam(value = "name", defaultValue = "John Doe") String name) {
        String token = tokenProvider.generateToken(email, name, "https://lh3.googleusercontent.com/a/default-profile");
        
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("tokenType", "Bearer");
        response.put("email", email);
        response.put("name", name);
        
        return ResponseEntity.ok(response);
    }
}
