package com.mg_devjoint.library_management.service.impl;

import com.mg_devjoint.library_management.dto.request.*;
import com.mg_devjoint.library_management.dto.request.create.CreateUserRequest;
import com.mg_devjoint.library_management.dto.response.*;
import com.mg_devjoint.library_management.mapper.UserMapper;
import com.mg_devjoint.library_management.model.RefreshToken;
import com.mg_devjoint.library_management.model.User;
import com.mg_devjoint.library_management.security.CustomUserDetails;
import com.mg_devjoint.library_management.security.infra.JwtService;
import com.mg_devjoint.library_management.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final MailService mailService;


    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JwtService jwtService,
                           RefreshTokenService refreshTokenService,
                           UserService userService,
                           PasswordEncoder passwordEncoder,
                           MailService mailService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {

        String temporaryPassword = generateTemporaryPassword();

        String encodedInitialPassword = passwordEncoder.encode(temporaryPassword);

        User user = new User(
                request.email(),
                encodedInitialPassword,
                request.name(),
                request.surname(),
                request.phoneNumber(),
                request.role()
        );

        log.debug("Temporary password for {}: {}", user.getEmail(), temporaryPassword);

        CreateUserResponse response = userService.createUser(user);

        boolean emailSent = mailService.sendTemporaryPasswordEmail(user.getEmail(), temporaryPassword);

        return response.withTemporaryPasswordEmailSent(emailSent);
    }

    @Override
    public void logout(LogoutRequest request) {
        RefreshToken refreshTokenByValue = refreshTokenService.getRefreshTokenByValue(request.refreshToken());

        refreshTokenService.revokeRefreshToken(refreshTokenByValue);
    }

    @Override
    public void logoutFromAllDevices(UUID userId) {
        int updatedCount = refreshTokenService.revokeAllRefreshTokensByUser(userId);
        log.info("{} refresh tokens revoked for user {}", updatedCount, userId);
    }

    @Override
    public UserResponse getCurrentUser(CustomUserDetails userDetails) {
        User user = userDetails.user();

        return UserMapper.toUserResponse(user);
    }


    @Override
    public LoginResponse login(LoginRequest request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());

        Authentication authenticationResult = authenticationManager.authenticate(authenticationToken);

        CustomUserDetails principal = (CustomUserDetails) authenticationResult.getPrincipal();

        String accessToken = jwtService.generateAccessToken(principal.getUsername(), principal.getId(), principal.getRole());

        String refreshToken = refreshTokenService.createRefreshToken(principal.getId());

        return new LoginResponse(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public RefreshResponse refresh(RefreshRequest request) {

        RefreshToken refreshTokenByValue = refreshTokenService.getRefreshTokenByValue(request.refreshToken());

        refreshTokenService.validateRefreshToken(refreshTokenByValue);

        User user = refreshTokenByValue.getUser();

        String accessToken = jwtService.generateAccessToken(user.getEmail(), user.getId(), user.getRole());

        refreshTokenService.revokeRefreshToken(refreshTokenByValue);

        String refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return new RefreshResponse(accessToken, refreshToken);
    }

    private String generateTemporaryPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

}
