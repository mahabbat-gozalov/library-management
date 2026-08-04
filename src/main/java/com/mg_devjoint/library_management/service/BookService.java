package com.mg_devjoint.library_management.service;

import com.mg_devjoint.library_management.dto.criteria.BookSearchCriteria;
import com.mg_devjoint.library_management.dto.request.create.CreateBookRequest;
import com.mg_devjoint.library_management.dto.request.update.*;
import com.mg_devjoint.library_management.dto.response.*;
import com.mg_devjoint.library_management.model.Book;

import java.util.UUID;

public interface BookService {

    BookResponse createBook(CreateBookRequest request);

    PageResponse<BookResponse> getAllBooksWithAuthorsAndCategories(int page, int size);

    BookResponse getBookById(UUID bookId);

    BookResponse updateBook(UUID bookId, UpdateBookRequest request);

    BookResponse updateBookFullQuantity(UUID bookId, Integer fullQuantity);

    BookResponse addAuthorsToBook(UUID bookId, AddAuthorsToBookRequest request);

    BookResponse addCategoriesToBook(UUID bookId, AddCategoriesToBookRequest request);

    void activateBookById(UUID bookId);

    void deactivateBookById(UUID bookId);

    void deleteBookById(UUID bookId);

    void suspendBookById(UUID bookId);

    Book getBookEntityById(UUID bookId);

    Book getBookEntityByIdWithAuthorsAndCategories(UUID bookId);

    PageResponse<BookSummaryResponse> filter(BookSearchCriteria criteria, int page, int size);
}
