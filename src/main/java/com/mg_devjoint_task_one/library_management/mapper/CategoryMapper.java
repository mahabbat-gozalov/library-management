package com.mg_devjoint_task_one.library_management.mapper;

import com.mg_devjoint_task_one.library_management.dto.response.CategoryResponse;
import com.mg_devjoint_task_one.library_management.model.Category;

import java.util.*;

public final class CategoryMapper {
    private CategoryMapper() {
    }

    public static CategoryResponse toCategoryResponse(Category category) {

        Set<UUID> categoryBookIdSet = new HashSet<>();

        category.getBooks()
                .forEach(book -> categoryBookIdSet.add(book.getId()));

        return new CategoryResponse(category.getName(), category.getDescription(), categoryBookIdSet);


    }

}
