package com.mg_devjoint.library_management.service.impl;

import com.mg_devjoint.library_management.dto.request.create.CreateBookRequest;
import com.mg_devjoint.library_management.dto.request.update.UpdateBookRequest;
import com.mg_devjoint.library_management.dto.response.BookResponse;
import com.mg_devjoint.library_management.exception.InvalidOperationException;
import com.mg_devjoint.library_management.exception.NotFoundException;
import com.mg_devjoint.library_management.model.*;
import com.mg_devjoint.library_management.model.enums.BookStatus;
import com.mg_devjoint.library_management.repository.BookRepository;
import com.mg_devjoint.library_management.service.AuthorService;
import com.mg_devjoint.library_management.service.CategoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book getBook(BookStatus status) {
        return Book.create("title",
                "isbn",
                "description",
                10,
                status,
                null,
                null);
    }

    private Book getBookWithId(UUID bookId) {
        return Book.createWithId(
                bookId,
                "title",
                "isbn",
                "description",
                10,
                BookStatus.ACTIVE,
                null,
                null);
    }

    @Test
    void getBookEntityById_shouldReturnBook_whenBookExists() {
        // Arrange
        UUID bookId = UUID.randomUUID();

        Book book = Book.create("title",
                "isbn",
                "description",
                10,
                BookStatus.ACTIVE,
                null,
                null);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        Book bookEntityById = bookService.getBookEntityById(bookId);

        // Assert
        assertThat(bookEntityById)
                .usingRecursiveComparison()
                .isEqualTo(book);

        Mockito.verify(bookRepository).findById(bookId);
    }

    @Test
    void getBookEntityById_shouldThrowNotfoundException_whenBookDoesNotExist() {

        // Arrange & Act
        UUID bookId = UUID.randomUUID();

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThatThrownBy(() -> bookService.getBookEntityById(bookId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Book not found with id " + bookId);

        Mockito.verify(bookRepository).findById(bookId);
    }


    @Test
    void activateBookById_shouldActivateBook_whenBookIsInactive() {
        // Arrange
        Book inactiveBook = getBook(BookStatus.INACTIVE);

        when(bookRepository.findById(inactiveBook.getId())).thenReturn(Optional.of(inactiveBook));

        // Act
        bookService.activateBookById(inactiveBook.getId());

        // Assert
        assertThat(inactiveBook.getStatus()).isEqualTo(BookStatus.ACTIVE);
        Mockito.verify(bookRepository).findById(inactiveBook.getId());
    }

    @Test
    void activateBookById_shouldThrowNotfoundException_whenBookDoesNotExist() {
        // Arrange
        UUID bookId = UUID.randomUUID();

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThatThrownBy(() -> bookService.activateBookById(bookId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Book not found with id " + bookId);

        Mockito.verify(bookRepository).findById(bookId);
    }

    @Test
    void activateBookById_shouldThrowInvalidOperationException_whenBookIsNotInactive() {
        // Arrange
        Book activeBook = getBook(BookStatus.ACTIVE);

        when(bookRepository.findById(activeBook.getId())).thenReturn(Optional.of(activeBook));

        // Act & Assert
        Assertions.assertThatThrownBy(() -> bookService.activateBookById(activeBook.getId()))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("Only inactive books can be activated. Current status: " + activeBook.getStatus());

        Mockito.verify(bookRepository).findById(activeBook.getId());
    }

    @Test
    void deactivateBookById_shouldDeactivateBook_whenBookIsActive() {
        // Arrange
        Book activeBook = getBook(BookStatus.ACTIVE);
        when(bookRepository.findById(activeBook.getId())).thenReturn(Optional.of(activeBook));

        // Act
        bookService.deactivateBookById(activeBook.getId());

        // Assert
        assertThat(activeBook.getStatus()).isEqualTo(BookStatus.INACTIVE);
        Mockito.verify(bookRepository).findById(activeBook.getId());
    }

    @Test
    void deactivateBookById_shouldThrowNotfoundException_whenBookDoesNotExist() {
        // Arrange
        UUID bookId = UUID.randomUUID();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThatThrownBy(() -> bookService.deactivateBookById(bookId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Book not found with id " + bookId);

        Mockito.verify(bookRepository).findById(bookId);
    }


    @Test
    void deactivateBookById_shouldThrowInvalidOperationException_whenBookIsNotActive() {
        // Arrange
        Book inactiveBook = getBook(BookStatus.INACTIVE);
        when(bookRepository.findById(inactiveBook.getId())).thenReturn(Optional.of(inactiveBook));

        // Act & Assert
        Assertions.assertThatThrownBy(() -> bookService.deactivateBookById(inactiveBook.getId()))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("Only active books can be deactivated. Current status: " + inactiveBook.getStatus());
        Mockito.verify(bookRepository).findById(inactiveBook.getId());
    }

    @Test
    void suspendBookById_shouldSuspendBook_whenBookIsInactive() {
        // Arrange
        Book inactiveBook = getBook(BookStatus.INACTIVE);
        when(bookRepository.findById(inactiveBook.getId())).thenReturn(Optional.of(inactiveBook));

        // Act
        bookService.suspendBookById(inactiveBook.getId());

        // Assert
        assertThat(inactiveBook.getStatus()).isEqualTo(BookStatus.SUSPENDED);
        Mockito.verify(bookRepository).findById(inactiveBook.getId());
    }

    @Test
    void suspendBookById_shouldThrowNotfoundException_whenBookDoesNotExist() {
        // Arrange
        UUID bookId = UUID.randomUUID();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThatThrownBy(() -> bookService.suspendBookById(bookId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Book not found with id " + bookId);

        Mockito.verify(bookRepository).findById(bookId);
    }


    @Test
    void suspendBookById_shouldThrowInvalidOperationException_whenBookIsNotInactive() {
        // Arrange
        Book activeBook = getBook(BookStatus.ACTIVE);

        when(bookRepository.findById(activeBook.getId())).thenReturn(Optional.of(activeBook));

        // Act & Assert
        Assertions.assertThatThrownBy(() -> bookService.suspendBookById(activeBook.getId()))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("Only inactive books can be suspended. Current status: " + activeBook.getStatus());

        Mockito.verify(bookRepository).findById(activeBook.getId());
    }

    @Test
    void deleteBookById_shouldDeleteBook_whenBookIsSuspendedAndOnLoanIsZero() {
        // Arrange
        UUID bookId = UUID.randomUUID();

        Book book = getBook(BookStatus.SUSPENDED);

        BookStatus expectedStatus = BookStatus.DELETED;

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        bookService.deleteBookById(bookId);

        // Assert
        assertThat(book.getStatus()).isEqualTo(expectedStatus);

        Mockito.verify(bookRepository).findById(bookId);
    }

    @Test
    void deleteBookById_shouldThrowNotfoundException_whenBookDoesNotExist() {

        // Arrange
        UUID bookId = UUID.randomUUID();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> bookService.deleteBookById(bookId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Book not found with id " + bookId);

        Mockito.verify(bookRepository).findById(bookId);
    }

    @Test
    void deleteBookById_shouldThrowInvalidOperationException_whenOnLoanGreaterThanZero() {

        // Arrange
        UUID bookId = UUID.randomUUID();

        Book book = getBook(BookStatus.SUSPENDED);

        book.setAvailableQuantity(5);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act & Assert
        assertThatThrownBy(() -> bookService.deleteBookById(bookId))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("Cannot delete the book because one or more copies are still on loan.");

        Mockito.verify(bookRepository).findById(bookId);
    }

    @Test
    void deleteBookById_shouldThrowInvalidOperationException_whenBookStatusIsNotSuspended() {

        // Arrange
        UUID bookId = UUID.randomUUID();

        Book book = getBook(BookStatus.ACTIVE);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act & Assert
        assertThatThrownBy(() -> bookService.deleteBookById(bookId))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("Only suspended books can be deleted. Current status: " + book.getStatus());

        Mockito.verify(bookRepository).findById(bookId);
    }

    @Test
    void createBook_shouldCreateBookWithoutAuthorsAndCategories_whenIdSetsAreNullInValidRequest() {
        // Arrange
        CreateBookRequest request = new CreateBookRequest(
                "The Pragmatic Programmer",
                "978-1-56619-909-4",
                """
                        For twenty years, this masterpiece have helped a generation of programmers.
                        """,
                10,
                null,
                null);
        UUID bookId = UUID.randomUUID();

        Book book = Book.createWithId(
                bookId,
                "The Pragmatic Programmer",
                "978-1-56619-909-4",
                """
                        For twenty years, this masterpiece have helped a generation of programmers.
                        """,
                10,
                BookStatus.INACTIVE,
                null,
                null
        );

        BookResponse expectedResponse = new BookResponse(
                bookId,
                "The Pragmatic Programmer",
                "978-1-56619-909-4",
                """
                        For twenty years, this masterpiece have helped a generation of programmers.
                        """,
                10,
                10,
                Collections.emptySet(),
                Collections.emptySet()
        );

        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        when(authorService.getAuthorSetByIdSet(Collections.emptySet())).thenReturn(new HashSet<>());
        when(categoryService.getCategorySetByIdSet(Collections.emptySet())).thenReturn(new HashSet<>());

        // Act
        BookResponse actualResponse = bookService.createBook(request);

        // Assert
        assertThat(actualResponse).isEqualTo(expectedResponse);
        Mockito.verify(bookRepository).save(Mockito.any(Book.class));
    }


    @Test
    void createBook_shouldCreateBookWithAuthorsAndCategories_whenIdSetsProvidedInValidRequest() {
        // Arrange
        UUID authorId1 = UUID.randomUUID();
        UUID authorId2 = UUID.randomUUID();

        UUID categoryId1 = UUID.randomUUID();
        UUID categoryId2 = UUID.randomUUID();

        CreateBookRequest request = new CreateBookRequest(
                "The Pragmatic Programmer",
                "978-1-56619-909-4",
                """
                        For twenty years, this masterpiece have helped a generation of programmers.
                        """,
                10,
                Set.of(authorId1, authorId2),
                Set.of(categoryId1, categoryId2)
        );
        UUID bookId = UUID.randomUUID();

        Author author1 = Author.createWithId(authorId1, "Mahabbat", "Gozalov", "Programmer", "mgzlovcontact@gmailcom");
        Author author2 = Author.createWithId(authorId2, "Mutalib", "Gozalov", "Programmer", "mgzlovcontact2@gmail.com");

        Category category1 = Category.createWithId(categoryId1, "Science", "It is all about science");
        Category category2 = Category.createWithId(categoryId2, "Fiction", "It is all about fiction");

        Book book = Book.createWithId(
                bookId,
                "The Pragmatic Programmer",
                "978-1-56619-909-4",
                """
                        For twenty years, this masterpiece have helped a generation of programmers.
                        """,
                10,
                BookStatus.INACTIVE,
                Set.of(author1, author2),
                Set.of(category1, category2)
        );

        BookResponse expectedResponse = new BookResponse(
                bookId,
                "The Pragmatic Programmer",
                "978-1-56619-909-4",
                """
                        For twenty years, this masterpiece have helped a generation of programmers.
                        """,
                10,
                10,
                Set.of(authorId1, authorId2),
                Set.of(categoryId1, categoryId2)
        );


        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        when(authorService.getAuthorSetByIdSet(Set.of(authorId1, authorId2))).thenReturn(new HashSet<>(Set.of(author1, author2)));
        when(categoryService.getCategorySetByIdSet(Set.of(categoryId1, categoryId2))).thenReturn(new HashSet<>(Set.of(category1, category2)));

        // Act
        BookResponse actualResponse = bookService.createBook(request);

        // Assert
        assertThat(actualResponse)
                .isEqualTo(expectedResponse);

        Mockito.verify(bookRepository).save(Mockito.any(Book.class));
    }

    @Test
    void createBook_shouldThrowNotFoundException_whenAnAuthorDoesNotExistsWithGivenAuthorIdSet() {

        // Arrange
        UUID authorId1 = UUID.randomUUID();
        UUID authorId2 = UUID.randomUUID();

        CreateBookRequest request = new CreateBookRequest(
                "The Pragmatic Programmer",
                "978-1-56619-909-4",
                """
                        For twenty years, this masterpiece have helped a generation of programmers.
                        """,
                10,
                Set.of(authorId1, authorId2),
                null
        );

        when(authorService.getAuthorSetByIdSet(Set.of(authorId1, authorId2))).thenThrow(NotFoundException.class);

        // Act & Assert
        Assertions.assertThatThrownBy(() -> bookService.createBook(request))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void createBook_shouldThrowNotFoundException_whenACategoryDoesNotExistsWithGivenCategoryIdSet() {

        // Arrange
        UUID categoryId1 = UUID.randomUUID();
        UUID categoryId2 = UUID.randomUUID();

        CreateBookRequest request = new CreateBookRequest(
                "The Pragmatic Programmer",
                "978-1-56619-909-4",
                """
                        For twenty years, this masterpiece have helped a generation of programmers.
                        """,
                10,
                null,
                Set.of(categoryId1, categoryId2)
        );

        when(authorService.getAuthorSetByIdSet(Collections.emptySet())).thenReturn(Collections.emptySet());
        when(categoryService.getCategorySetByIdSet(Set.of(categoryId1, categoryId2))).thenThrow(NotFoundException.class);
        // Act & Assert
        Assertions.assertThatThrownBy(() -> bookService.createBook(request))
                .isInstanceOf(NotFoundException.class);
    }


    @Test
    void updateBook_shouldReturnBookResponse_whenRequestIsValid() {
        // Arrange
        UUID bookId = UUID.randomUUID();

        UpdateBookRequest request = new UpdateBookRequest("title", "isbn", "description");

        Book book = getBookWithId(bookId);

        when(bookRepository.findBookByIdWithAuthorsAndCategories(bookId)).thenReturn(Optional.of(book));

        BookResponse expectedResponse = new BookResponse(bookId,
                "title",
                "isbn",
                "description",
                10,
                10,
                Collections.emptySet(),
                Collections.emptySet()
        );

        // Act
        BookResponse actualResponse = bookService.updateBook(bookId, request);

        // Assert
        assertThat(actualResponse).isEqualTo(expectedResponse);

        Mockito.verify(bookRepository).findBookByIdWithAuthorsAndCategories(bookId);
    }

    @Test
    void updateBook_shouldThrowNotFoundException_whenBookDoesNotExistsWithGivenId() {
        // Arrange
        UUID bookId = UUID.randomUUID();

        UpdateBookRequest request = new UpdateBookRequest("title", "isbn", "description");

        when(bookRepository.findBookByIdWithAuthorsAndCategories(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> bookService.updateBook(bookId, request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Book not found with id " + bookId);

        Mockito.verify(bookRepository).findBookByIdWithAuthorsAndCategories(bookId);
    }

    @Test
    void updateBookFullQuantity_shouldRetuenBookResponse_whenRequestIsValid() {

        UUID bookId = UUID.randomUUID();

        Integer newFullQuantity = 20;

        Book book = getBookWithId(bookId);

        when(bookRepository.findBookByIdWithAuthorsAndCategories(bookId)).thenReturn(Optional.of(book));

        BookResponse actual = bookService.updateBookFullQuantity(bookId, newFullQuantity);

        BookResponse expected = new BookResponse(
                bookId,
                book.getTitle(),
                book.getIsbn(),
                book.getDescription(),
                book.getFullQuantity(),
                book.getAvailableQuantity(),
                Collections.emptySet(),
                Collections.emptySet()
        );

        verify(bookRepository).findBookByIdWithAuthorsAndCategories(bookId);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void updateBookFullQuantity_shouldThrowNotFoundException_whenBookDoesNotExistsWithGivenId() {
        UUID bookId = UUID.randomUUID();

        when(bookRepository.findBookByIdWithAuthorsAndCategories(bookId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.updateBookFullQuantity(bookId, 20))
                .isInstanceOf(NotFoundException.class);

        Mockito.verify(bookRepository).findBookByIdWithAuthorsAndCategories(bookId);
    }


    @Test
    void updateBookFullQuantity_shouldThrowInvalidOperationException_whenNewFullQuantityIsLessThanOnLoans() {
        UUID bookId = UUID.randomUUID();
        Integer newFullQuantity = 4;
        Book book = getBookWithId(bookId);

        book.setAvailableQuantity(5);

        when(bookRepository.findBookByIdWithAuthorsAndCategories(bookId)).thenReturn(Optional.of(book));

        assertThatThrownBy(() -> bookService.updateBookFullQuantity(bookId, newFullQuantity))
                .isInstanceOf(InvalidOperationException.class);

        Mockito.verify(bookRepository).findBookByIdWithAuthorsAndCategories(bookId);
    }
}


