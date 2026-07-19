package com.mg_devjoint_task_one.library_management.mapper;

import com.mg_devjoint_task_one.library_management.dto.response.BookResponse;
import com.mg_devjoint_task_one.library_management.model.*;

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
}
