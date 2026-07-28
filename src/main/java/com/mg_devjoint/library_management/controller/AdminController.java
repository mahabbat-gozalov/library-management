package com.mg_devjoint.library_management.controller;

import com.mg_devjoint.library_management.dto.request.create.CreateUserRequest;
import com.mg_devjoint.library_management.dto.response.CreateUserResponse;
import com.mg_devjoint.library_management.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AuthService authService;

    public AdminController(AuthService authService) {
        this.authService = authService;
    }


    @Operation(
            summary = "Create a new user",
            description = "Creates a new user record."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "Conflict email error"),
    })
    @PostMapping("/admin/create-user")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        CreateUserResponse response = authService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
