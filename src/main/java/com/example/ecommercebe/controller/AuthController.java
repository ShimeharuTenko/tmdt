package com.example.ecommercebe.controller;

import com.example.ecommercebe.dto.request.LoginRequestDTO;
import com.example.ecommercebe.dto.request.RegisterRequestDTO;
import com.example.ecommercebe.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private  AuthService authService;

    // Register
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDTO request) {
        return authService.registerUser(request);
    }

    // Login with Email
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDTO request) {
        return authService.loginUser(request);
    }

    // Logout
//    @PostMapping("/logout")

}