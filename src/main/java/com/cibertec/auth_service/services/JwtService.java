package com.cibertec.auth_service.services;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.cibertec.auth_service.entities.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private static final String SECRET_KEY = "2060f61b05846c956e142232a8f92521069c33defcd8de42946c29c876f188e9";

    public String generateToken(User user) {
    
        return Jwts.builder()
            .setSubject(user.getEmail())
            .claim("role", user.getType())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(getSignInKey())
            .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
