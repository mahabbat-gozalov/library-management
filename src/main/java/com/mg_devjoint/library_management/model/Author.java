package com.mg_devjoint.library_management.model;

import jakarta.persistence.*;

import java.util.*;

import static com.mg_devjoint.library_management.model.validation.AuthorValidationUtils.validateSummary;
import static com.mg_devjoint.library_management.model.validation.CommonValidationUtils.*;

@Entity
@Table(name = "AUTHORS")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "summary")
    private String summary;

    @Column(name = "email")
    private String email;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;

    protected Author() {
    }

    public static Author create(String firstName, String lastName, String summary, String email) {

        validateName(firstName);

        validateSurname(lastName);

        validateSummary(summary);

        if (email != null) validateEmail(email);

        Author author = new Author();

        author.firstName = firstName;
        author.lastName = lastName;
        author.summary = summary;
        author.email = email;
        author.books = new HashSet<>();

        return author;
    }

    public static Author createWithId(UUID id, String firstName, String lastName, String summary, String email) {
        validateIdCannotBeNull(id);

        Author author = create(firstName, lastName, summary, email);

        author.id = id;

        return author;
    }

    public void addBook(Book book) {
        if (book == null || this.books.contains(book)) return;
        books.add(book);
        book.addAuthor(this);
    }

    public void removeBook(Book book) {
        if (book == null || !this.books.contains(book)) return;
        books.remove(book);
        book.removeAuthor(this);
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSummary() {
        return summary;
    }

    public String getEmail() {
        return email;
    }

    public Set<Book> getBooks() {
        return Collections.unmodifiableSet(books);
    }

    public void setFirstName(String firstName) {
        validateName(firstName);
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        validateSurname(lastName);
        this.lastName = lastName;
    }

    public void setSummary(String summary) {
        validateSummary(summary);
        this.summary = summary;
    }

    public void setEmail(String email) {
        if (email != null) validateEmail(email);
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author author)) return false;
        return id != null && id.equals(author.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
