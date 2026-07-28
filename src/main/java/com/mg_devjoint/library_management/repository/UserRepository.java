package com.mg_devjoint.library_management.repository;

import com.mg_devjoint.library_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);
}
