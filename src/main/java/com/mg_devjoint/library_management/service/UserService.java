package com.mg_devjoint.library_management.service;

import com.mg_devjoint.library_management.dto.response.*;
import com.mg_devjoint.library_management.model.User;

import java.util.UUID;

public interface UserService {

    CreateUserResponse createUser(User user);

    PageResponse<UserResponse> getAllUsers(int page, int size);

    User findUserByEmail(String email);

    User findUserById(UUID userId);

    void changePassword(User user, String encodedNewPassword);
}
