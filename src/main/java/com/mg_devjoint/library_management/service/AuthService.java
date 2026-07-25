package com.mg_devjoint.library_management.service;


import com.mg_devjoint.library_management.dto.request.LoginRequest;
import com.mg_devjoint.library_management.dto.request.RefreshRequest;
import com.mg_devjoint.library_management.dto.request.create.CreateUserRequest;
import com.mg_devjoint.library_management.dto.response.*;

public interface AuthService {
    LoginResponse login(LoginRequest request);

    RefreshResponse refresh(RefreshRequest request);

    CreateUserResponse createUser(CreateUserRequest request);
}
