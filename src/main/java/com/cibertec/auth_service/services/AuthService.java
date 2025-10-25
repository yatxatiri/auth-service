package com.cibertec.auth_service.services;

import org.springframework.stereotype.Service;

import com.cibertec.auth_service.dtos.AuthResponse;
import com.cibertec.auth_service.dtos.RegisterRequest;
import com.cibertec.auth_service.entities.User;
import com.cibertec.auth_service.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }     

        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(request.getPassword())
            .type(request.getType())
            .documentType(request.getDocumentType())
            .number(request.getNumber())
            .address(request.getAddress())
            .telephone(request.getTelephone())
            .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .accessToken(token)
                .build();
    }

}
