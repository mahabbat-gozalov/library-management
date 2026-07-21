package com.mg_devjoint_task_one.library_management.controller;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateBookRequest;
import com.mg_devjoint_task_one.library_management.dto.request.update.UpdateBookRequest;
import com.mg_devjoint_task_one.library_management.dto.response.BookResponse;
import com.mg_devjoint_task_one.library_management.dto.response.PageResponse;
import com.mg_devjoint_task_one.library_management.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@Tag(name = "Books", description = "Book management endpoints")
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(
            summary = "Create a new book",
            description = "Creates a new Book with INACTIVE status. An admin must activate it after review and before it becomes available for loans."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "404", description = "One or more provided author or category IDs do not exist")
    })
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody CreateBookRequest request) {
        BookResponse response = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get all books",
            description = "Retrieves a paginated list of books. Page numbering starts at 1."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All books retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> getAllBooks(@RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "10") int size
    ) {
        var body = bookService.getAllBooks(page, size);

        return ResponseEntity.ok(body);
    }

    @Operation(
            summary = "Get a book by ID",
            description = "Retrieves a book by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No book found with given Id")
    })
    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable UUID bookId) {
        BookResponse response = bookService.getBookById(bookId);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Update a book",
            description = """
                    Updates an existing book by its ID.
                    
                    The request may update the book's basic information,
                    quantity, authors, and categories.
                    
                    The full quantity cannot be less than the number of
                    currently loaned copies.
                    
                    Provided author and category IDs must exist.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Book updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data or full quantity is less than the number of loaned copies"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book, author, or category not found"
            )
    })
    @PutMapping("/{bookId}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable UUID bookId,
                                                   @Valid @RequestBody UpdateBookRequest request) {

        BookResponse response = bookService.updateBook(bookId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Activate a book",
            description = "Changes the book status to ACTIVE."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Book activated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Only INACTIVE books can be activated"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found"
            )
    })
    @PatchMapping("/{bookId}/activate")
    public ResponseEntity<Void> activateBookById(@PathVariable UUID bookId) {
        bookService.activateBookById(bookId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Deactivate a book",
            description = "Changes the book status to INACTIVE."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Book deactivated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Only ACTIVE books can be deactivated"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found"
            )
    })
    @PatchMapping("/{bookId}/deactivate")
    public ResponseEntity<Void> deactivateBookById(@PathVariable UUID bookId) {
        bookService.deactivateBookById(bookId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Suspend a book",
            description = "Changes the book status to SUSPENDED."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Book suspended successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Only INACTIVE books can be suspended"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found"
            )
    })
    @PatchMapping("/{bookId}/suspend")
    public ResponseEntity<Void> suspendBookById(@PathVariable UUID bookId) {
        bookService.suspendBookById(bookId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Delete a book",
            description = "Changes the book status to DELETED."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Book deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Book cannot be deleted due to business rule violations"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found"
            )
    })
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBookById(@PathVariable UUID bookId) {
        bookService.deleteBookById(bookId);
        return ResponseEntity.noContent().build();
    }

}