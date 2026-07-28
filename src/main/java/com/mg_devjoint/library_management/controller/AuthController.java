package com.mg_devjoint.library_management.controller;

import com.mg_devjoint.library_management.dto.request.*;
import com.mg_devjoint.library_management.dto.request.update.ChangePasswordRequest;
import com.mg_devjoint.library_management.dto.response.*;
import com.mg_devjoint.library_management.security.CustomUserDetails;
import com.mg_devjoint.library_management.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Authenticate user",
            description = "Authenticates a user using email and password and returns access and refresh tokens"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User authenticated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(@Valid @RequestBody RefreshRequest request) {
        RefreshResponse response = authService.refresh(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequest request) {
        authService.logout(request);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/logout-all")
    public ResponseEntity<Void> logoutFromAllDevices(@AuthenticationPrincipal CustomUserDetails userDetails) {
        authService.logoutFromAllDevices(userDetails.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserResponse response = authService.getCurrentUser(userDetails);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(customUserDetails, request);
        return ResponseEntity.noContent().build();
    }
}
