package com.mg_devjoint_task_one.library_management.service.impl;

import com.mg_devjoint_task_one.library_management.dto.enums.CollectionUpdateMode;
import com.mg_devjoint_task_one.library_management.dto.request.create.CreateCategoryRequest;
import com.mg_devjoint_task_one.library_management.dto.request.update.UpdateCategoryRequest;
import com.mg_devjoint_task_one.library_management.dto.response.CategoryResponse;
import com.mg_devjoint_task_one.library_management.dto.response.PageResponse;
import com.mg_devjoint_task_one.library_management.exception.NotFoundException;
import com.mg_devjoint_task_one.library_management.mapper.CategoryMapper;
import com.mg_devjoint_task_one.library_management.model.Book;
import com.mg_devjoint_task_one.library_management.model.Category;
import com.mg_devjoint_task_one.library_management.repository.BookRepository;
import com.mg_devjoint_task_one.library_management.repository.CategoryRepository;
import com.mg_devjoint_task_one.library_management.service.CategoryService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, BookRepository bookRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {

        Set<UUID> initialBookIdSet = request.bookIdSet() == null ? Collections.emptySet() : request.bookIdSet();

        Set<Book> bookSet = getBookEntitySetByIdSet(initialBookIdSet);

        Category category = Category.create(request.name(), request.description(), bookSet);

        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toCategoryResponse(savedCategory);
    }

    //TODO: USE ENTITY GRAPH UNLESS IT WON'T WORK
    @Override
    @Transactional(readOnly = true)
    public PageResponse<CategoryResponse> getAllCategories(int page, int size) {
        Pageable pageable = getPageable(page, size);

        Page<Category> allCategories = categoryRepository.findAll(pageable);

        Page<CategoryResponse> allCategoryResponses = allCategories.map(CategoryMapper::toCategoryResponse);

        return PageResponse.of(allCategoryResponses);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(UUID categoryId, UpdateCategoryRequest request) {

        CollectionUpdateMode bookSetUpdateMode = request.bookSetUpdateMode() == null ? CollectionUpdateMode.ADD : request.bookSetUpdateMode();

        Category categoryToUpdate = getCategoryEntityById(categoryId);

        categoryToUpdate.setName(request.name());
        categoryToUpdate.setDescription(request.description());

        if (request.bookIdSet() != null) {

            if (bookSetUpdateMode == CollectionUpdateMode.REPLACE) {
                Set<Book> currentBooks = new HashSet<>(categoryToUpdate.getBooks());

                currentBooks
                        .forEach(categoryToUpdate::removeBook);
            }

            getBookEntitySetByIdSet(request.bookIdSet())
                    .forEach(categoryToUpdate::addBook);
        }

        return CategoryMapper.toCategoryResponse(categoryToUpdate);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(UUID categoryId) {

        Category categoryById = getCategoryEntityById(categoryId);

        return CategoryMapper.toCategoryResponse(categoryById);
    }

    @Override
    @Transactional
    public void deleteCategoryById(UUID categoryId) {
        Category categoryById = getCategoryEntityById(categoryId);

        Set<Book> categoryBookSet = new HashSet<>(categoryById.getBooks());

        categoryBookSet.forEach(categoryById::removeBook);

        categoryRepository.delete(categoryById);
    }

    @Override
    public Category getCategoryEntityById(UUID categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found with id: " + categoryId));
    }

    @Override
    public Set<Category> getCategorySetByIdSet(Set<UUID> categoryIdSet) {

        Set<Category> categorySet = new HashSet<>(categoryRepository.findAllById(categoryIdSet));

        Set<UUID> categoryIdSetFromDB = categorySet
                .stream()
                .map(Category::getId)
                .collect(Collectors.toSet());

        Set<UUID> notFoundCategoryIdSet = categoryIdSet.stream()
                .filter(id -> !categoryIdSetFromDB.contains(id))
                .collect(Collectors.toSet());

        if (!notFoundCategoryIdSet.isEmpty()) {
            throw new NotFoundException(
                    "Categories not found with ids: " + notFoundCategoryIdSet
            );
        }

        return categorySet;

    }

    private Set<Book> getBookEntitySetByIdSet(Set<UUID> bookIdSet) {
        Set<Book> bookSet = new HashSet<>(bookRepository.findAllById(bookIdSet));

        Set<UUID> bookIdSetFromDB = bookSet.stream()
                .map(Book::getId)
                .collect(Collectors.toSet());

        Set<UUID> notFoundBookIdSet = bookIdSet.stream()
                .filter(bookId -> !bookIdSetFromDB.contains(bookId))
                .collect(Collectors.toSet());

        if (!notFoundBookIdSet.isEmpty())
            throw new NotFoundException(
                    "Books not found with ids: " + notFoundBookIdSet
            );

        return bookSet;
    }

    private Pageable getPageable(int pageNumber, int pageSize) {

        pageNumber = pageNumber <= 0 ? 1 : pageNumber;
        pageSize = pageSize <= 0 ? 10 : pageSize;

        return PageRequest.of(pageNumber - 1, pageSize);
    }

}
