package com.mg_devjoint_task_one.library_management.controller;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateCategoryRequest;
import com.mg_devjoint_task_one.library_management.dto.request.update.UpdateCategoryRequest;
import com.mg_devjoint_task_one.library_management.dto.response.*;
import com.mg_devjoint_task_one.library_management.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {

        CategoryResponse response = categoryService.createCategory(request);

        return ResponseEntity.status(CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<CategoryResponse>> getAllCategories(@RequestParam(defaultValue = "1") int page,
                                                                           @RequestParam(defaultValue = "10") int size
    ) {
        var body = categoryService.getAllCategories(page, size);

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable(name = "categoryId") UUID categoryId) {

        CategoryResponse response = categoryService.getCategoryById(categoryId);

        return ResponseEntity.status(OK).body(response);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable(name = "categoryId") UUID categoryId, @Valid @RequestBody UpdateCategoryRequest request) {

        CategoryResponse response = categoryService.updateCategory(categoryId, request);

        return ResponseEntity.status(OK).body(response);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable(name = "categoryId") UUID categoryId) {

        categoryService.deleteCategoryById(categoryId);

        return ResponseEntity.status(NO_CONTENT).build();
    }

}
