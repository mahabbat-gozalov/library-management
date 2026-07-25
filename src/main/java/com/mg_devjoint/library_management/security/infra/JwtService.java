package com.mg_devjoint.library_management.security.infra;

import com.mg_devjoint.library_management.model.enums.UserRole;

import java.util.UUID;

public interface JwtService {

    String generateAccessToken(String email, UUID userId, UserRole userRole);

    String extractUsername(String token);

    boolean isTokenExpired(String token);

}
