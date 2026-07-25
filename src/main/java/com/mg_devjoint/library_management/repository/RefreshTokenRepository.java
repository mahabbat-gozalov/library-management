package com.mg_devjoint.library_management.repository;

import com.mg_devjoint.library_management.model.RefreshToken;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    @EntityGraph(attributePaths = {"user"})
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    List<RefreshToken> findAllByUserId(UUID userId);
}
