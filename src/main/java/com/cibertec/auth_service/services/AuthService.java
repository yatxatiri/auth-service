package com.cibertec.auth_service.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cibertec.auth_service.configs.JwtService;
import com.cibertec.auth_service.dtos.AuthResponse;
import com.cibertec.auth_service.dtos.LoginRequest;
import com.cibertec.auth_service.dtos.RegisterRequest;
import com.cibertec.auth_service.dtos.UserResponse;
import com.cibertec.auth_service.entities.User;
import com.cibertec.auth_service.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            // TODO: change exception to more specifically
            throw new RuntimeException("El email " + request.getEmail() + " ya está registrado");
        }     

        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
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
    
    public AuthResponse login(LoginRequest request) {
    	
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(), 
                request.getPassword()
            )
        );

    	User user = userRepository.findByEmail(request.getEmail())
    			.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    	
    	String token = jwtService.generateToken(user);
    	String refreshToken = jwtService.generateRefreshToken(user);
    	
    	return AuthResponse.builder()
    			.accessToken(token)
    			.refreshToken(refreshToken)
    			.build();
    }

    public UserResponse getCurrentUser(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Cabecera de autorización inválido");
        }
    
        String token = authHeader.replace("Bearer ", "");
        String email = jwtService.extractEmail(token);

        if (email == null) {
            throw new IllegalArgumentException("Token de refresco inválido");
        }

        User user = userRepository.findByEmail(email)
    			.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if (jwtService.isTokenValid(user, token)) {
            throw new IllegalArgumentException("Token de refresco inválido");
        }

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .documentType(user.getDocumentType())
                .type(user.getType())
                .number(user.getNumber())
                .address(user.getAddress())
                .telephone(user.getTelephone())
                .active(user.isActive())
                .build();
    }

    public AuthResponse refreshToken(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Header de autorización inválido");
        }

        String refreshToken = authHeader.replace("Bearer ", "");

        String email = jwtService.extractEmail(refreshToken);

        User user = userRepository.findByEmail(email)
    			.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String accesToken = jwtService.generateRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accesToken)
                .refreshToken(refreshToken)
                .build();
    }
}
