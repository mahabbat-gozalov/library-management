package com.mg_devjoint.library_management.mapper;

import com.mg_devjoint.library_management.dto.response.CategoryResponse;
import com.mg_devjoint.library_management.model.Category;

public final class CategoryMapper {
    private CategoryMapper() {
    }

    public static CategoryResponse toCategoryResponse(Category category) {

        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription());

    }

}
