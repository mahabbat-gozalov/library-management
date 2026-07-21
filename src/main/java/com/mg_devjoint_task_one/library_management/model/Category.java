package com.mg_devjoint_task_one.library_management.model;

import com.mg_devjoint_task_one.library_management.exception.InvalidEntityDataException;
import jakarta.persistence.*;

import java.util.*;

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
    private Set<Book> books = new HashSet<>();

    protected Category() {
    }

    public static Category create(String name, String description, Set<Book> initialBookSet) {
        if (name == null) throw new InvalidEntityDataException("name cannot be null");

        Category category = new Category();
        category.setName(name);
        category.setDescription(description);

        if (initialBookSet != null)
            initialBookSet.forEach(category::addBook);

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
        this.name = name;
    }

    public void setDescription(String description) {
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
