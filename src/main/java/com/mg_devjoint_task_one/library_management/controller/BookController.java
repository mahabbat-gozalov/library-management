package com.mg_devjoint_task_one.library_management.controller;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateBookRequest;
import com.mg_devjoint_task_one.library_management.dto.request.update.UpdateBookRequest;
import com.mg_devjoint_task_one.library_management.dto.response.BookResponse;
import com.mg_devjoint_task_one.library_management.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody CreateBookRequest request) {
        BookResponse response = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable UUID bookId,
                                                   @Valid @RequestBody UpdateBookRequest request) {

        BookResponse response = bookService.updateBook(bookId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable UUID bookId) {
        BookResponse response = bookService.getBookById(bookId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{bookId}/activate")
    public ResponseEntity<Void> activateBookById(@PathVariable UUID bookId) {
        bookService.activateBookById(bookId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{bookId}/deactivate")
    public ResponseEntity<Void> deactivateBookById(@PathVariable UUID bookId) {
        bookService.deactivateBookById(bookId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{bookId}/suspend")
    public ResponseEntity<Void> suspendBookById(@PathVariable UUID bookId) {
        bookService.suspendBookById(bookId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBookById(@PathVariable UUID bookId) {
        bookService.deleteBookById(bookId);
        return ResponseEntity.noContent().build();
    }

}