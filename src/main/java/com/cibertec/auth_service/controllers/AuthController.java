package com.cibertec.auth_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.auth_service.dtos.AuthResponse;
import com.cibertec.auth_service.dtos.RegisterRequest;
import com.cibertec.auth_service.services.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registeResponseEntity(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    
}
