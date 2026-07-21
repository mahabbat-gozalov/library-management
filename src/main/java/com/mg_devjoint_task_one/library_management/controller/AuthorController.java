package com.mg_devjoint_task_one.library_management.controller;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateAuthorRequest;
import com.mg_devjoint_task_one.library_management.dto.request.update.UpdateAuthorRequest;
import com.mg_devjoint_task_one.library_management.dto.response.AuthorResponse;
import com.mg_devjoint_task_one.library_management.dto.response.PageResponse;
import com.mg_devjoint_task_one.library_management.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Authors", description = "Author management endpoints")
@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Operation(
            summary = "Create a new author",
            description = "Creates a new author record."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Author created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@Valid @RequestBody CreateAuthorRequest request) {
        AuthorResponse response = authorService.createAuthor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get all authors",
            description = "Retrieves a paginated list of authors. Page numbering starts at 1."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authors retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<PageResponse<AuthorResponse>> getAllAuthors(@RequestParam(defaultValue = "1") int page,
                                                                      @RequestParam(defaultValue = "10") int size
    ) {
        var body = authorService.getAllAuthors(page, size);
        return ResponseEntity.ok(body);
    }

    @Operation(
            summary = "Get an author by ID",
            description = "Retrieves author details by ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable UUID authorId) {
        AuthorResponse response = authorService.getAuthorById(authorId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update an author",
            description = "Updates an existing author details by ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @PutMapping("/{authorId}")
    public ResponseEntity<AuthorResponse> updateAuthor(
            @PathVariable UUID authorId,
            @Valid @RequestBody UpdateAuthorRequest request) {
        AuthorResponse response = authorService.updateAuthor(authorId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete an author",
            description = "Deletes an author by ID and removes relationships with associated books."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Author deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable UUID authorId) {
        authorService.deleteAuthorById(authorId);
        return ResponseEntity.noContent().build();
    }
}