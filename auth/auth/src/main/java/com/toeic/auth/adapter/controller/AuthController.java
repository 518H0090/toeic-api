package com.toeic.auth.adapter.controller;

import com.toeic.auth.application.dto.AuthResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.toeic.auth.application.service;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
        @RequestParam(name = "username") String username, 
        @RequestParam(name = "password") String password) {
        
        AuthResponseDto token = authService.authenticate(username, password);
        return token != null ? ResponseEntity.ok(token) : ResponseEntity.badRequest().body("Invalid credentials");
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam("refresh-token") String refreshToken) {
        AuthResponseDto newToken = authService.refreshToken(refreshToken);
        return newToken != null ? ResponseEntity.ok(newToken) : ResponseEntity.badRequest().body("Invalid refresh token");
    }

    @PostMapping("/invoke")
    public ResponseEntity<String> logout(@RequestParam("refresh-token") String refreshToken) {
        boolean success = authService.logout(refreshToken);
        return success ? ResponseEntity.ok("Logout successful") : ResponseEntity.badRequest().body("Logout failed");
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validate(@RequestHeader("Authorization") String token) {
        boolean isValid = authService.validateToken(token);
        return isValid ? ResponseEntity.ok("Token is valid") : ResponseEntity.badRequest().body("Invalid token");
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<String> validateToken(@PathVariable("token") String token) {
        boolean isValid = authService.validateToken(token);
        return isValid ? ResponseEntity.ok("Token is valid") : ResponseEntity.badRequest().body("Invalid token");
    }
}
