package com.mg_devjoint_task_one.library_management.controller;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateAuthorRequest;
import com.mg_devjoint_task_one.library_management.dto.request.update.UpdateAuthorRequest;
import com.mg_devjoint_task_one.library_management.dto.response.AuthorResponse;
import com.mg_devjoint_task_one.library_management.dto.response.PageResponse;
import com.mg_devjoint_task_one.library_management.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@Valid @RequestBody CreateAuthorRequest request) {
        AuthorResponse response = authorService.createAuthor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    ResponseEntity<PageResponse<AuthorResponse>> getAllAuthors(@RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "10") int size
    ) {
        var body = authorService.getAllAuthors(page, size);

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable UUID authorId) {
        AuthorResponse response = authorService.getAuthorById(authorId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<AuthorResponse> updateAuthor(
            @PathVariable UUID authorId,
            @Valid @RequestBody UpdateAuthorRequest request) {
        AuthorResponse response = authorService.updateAuthor(authorId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable UUID authorId) {
        authorService.deleteAuthorById(authorId);
        return ResponseEntity.noContent().build();
    }
}