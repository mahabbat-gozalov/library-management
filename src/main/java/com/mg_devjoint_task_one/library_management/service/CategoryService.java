package com.mg_devjoint_task_one.library_management.service;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateCategoryRequest;
import com.mg_devjoint_task_one.library_management.dto.request.update.UpdateCategoryRequest;
import com.mg_devjoint_task_one.library_management.dto.response.*;
import com.mg_devjoint_task_one.library_management.model.Category;

import java.util.Set;
import java.util.UUID;

public interface CategoryService {

    CategoryResponse createCategory(CreateCategoryRequest request);

    PageResponse<CategoryResponse> getAllCategories(int page, int size);

    CategoryResponse getCategoryById(UUID categoryId);

    CategoryResponse updateCategory(UUID categoryId, UpdateCategoryRequest request);

    void deleteCategoryById(UUID categoryId);

    Category getCategoryEntityById(UUID categoryId);

    Set<Category> getCategorySetByIdSet(Set<UUID> categoryIdSet);
}
