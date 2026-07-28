package com.mg_devjoint.library_management.mapper;

import com.mg_devjoint.library_management.dto.response.UserResponse;
import com.mg_devjoint.library_management.model.User;

public final class UserMapper {
    public static UserResponse toUserResponse(final User user) {
        return new UserResponse(
            user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getName(),
                user.getSurname(),
                user.getPhoneNumber(),
                user.getCreatedAt()
        );
    }

}
