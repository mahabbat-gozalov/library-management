package com.mg_devjoint_task_one.library_management.service;

import com.mg_devjoint_task_one.library_management.dto.response.CreateUserResponse;
import com.mg_devjoint_task_one.library_management.model.User;

import java.util.UUID;

public interface UserService {

    User findUserByEmail(String email);

    User findUserById(UUID userId);

    CreateUserResponse createUser(User user);

}
