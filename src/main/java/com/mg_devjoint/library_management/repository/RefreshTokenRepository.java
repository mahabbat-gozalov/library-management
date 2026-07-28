package com.mg_devjoint.library_management.repository;

import com.mg_devjoint.library_management.model.RefreshToken;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    @EntityGraph(attributePaths = {"user"})
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    List<RefreshToken> findAllByUserId(UUID userId);

    @Modifying
    @Transactional
    @Query("""
    update RefreshToken rt
       set rt.revoked = true
     where rt.user.id = :userId
       and rt.revoked = false
""")
    int revokeAllByUserId(UUID userId);
}
