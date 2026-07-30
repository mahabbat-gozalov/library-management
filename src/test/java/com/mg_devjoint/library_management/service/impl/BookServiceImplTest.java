package com.mg_devjoint.library_management.service.impl;

import com.mg_devjoint.library_management.dto.request.create.CreateBookRequest;
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

    @Test
    void getBookEntityById_shouldReturnBook_whenBookExists() {
        // Arrange
        UUID bookId = UUID.randomUUID();

        Book book = Book.create("The Lord of The Rings: The Two Tower",
                "9788845292255",
                "Most beautiful book of the world",
                10,
                BookStatus.ACTIVE,
                null,
                null);

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        Book bookEntityById = bookService.getBookEntityById(bookId);

        // Assert
        Assertions.assertThat(bookEntityById)
                .usingRecursiveComparison()
                .isEqualTo(book);

        Mockito.verify(bookRepository).findById(bookId);
    }

    @Test
    void getBookEntityById_shouldThrowNotfoundException_whenBookDoesNotExist() {

        // Arrange & Act
        UUID bookId = UUID.randomUUID();

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThatThrownBy(() -> bookService.getBookEntityById(bookId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Book not found with id " + bookId);

        Mockito.verify(bookRepository).findById(bookId);
    }


    @Test
    void activateBookById_shouldActivateBook_whenBookIsInactive() {
        // Arrange
        Book inactiveBook = getInactiveBook();

        Mockito.when(bookRepository.findById(inactiveBook.getId())).thenReturn(Optional.of(inactiveBook));

        // Act
        bookService.activateBookById(inactiveBook.getId());

        // Assert
        Assertions.assertThat(inactiveBook.getStatus()).isEqualTo(BookStatus.ACTIVE);
        Mockito.verify(bookRepository).findById(inactiveBook.getId());
    }

    @Test
    void activateBookById_shouldThrowNotfoundException_whenBookDoesNotExist() {
        // Arrange
        UUID bookId = UUID.randomUUID();

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThatThrownBy(() -> bookService.activateBookById(bookId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Book not found with id " + bookId);

        Mockito.verify(bookRepository).findById(bookId);
    }

    @Test
    void activateBookById_shouldThrowInvalidOperationException_whenBookIsNotInactive() {
        // Arrange
        Book activeBook = getActiveBook();

        Mockito.when(bookRepository.findById(activeBook.getId())).thenReturn(Optional.of(activeBook));

        // Act & Assert
        Assertions.assertThatThrownBy(() -> bookService.activateBookById(activeBook.getId()))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("Only inactive books can be activated. Current status: " + activeBook.getStatus());

        Mockito.verify(bookRepository).findById(activeBook.getId());
    }

    @Test
    void deactivateBookById_shouldDeactivateBook_whenBookIsActive() {
        // Arrange
        Book activeBook = getActiveBook();
        Mockito.when(bookRepository.findById(activeBook.getId())).thenReturn(Optional.of(activeBook));

        // Act
        bookService.deactivateBookById(activeBook.getId());

        // Assert
        Assertions.assertThat(activeBook.getStatus()).isEqualTo(BookStatus.INACTIVE);
        Mockito.verify(bookRepository).findById(activeBook.getId());
    }

    @Test
    void deactivateBookById_shouldThrowNotfoundException_whenBookDoesNotExist() {
        // Arrange
        UUID bookId = UUID.randomUUID();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThatThrownBy(() -> bookService.deactivateBookById(bookId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Book not found with id " + bookId);

        Mockito.verify(bookRepository).findById(bookId);
    }


    @Test
    void deactivateBookById_shouldThrowInvalidOperationException_whenBookIsNotActive() {
        // Arrange
        Book inactiveBook = getInactiveBook();
        Mockito.when(bookRepository.findById(inactiveBook.getId())).thenReturn(Optional.of(inactiveBook));

        // Act & Assert
        Assertions.assertThatThrownBy(() -> bookService.deactivateBookById(inactiveBook.getId()))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("Only active books can be deactivated. Current status: " + inactiveBook.getStatus());
        Mockito.verify(bookRepository).findById(inactiveBook.getId());
    }

    @Test
    void suspendBookById_shouldSuspendBook_whenBookIsInactive() {
        // Arrange
        Book inactiveBook = getInactiveBook();
        Mockito.when(bookRepository.findById(inactiveBook.getId())).thenReturn(Optional.of(inactiveBook));

        // Act
        bookService.suspendBookById(inactiveBook.getId());

        // Assert
        Assertions.assertThat(inactiveBook.getStatus()).isEqualTo(BookStatus.SUSPENDED);
        Mockito.verify(bookRepository).findById(inactiveBook.getId());
    }

    @Test
    void suspendBookById_shouldThrowNotfoundException_whenBookDoesNotExist() {
        // Arrange
        UUID bookId = UUID.randomUUID();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThatThrownBy(() -> bookService.suspendBookById(bookId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Book not found with id " + bookId);

        Mockito.verify(bookRepository).findById(bookId);
    }


    @Test
    void suspendBookById_shouldThrowInvalidOperationException_whenBookIsNotInactive() {
        // Arrange
        Book activeBook = getActiveBook();

        Mockito.when(bookRepository.findById(activeBook.getId())).thenReturn(Optional.of(activeBook));

        // Act & Assert
        Assertions.assertThatThrownBy(() -> bookService.suspendBookById(activeBook.getId()))
                .isInstanceOf(InvalidOperationException.class)
                .hasMessage("Only inactive books can be suspended. Current status: " + activeBook.getStatus());

        Mockito.verify(bookRepository).findById(activeBook.getId());
    }

    private Book getInactiveBook() {
        return Book.create("The Lord of The Rings: The Two Tower",
                "9788845292255",
                "Most beautiful book of the world",
                10,
                BookStatus.INACTIVE,
                null,
                null);
    }

    private Book getActiveBook() {
        return Book.create("The Lord of The Rings: The Two Tower",
                "9788845292255",
                "Most beautiful book of the world",
                10,
                BookStatus.ACTIVE,
                null,
                null);
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

        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        Mockito.when(authorService.getAuthorSetByIdSet(Collections.emptySet())).thenReturn(new HashSet<>());
        Mockito.when(categoryService.getCategorySetByIdSet(Collections.emptySet())).thenReturn(new HashSet<>());

        // Act
        BookResponse actualResponse = bookService.createBook(request);

        // Assert
        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
        Mockito.verify(bookRepository).save(Mockito.any(Book.class));
    }


    @Test
    void createBook_shouldCreateBookWithAuthorsAndCategories_whenIdSetsProvidedInValidRequest() {
        UUID authorId1 = UUID.randomUUID();
        UUID authorId2 = UUID.randomUUID();

        UUID categoryId1 = UUID.randomUUID();
        UUID categoryId2 = UUID.randomUUID();

        // Arrange
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


        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        Mockito.when(authorService.getAuthorSetByIdSet(Set.of(authorId1, authorId2))).thenReturn(new HashSet<>(Set.of(author1, author2)));
        Mockito.when(categoryService.getCategorySetByIdSet(Set.of(categoryId1, categoryId2))).thenReturn(new HashSet<>(Set.of(category1, category2)));

        // Act
        BookResponse actualResponse = bookService.createBook(request);

        // Assert
        Assertions.assertThat(actualResponse)
                .isEqualTo(expectedResponse);

        Mockito.verify(bookRepository).save(Mockito.any(Book.class));
    }


}

