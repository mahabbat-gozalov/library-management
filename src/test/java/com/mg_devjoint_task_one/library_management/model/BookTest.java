package com.mg_devjoint_task_one.library_management.model;


import com.mg_devjoint_task_one.library_management.exception.InvalidEntityDataException;
import com.mg_devjoint_task_one.library_management.model.enums.BookStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class BookTest {
    @Test
    void shouldCreateBookWithGivenFields() {
        String title = "Dune";
        String isbn = "978-0-441-01359-3";
        String description = "A science fiction novel";
        Integer fullQuantity = 5;
        BookStatus status = BookStatus.INACTIVE;

        Book book = Book.create(title, isbn, description, fullQuantity, status, null, null);

        assertThat(book.getTitle()).isEqualTo("Dune");
        assertThat(book.getIsbn()).isEqualTo("978-0-441-01359-3");
        assertThat(book.getDescription()).isEqualTo("A science fiction novel");
        assertThat(book.getFullQuantity()).isEqualTo(5);
        assertThat(book.getStatus()).isEqualTo(BookStatus.INACTIVE);
    }

    @Test
    void shouldSetAvailableQuantityEqualToFullQuantityOnCreate() {
        String title = "Dune";
        String isbn = "978-0-441-01359-3";
        String description = "A science fiction novel";
        Integer fullQuantity = 5;
        BookStatus status = BookStatus.INACTIVE;

        Book book = Book.create(title, isbn, description, fullQuantity, status, null, null);

        assertThat(book.getAvailableQuantity()).isEqualTo(5);

    }

    @Test
    void shouldThrowInvalidEntityDataExceptionWhenTitleIsNull() {
        String title = null;
        String isbn = "978-0-441-01359-3";
        String description = "A science fiction novel";
        Integer fullQuantity = 5;
        BookStatus status = BookStatus.INACTIVE;

        assertThatThrownBy(() -> Book.create(
                title,
                isbn,
                description,
                fullQuantity,
                status,
                null,
                null))
                .isInstanceOf(InvalidEntityDataException.class)
                .hasMessage("title cannot be null");


    }

    @Test
    void shouldThrowInvalidEntityDataExceptionWhenIsbnIsNull() {
        String title = "Dune";
        String isbn = null;
        String description = "A science fiction novel";
        Integer fullQuantity = 5;
        BookStatus status = BookStatus.INACTIVE;

        assertThatThrownBy(() -> Book.create(
                title,
                isbn,
                description,
                fullQuantity,
                status,
                null,
                null))
                .isInstanceOf(InvalidEntityDataException.class)
                .hasMessage("isbn cannot be null");


    }

    @Test
    void shouldThrowInvalidEntityDataExceptionWhenFullQuantityIsNull() {
        String title = "Dune";
        String isbn = "978-0-441-01359-3";
        String description = "A science fiction novel";
        Integer fullQuantity = null;
        BookStatus status = BookStatus.INACTIVE;

        assertThatThrownBy(() -> Book.create(
                title,
                isbn,
                description,
                fullQuantity,
                status,
                null,
                null))
                .isInstanceOf(InvalidEntityDataException.class)
                .hasMessage("fullQuantity cannot be null");


    }

    @Test
    void shouldThrowInvalidEntityDataExceptionWhenBookStatusIsNull() {
        String title = "Dune";
        String isbn = "978-0-441-01359-3";
        String description = "A science fiction novel";
        Integer fullQuantity = 5;
        BookStatus status = null;

        assertThatThrownBy(() -> Book.create(
                title,
                isbn,
                description,
                fullQuantity,
                status,
                null,
                null))
                .isInstanceOf(InvalidEntityDataException.class)
                .hasMessage("bookStatus cannot be null");

    }

    @Test
    void shouldDecreaseAvailableQuantityWhenQuantityIsAvailable() {
        String title = "Dune";
        String isbn = "978-0-441-01359-3";
        String description = "A science fiction novel";
        Integer fullQuantity = 5;
        BookStatus status = BookStatus.INACTIVE;

        Book book = Book.create(
                title,
                isbn,
                description,
                fullQuantity,
                status,
                null,
                null);

        book.decreaseAvailableQuantity();

        assertThat(book.getAvailableQuantity()).isEqualTo(4);

    }

    @Test
    void shouldThrowExceptionWhenAvailableQuantityIsZero() {
        String title = "Dune";
        String isbn = "978-0-441-01359-3";
        String description = "A science fiction novel";
        Integer fullQuantity = 0;
        BookStatus status = BookStatus.INACTIVE;

        Book book = Book.create(
                title,
                isbn,
                description,
                fullQuantity,
                status,
                null,
                null);


        assertThatThrownBy(book::decreaseAvailableQuantity)
                .isInstanceOf(InvalidEntityDataException.class)
                .hasMessage("Not enough quantity");
    }


    @Test
    void shouldDecreaseAvailableQuantityWhenAvailableQuantityIsOne() {
        String title = "Dune";
        String isbn = "978-0-441-01359-3";
        String description = "A science fiction novel";
        Integer fullQuantity = 1;
        BookStatus status = BookStatus.INACTIVE;

        Book book = Book.create(
                title,
                isbn,
                description,
                fullQuantity,
                status,
                null,
                null);

        book.decreaseAvailableQuantity();

        assertThat(book.getAvailableQuantity()).isEqualTo(0);

    }

    @Test
    void shouldIncreaseAvailableQuantityWhenAvailableQuantityIsNotEqualToFullQuantity() {
        String title = "Dune";
        String isbn = "978-0-441-01359-3";
        String description = "A science fiction novel";
        Integer fullQuantity = 10;
        BookStatus status = BookStatus.INACTIVE;

        Book book = Book.create(
                title,
                isbn,
                description,
                fullQuantity,
                status,
                null,
                null);

        book.setAvailableQuantity(5);

        book.increaseAvailableQuantity();

        assertThat(book.getAvailableQuantity()).isEqualTo(6);
    }

    @Test
    void shouldThrowInvalidEntityDataExceptionWhenAvailableQuantityIsEqualToFullQuantity() {
        String title = "Dune";
        String isbn = "978-0-441-01359-3";
        String description = "A science fiction novel";
        Integer fullQuantity = 10;
        BookStatus status = BookStatus.INACTIVE;

        Book book = Book.create(
                title,
                isbn,
                description,
                fullQuantity,
                status,
                null,
                null);

        assertThatThrownBy(book::increaseAvailableQuantity)
                .isInstanceOf(InvalidEntityDataException.class)
                .hasMessage("Available quantity exceeds full quantity");
    }

    @Test
    void shouldIncreaseAvailableQuantityWhenAvailableQuantityIsOneLessThanFullQuantity() {
        String title = "Dune";
        String isbn = "978-0-441-01359-3";
        String description = "A science fiction novel";
        Integer fullQuantity = 10;
        BookStatus status = BookStatus.INACTIVE;

        Book book = Book.create(
                title,
                isbn,
                description,
                fullQuantity,
                status,
                null,
                null);

        book.setAvailableQuantity(9);

        book.increaseAvailableQuantity();

        assertThat(book.getAvailableQuantity()).isEqualTo(10);
    }


    //TODO: test add and remove methods

}
