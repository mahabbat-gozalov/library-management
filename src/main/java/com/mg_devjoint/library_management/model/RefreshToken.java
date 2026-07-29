package com.mg_devjoint.library_management.model;

import com.mg_devjoint.library_management.exception.InvalidEntityDataException;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.mg_devjoint.library_management.model.validation.CommonValidationUtils.validateIdCannotBeNull;
import static com.mg_devjoint.library_management.model.validation.RefreshTokenValidationUtils.*;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "refresh_token")
    private String refreshToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column
    private boolean revoked;

    protected RefreshToken() {
    }


    public static RefreshToken create(String refreshTokenValue, User user, LocalDateTime expiresAt, LocalDateTime createdAt) {
        RefreshToken token = new RefreshToken();

        validateTokenValue(refreshTokenValue);
        validateAssociatedUser(user);
        validateCreatedAtAndExpiresAt(createdAt, expiresAt);

        token.refreshToken = refreshTokenValue;
        token.user = user;
        token.expiresAt = expiresAt;
        token.createdAt = createdAt;
        token.revoked = false;

        return token;
    }

    public static RefreshToken createWithId(UUID id, String refreshToken, User user, LocalDateTime expiresAt, LocalDateTime createdAt) {
        RefreshToken token = create(refreshToken, user, expiresAt, createdAt);
        validateIdCannotBeNull(id);
        token.id = id;
        return token;
    }

    public boolean isRevoked() {
        return this.revoked;
    }

    public void revoke() {
        if (this.revoked) {
            throw new InvalidEntityDataException("Refresh token is already revoked");
        }
        this.revoked = true;
    }

    public LocalDateTime getExpiresAt() {
        return this.expiresAt;
    }

    public User getUser() {
        return this.user;
    }
}
