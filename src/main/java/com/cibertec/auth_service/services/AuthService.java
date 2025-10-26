package com.cibertec.auth_service.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cibertec.auth_service.dtos.AuthResponse;
import com.cibertec.auth_service.dtos.LoginRequest;
import com.cibertec.auth_service.dtos.RegisterRequest;
import com.cibertec.auth_service.entities.User;
import com.cibertec.auth_service.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
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
    	
    	User user = userRepository.findByEmail(request.getEmail())
    			.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    	
    	if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new BadCredentialsException("Credenciales de usuario inválidas");
		}
    	
    	String token = jwtService.generateToken(user);
    	String refreshToken = jwtService.generateRefreshToken(user);
    	
    	return AuthResponse.builder()
    			.accessToken(token)
    			.refreshToken(refreshToken)
    			.build();
    }

}
