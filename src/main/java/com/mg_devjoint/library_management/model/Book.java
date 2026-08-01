package com.mg_devjoint.library_management.model;

import com.mg_devjoint.library_management.exception.InvalidEntityDataException;
import com.mg_devjoint.library_management.model.enums.BookStatus;
import jakarta.persistence.*;

import java.util.*;

import static com.mg_devjoint.library_management.model.validation.BookValidationUtils.*;
import static com.mg_devjoint.library_management.model.validation.CommonValidationUtils.validateIdCannotBeNull;

@Entity
@Table(name = "BOOKS")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "description")
    private String description;

    @Column(name = "full_quantity")
    private Integer fullQuantity;

    @Column(name = "available_quantity")
    private Integer availableQuantity;

    @Column(name = "status")
    private BookStatus status;

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    @ManyToMany
    @JoinTable(
            name = "book_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @OneToMany(mappedBy = "book")
    private Set<Loan> loans;

    protected Book() {
    }

    public static Book create(String title, String isbn, String description, Integer fullQuantity, BookStatus status, Set<Author> initialAuthorSet, Set<Category> initialCategorySet) {

        validateTitle(title);
        validateIsbn(isbn);
        validateDescription(description);
        validateFullQuantityNullOrNegative(fullQuantity);
        validateBookStatus(status);
        validateInitialAuthorSet(initialAuthorSet);
        validateInitialCategorySet(initialCategorySet);

        Book book = new Book();

        book.title = title;
        book.isbn = isbn;
        book.description = description;
        book.fullQuantity = fullQuantity;
        book.availableQuantity = fullQuantity;
        book.status = status;

        book.authors = new HashSet<>();
        book.categories = new HashSet<>();
        book.loans = new HashSet<>();

        if (initialAuthorSet != null) {
            initialAuthorSet.forEach(book::addAuthor);
        }

        if (initialCategorySet != null) {
            initialCategorySet.forEach(book::addCategory);
        }

        return book;
    }

    public static Book createWithId(UUID id, String title, String isbn, String description, Integer fullQuantity, BookStatus status, Set<Author> initialAuthorSet, Set<Category> initialCategorySet) {

        validateIdCannotBeNull(id);

        Book book = create(title, isbn, description, fullQuantity, status, initialAuthorSet, initialCategorySet);

        book.id = id;

        return book;
    }


    public void addCategory(Category category) {
        if (category == null || categories.contains(category)) return;
        categories.add(category);
        category.addBook(this);
    }

    public void addAuthor(Author author) {
        if (author == null || authors.contains(author)) return;
        authors.add(author);
        author.addBook(this);
    }

    public void removeCategory(Category category) {
        if (category == null || !categories.contains(category)) return;
        categories.remove(category);
        category.removeBook(this);
    }

    public void removeAuthor(Author author) {
        if (author == null || !authors.contains(author)) return;
        authors.remove(author);
        author.removeBook(this);
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getDescription() {
        return description;
    }

    public Integer getFullQuantity() {
        return fullQuantity;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public BookStatus getStatus() {
        return status;
    }

    public Set<Author> getAuthors() {
        return Collections.unmodifiableSet(authors);
    }

    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    public Set<Loan> getLoans() {
        return Collections.unmodifiableSet(loans);
    }

    public void setTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public void setIsbn(String isbn) {
        validateIsbn(isbn);
        this.isbn = isbn;
    }

    public void setDescription(String description) {
        validateDescription(description);
        this.description = description;
    }

    public void setFullQuantity(Integer fullQuantity) {
        validateFullQuantityNullOrNegative(fullQuantity);

        this.fullQuantity = fullQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        if (availableQuantity == null) {
            throw new InvalidEntityDataException("Available quantity cannot be null");
        }
        if (availableQuantity < 0) {
            throw new InvalidEntityDataException("Available quantity cannot be less than 0");
        }
        if (availableQuantity > this.fullQuantity) {
            throw new InvalidEntityDataException("Available quantity cannot exceeds full quantity");
        }
        this.availableQuantity = availableQuantity;
    }

    public void setStatus(BookStatus status) {
        validateBookStatus(status);
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return id != null && id.equals(book.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
