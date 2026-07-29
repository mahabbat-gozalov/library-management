package com.mg_devjoint.library_management.model;

import jakarta.persistence.*;

import java.util.*;

import static com.mg_devjoint.library_management.model.validation.CategoryValidationUtils.validateCategoryName;
import static com.mg_devjoint.library_management.model.validation.CategoryValidationUtils.validateDescription;
import static com.mg_devjoint.library_management.model.validation.CommonValidationUtils.validateIdCannotBeNull;

@Entity
@Table(name = "CATEGORIES")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Book> books;

    protected Category() {
    }

    public static Category create(String name, String description) {

        validateCategoryName(name);
        validateDescription(description);

        Category category = new Category();

        category.name = name;

        category.description = description;

        category.books = new HashSet<>();

        return category;
    }

    public static Category createWithId(UUID id, String name, String description) {

        validateIdCannotBeNull(id);

        Category category = create(name, description);

        category.id = id;

        return category;
    }

    public void addBook(Book book) {
        if (book == null || this.books.contains(book)) return;
        this.books.add(book);
        book.addCategory(this);
    }

    public void removeBook(Book book) {
        if (book == null || !this.books.contains(book)) return;
        this.books.remove(book);
        book.removeCategory(this);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<Book> getBooks() {
        return Collections.unmodifiableSet(books);
    }

    public void setName(String name) {
        validateCategoryName(name);
        this.name = name;
    }

    public void setDescription(String description) {
        validateDescription(description);
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return id != null && id.equals(category.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
