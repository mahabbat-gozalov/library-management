package com.mg_devjoint.library_management.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

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

    public RefreshToken() {
    }

    public RefreshToken(String refreshToken, User user, LocalDateTime expiresAt, LocalDateTime createdAt, boolean revoked) {
        this.refreshToken = refreshToken;
        this.user = user;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.revoked = revoked;
    }


    public boolean isRevoked() {
        return this.revoked;
    }
    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public LocalDateTime getExpiresAt() {
        return this.expiresAt;
    }

    public User getUser() {
        return this.user;
    }
}
