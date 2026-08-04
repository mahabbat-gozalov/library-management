package com.mg_devjoint.library_management.mapper;

import com.mg_devjoint.library_management.dto.response.BookResponse;
import com.mg_devjoint.library_management.dto.response.BookSummaryResponse;
import com.mg_devjoint.library_management.model.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public final class BookMapper {

    private BookMapper() {
    }

    public static BookResponse toBookResponse(Book book) {

        Set<UUID> authorsIdSet = book.getAuthors()
                .stream()
                .map(Author::getId)
                .collect(Collectors.toSet());

        Set<UUID> categoryIdSet = book.getCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toSet());

       return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getDescription(),
                book.getFullQuantity(),
                book.getAvailableQuantity(),
                authorsIdSet,
                categoryIdSet
        );


    }

    public static BookSummaryResponse toBookSummaryResponse(Book book) {
        return new BookSummaryResponse(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getDescription(),
                book.getFullQuantity(),
                book.getAvailableQuantity()
        );
    }

}
