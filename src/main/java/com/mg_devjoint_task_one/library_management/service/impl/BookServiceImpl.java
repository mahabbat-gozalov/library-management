package com.mg_devjoint_task_one.library_management.service.impl;

import com.mg_devjoint_task_one.library_management.dto.enums.CollectionUpdateMode;
import com.mg_devjoint_task_one.library_management.dto.request.create.CreateBookRequest;
import com.mg_devjoint_task_one.library_management.dto.request.update.UpdateBookRequest;
import com.mg_devjoint_task_one.library_management.dto.response.BookResponse;
import com.mg_devjoint_task_one.library_management.dto.response.PageResponse;
import com.mg_devjoint_task_one.library_management.exception.*;
import com.mg_devjoint_task_one.library_management.mapper.BookMapper;
import com.mg_devjoint_task_one.library_management.model.*;
import com.mg_devjoint_task_one.library_management.model.enums.BookStatus;
import com.mg_devjoint_task_one.library_management.repository.BookRepository;
import com.mg_devjoint_task_one.library_management.service.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public BookResponse createBook(CreateBookRequest request) {

        Set<UUID> authorIdSet = request.authorIdSet() == null ? Collections.emptySet() : request.authorIdSet();
        Set<UUID> categoryIdSet = request.categoryIdSet() == null ? Collections.emptySet() : request.categoryIdSet();

        Set<Author> authorSet = authorService.getAuthorSetByIdSet(authorIdSet);

        Set<Category> categorySet = categoryService.getCategorySetByIdSet(categoryIdSet);

        Book book = Book.create(
                request.title(),
                request.isbn(),
                request.description(),
                request.fullQuantity(),
                BookStatus.INACTIVE,
                authorSet,
                categorySet
        );

        Book savedBook = bookRepository.save(book);

        return BookMapper.toBookResponse(savedBook);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<BookResponse> getAllBooks(int page, int size) {

        Pageable pageable = getPageable(page, size);

        Page<Book> allBooks = bookRepository.findAll(pageable);

        Page<BookResponse> allBookResponses = allBooks.map(BookMapper::toBookResponse);

        return PageResponse.of(allBookResponses);

    }

    @Override
    @Transactional(readOnly = true)
    public BookResponse getBookById(UUID bookId) {
        Book bookEntityById = getBookEntityByIdWithAuthorsAndCategories(bookId);
        return BookMapper.toBookResponse(bookEntityById);
    }

    @Override
    @Transactional
    public BookResponse updateBook(UUID bookId, UpdateBookRequest request) {

        CollectionUpdateMode authorSetUpdateMode = request.authorSetUpdateMode() == null ? CollectionUpdateMode.ADD : request.authorSetUpdateMode();
        CollectionUpdateMode categorySetUpdateMode = request.categorySetUpdateMode() == null ? CollectionUpdateMode.ADD : request.categorySetUpdateMode();

        Book bookById = getBookEntityByIdWithAuthorsAndCategories(bookId);

        bookById.setTitle(request.title());
        bookById.setIsbn(request.isbn());
        bookById.setDescription(request.description());

        Integer currentAvailableQuantity = bookById.getAvailableQuantity();
        Integer currentFullQuantity = bookById.getFullQuantity();
        Integer onLoan = currentFullQuantity - currentAvailableQuantity;

        Integer newFullQuantity = request.fullQuantity();

        if (newFullQuantity < onLoan) {
            throw new InvalidEntityDataException("Full quantity cannot be less than borrowed books.");
        }

        Integer newAvailableQuantity = newFullQuantity - onLoan;

        bookById.setAvailableQuantity(newAvailableQuantity);
        bookById.setFullQuantity(newFullQuantity);


        if (request.authorIdSet() != null) {

            if (authorSetUpdateMode == CollectionUpdateMode.REPLACE) {
                HashSet<Author> currentBookAuthorSet = new HashSet<>(bookById.getAuthors());

                currentBookAuthorSet
                        .forEach(bookById::removeAuthor);
            }

            Set<Author> authorSet = authorService.getAuthorSetByIdSet(request.authorIdSet());

            authorSet.forEach(bookById::addAuthor);
        }

        if (request.categoryIdSet() != null) {
            if (categorySetUpdateMode == CollectionUpdateMode.REPLACE) {
                Set<Category> currentBookCategorySet = new HashSet<>(bookById.getCategories());

                currentBookCategorySet
                        .forEach(bookById::removeCategory);
            }

            Set<Category> categorySet = categoryService.getCategorySetByIdSet(request.categoryIdSet());

            categorySet.forEach(bookById::addCategory);
        }

        return BookMapper.toBookResponse(bookById);
    }

    @Override
    @Transactional
    public void activateBookById(UUID bookId) {
        Book bookToActivate = getBookEntityById(bookId);

        if (bookToActivate.getStatus() != BookStatus.INACTIVE) {
            throw new InvalidOperationException("Only inactive books can be activated. Current status: " + bookToActivate.getStatus());
        }

        bookToActivate.setStatus(BookStatus.ACTIVE);
    }

    @Override
    @Transactional
    public void deactivateBookById(UUID bookId) {
        Book bookToDeactivate = getBookEntityById(bookId);

        if (bookToDeactivate.getStatus() != BookStatus.ACTIVE) {
            throw new InvalidOperationException("Only active books can be deactivated. Current status: " + bookToDeactivate.getStatus());
        }

        bookToDeactivate.setStatus(BookStatus.INACTIVE);
    }

    @Override
    @Transactional
    public void suspendBookById(UUID bookId) {
        Book bookToSuspend = getBookEntityById(bookId);

        if (bookToSuspend.getStatus() != BookStatus.INACTIVE) {
            throw new InvalidOperationException("Only inactive books can be suspended. Current status: " + bookToSuspend.getStatus());
        }

        bookToSuspend.setStatus(BookStatus.SUSPENDED);
    }

    @Override
    @Transactional
    public void deleteBookById(UUID bookId) {
        Book bookToDelete = getBookEntityById(bookId);

        int onLoan = bookToDelete.getFullQuantity() - bookToDelete.getAvailableQuantity();

        if (onLoan > 0) {
            throw new InvalidOperationException("Cannot delete the book because one or more copies are still on loan.");
        }

        if (bookToDelete.getStatus() != BookStatus.SUSPENDED) {
            throw new InvalidOperationException("Only suspended books can be deleted. Current status: " + bookToDelete.getStatus());
        }

        bookToDelete.setStatus(BookStatus.DELETED);
    }

    @Override
    public Book getBookEntityById(UUID bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found with id " + bookId));
    }

    @Override
    public Book getBookEntityByIdWithAuthorsAndCategories(UUID bookId) {
        return bookRepository.findBookByIdWithAuthorsAndCategories(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found with id " + bookId));
    }

    private Pageable getPageable(int pageNumber, int pageSize) {

        pageNumber = pageNumber <= 0 ? 1 : pageNumber;
        pageSize = pageSize <= 0 ? 10 : pageSize;

        return PageRequest.of(pageNumber - 1, pageSize);
    }

}

