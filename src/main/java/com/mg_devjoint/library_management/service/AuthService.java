package com.mg_devjoint.library_management.service;


import com.mg_devjoint.library_management.dto.request.*;
import com.mg_devjoint.library_management.dto.request.create.CreateUserRequest;
import com.mg_devjoint.library_management.dto.request.update.ChangePasswordRequest;
import com.mg_devjoint.library_management.dto.response.*;
import com.mg_devjoint.library_management.security.CustomUserDetails;
import jakarta.validation.Valid;

import java.util.UUID;

public interface AuthService {
    LoginResponse login(LoginRequest request);

    RefreshResponse refresh(RefreshRequest request);

    CreateUserResponse createUser(CreateUserRequest request);

    void logout(LogoutRequest request);

    void logoutFromAllDevices(UUID id);

    UserResponse getCurrentUser(CustomUserDetails userDetails);

    void changePassword(CustomUserDetails customUserDetails,ChangePasswordRequest request);
}
