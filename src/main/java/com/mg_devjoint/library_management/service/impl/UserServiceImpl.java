package com.mg_devjoint.library_management.service.impl;

import com.mg_devjoint.library_management.dto.response.CreateUserResponse;
import com.mg_devjoint.library_management.exception.DuplicateEmailException;
import com.mg_devjoint.library_management.exception.NotFoundException;
import com.mg_devjoint.library_management.model.User;
import com.mg_devjoint.library_management.repository.UserRepository;
import com.mg_devjoint.library_management.service.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User findUserById(UUID userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public CreateUserResponse createUser(User user) {

        validateUniqueEmail(user.getEmail());

        User savedUser = userRepository.save(user);

        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getName(),
                savedUser.getSurname(),
                savedUser.getPhoneNumber(),
                savedUser.getRole().name(),
                false
        );

    }

    private void validateUniqueEmail(String email) {
        boolean emailExist = isEmailExist(email);

        if (emailExist) {
            throw new DuplicateEmailException("Email already exists");
        }
    }

    private boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

}
