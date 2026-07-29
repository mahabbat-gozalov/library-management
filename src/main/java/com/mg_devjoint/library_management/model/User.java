package com.mg_devjoint.library_management.model;


import com.mg_devjoint.library_management.model.enums.UserRole;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.mg_devjoint.library_management.model.validation.CommonValidationUtils.*;
import static com.mg_devjoint.library_management.model.validation.UserValidationUtils.*;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "enabled")
    private boolean enabled;

    protected User() {
    }


    public static User create(String email, String password, UserRole role, String name, String surname, String phoneNumber) {

        validateEmail(email);
        validatePhoneNumber(phoneNumber);
        validateUserRole(role);
        validatePassword(password);
        validateName(name);
        validateSurname(surname);

        User user = new User();

        user.email = email;
        user.password = password;
        user.role = role;
        user.name = name;
        user.surname = surname;
        user.phoneNumber = phoneNumber;

        return user;
    }

    public static User createWithId(UUID id, String email, String password, UserRole role, String name, String surname, String phoneNumber) {
        validateIdCannotBeNull(id);

        User user = create(email, password, role, name, surname, phoneNumber);

        user.id = id;

        return user;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setPassword(String password) {
        validatePassword(password);
        this.password = password;
    }

}
