package com.mg_devjoint_task_one.library_management.service;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateBookRequest;
import com.mg_devjoint_task_one.library_management.dto.request.update.UpdateBookRequest;
import com.mg_devjoint_task_one.library_management.dto.response.BookResponse;
import com.mg_devjoint_task_one.library_management.dto.response.PageResponse;
import com.mg_devjoint_task_one.library_management.model.Book;

import java.util.UUID;

public interface BookService {

    BookResponse createBook(CreateBookRequest request);

    BookResponse updateBook(UUID bookId, UpdateBookRequest request);

    PageResponse<BookResponse> getAllBooks(int page, int size);

    BookResponse getBookById(UUID bookId);

    void activateBookById(UUID bookId);

    void deactivateBookById(UUID bookId);

    void deleteBookById(UUID bookId);

    void suspendBookById(UUID bookId);

    Book getBookEntityById(UUID bookId);

}
