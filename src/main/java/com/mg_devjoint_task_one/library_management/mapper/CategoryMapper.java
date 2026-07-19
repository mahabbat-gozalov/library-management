package com.mg_devjoint_task_one.library_management.mapper;

import com.mg_devjoint_task_one.library_management.dto.response.CategoryResponse;
import com.mg_devjoint_task_one.library_management.model.Book;
import com.mg_devjoint_task_one.library_management.model.Category;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public final class CategoryMapper {
    private CategoryMapper() {
    }

    public static CategoryResponse toCategoryResponse(Category category) {

        Set<UUID> categoryBookIdSet = category.getBooks()
                .stream()
                .map(Book::getId)
                .collect(Collectors.toSet());

        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                categoryBookIdSet);

    }

}
