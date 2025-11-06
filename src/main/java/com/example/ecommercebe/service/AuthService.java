package com.example.ecommercebe.service;

import com.example.ecommercebe.dto.request.LoginRequestDTO;
import com.example.ecommercebe.dto.request.RegisterRequestDTO;
import com.example.ecommercebe.model.User;
import com.example.ecommercebe.repository.UserRepository;
import com.example.ecommercebe.security.JwtUtil;
import com.example.ecommercebe.security.PasswordEncoderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoderUtil passwordEncoderUtil;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoderUtil passwordEncoderUtil, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoderUtil = passwordEncoderUtil;
        this.jwtUtil = jwtUtil;
    }

    // Register service layer
// Service
    public ResponseEntity<String> registerUser(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already taken");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            return ResponseEntity.badRequest().body("Phone number is already taken");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoderUtil.encodePassword(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }


    // Login with Email service layer
    public ResponseEntity<String> loginUser(LoginRequestDTO request) {
        // Try to find user by email first
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        // If not found by email, try phone number
        if (user == null) {
            user = userRepository.findByPhone(request.getEmail()).orElse(null);
        }

        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid email or phone number");
        }

        if (!passwordEncoderUtil.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getId());
        return ResponseEntity.ok(token);
    }


}
