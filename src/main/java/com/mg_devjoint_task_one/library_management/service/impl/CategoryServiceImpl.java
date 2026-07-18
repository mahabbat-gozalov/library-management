package com.mg_devjoint_task_one.library_management.service.impl;

import com.mg_devjoint_task_one.library_management.dto.request.CreateCategoryRequest;
import com.mg_devjoint_task_one.library_management.dto.request.UpdateCategoryRequest;
import com.mg_devjoint_task_one.library_management.dto.response.CategoryResponse;
import com.mg_devjoint_task_one.library_management.exception.NotFoundException;
import com.mg_devjoint_task_one.library_management.mapper.CategoryMapper;
import com.mg_devjoint_task_one.library_management.model.Book;
import com.mg_devjoint_task_one.library_management.model.Category;
import com.mg_devjoint_task_one.library_management.repository.CategoryRepository;
import com.mg_devjoint_task_one.library_management.service.BookService;
import com.mg_devjoint_task_one.library_management.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final BookService bookService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, BookService bookService) {
        this.categoryRepository = categoryRepository;
        this.bookService = bookService;
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {

        Set<UUID> initialBookIdSet = request.initialBookIdSet();

        Set<Book> bookSetByIdSet = bookService.getBookSetByIdSet(initialBookIdSet);

        Category category = Category.create(request.name(), request.description(), bookSetByIdSet);

        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toCategoryResponse(savedCategory);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(UUID categoryId, UpdateCategoryRequest request) {

        UpdateCategoryRequest.BookUpdateMode bookUpdateMode = request.bookUpdateMode();
        if (request.bookUpdateMode() == null) bookUpdateMode = UpdateCategoryRequest.BookUpdateMode.ADD;


        Category categoryToUpdate = findCategoryById(categoryId);

        categoryToUpdate.setName(request.name());
        categoryToUpdate.setDescription(request.description());

        if (request.bookIdSet() != null && !request.bookIdSet().isEmpty()) {
            Set<Book> bookSetByIdSet = bookService.getBookSetByIdSet(request.bookIdSet());

            if (bookUpdateMode == UpdateCategoryRequest.BookUpdateMode.REPLACE) {
                Set<Book> currentBooks = new HashSet<>(categoryToUpdate.getBooks());
                currentBooks.forEach(categoryToUpdate::removeBook);
            }

            bookSetByIdSet.forEach(categoryToUpdate::addBook);
        }

        Category savedCategory = categoryRepository.save(categoryToUpdate);

        return CategoryMapper.toCategoryResponse(savedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(UUID categoryId) {

        Category categoryById = findCategoryById(categoryId);

        return CategoryMapper.toCategoryResponse(categoryById);
    }

    @Override
    @Transactional
    public void deleteCategoryById(UUID categoryId) {
        Category categoryById = findCategoryById(categoryId);

        Set<Book> categoryBookSet = new HashSet<>(categoryById.getBooks());

        categoryBookSet.forEach(categoryById::removeBook);

        categoryRepository.delete(categoryById);
    }

    private Category findCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found with id: " + categoryId));
    }

}
