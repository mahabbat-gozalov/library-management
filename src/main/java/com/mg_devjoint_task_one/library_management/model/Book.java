package com.mg_devjoint_task_one.library_management.model;

import com.mg_devjoint_task_one.library_management.exception.InvalidEntityDataException;
import jakarta.persistence.*;

import java.util.*;

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

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "book_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "book")
    private Set<Loan> loans = new HashSet<>();

    protected Book() {
    }

    public static Book create(String title, String isbn, String description, Integer fullQuantity, Set<Author> initialAuthorSet, Set<Category> initialCategorySet) {
        if (title == null) throw new InvalidEntityDataException("title cannot be null");
        if (isbn == null) throw new InvalidEntityDataException("isbn cannot be null");
        if (fullQuantity == null) throw new InvalidEntityDataException("fullQuantity cannot be null");


        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setDescription(description);
        book.setFullQuantity(fullQuantity);
        book.setAvailableQuantity(fullQuantity);

        if (initialCategorySet != null)
            initialCategorySet.forEach(book::addCategory);

        if (initialAuthorSet != null)
            initialAuthorSet.forEach(book::addAuthor);

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
        this.title = title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFullQuantity(Integer fullQuantity) {
        this.fullQuantity = fullQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
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
