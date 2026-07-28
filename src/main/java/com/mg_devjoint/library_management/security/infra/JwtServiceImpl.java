package com.mg_devjoint.library_management.security.infra;

import com.mg_devjoint.library_management.model.enums.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${security.jwt-secret-key}")
    private String secretKey;

    @Value("${security.access-token.expiration-milliseconds}")
    private long accessTokenExpiration;

    @Override
    public String generateAccessToken(String email, UUID userId, UserRole userRole) {

        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + accessTokenExpiration);

        return Jwts.builder()
                .setSubject(email)
                .claim("user_id", userId.toString())
                .claim("user_role", userRole.name())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getKey())
                .compact();
    }

    @Override
    public String extractUsername(String token) {

        Claims claims = getAllClaims(token);

        return claims.getSubject();
    }

    @Override
    public boolean isTokenExpired(String token) {
        Claims claims = getAllClaims(token);

        Date expiration = claims.getExpiration();

        return System.currentTimeMillis() > expiration.getTime();
    }

    private Claims getAllClaims(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build();

        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}