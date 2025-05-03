package com.pm.authservice.service;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtils;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtils) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public Optional<String> authenticate(LoginRequestDTO request) {
        Optional<String> token = userService
                .findByEmail(request.getEmail())
                .filter(u -> passwordEncoder.matches(request.getPassword(), u.getPassword()))
                .map(u -> jwtUtils.generateToken(u.getEmail(), u.getRole()));

      return token;
    }

    public boolean validateToken(String token) {
        try {
            jwtUtils.validateToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
