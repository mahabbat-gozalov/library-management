package com.mg_devjoint_task_one.library_management.service;

import com.mg_devjoint_task_one.library_management.dto.request.CreateCategoryRequest;
import com.mg_devjoint_task_one.library_management.dto.request.UpdateCategoryRequest;
import com.mg_devjoint_task_one.library_management.dto.response.CategoryResponse;

import java.util.UUID;

public interface CategoryService {
    CategoryResponse createCategory(CreateCategoryRequest request);

    CategoryResponse updateCategory(UUID categoryId, UpdateCategoryRequest request);

    CategoryResponse getCategoryById(UUID categoryId);

    void deleteCategoryById(UUID categoryId);
}
