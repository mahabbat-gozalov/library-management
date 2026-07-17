package com.mg_devjoint_task_one.library_management.model;

import jakarta.persistence.*;

import java.util.*;

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
    private Set<Book> books = new HashSet<>();

    protected Author() {
    }

    public static Author create(String firstName, String lastName, String summary, String email, Set<Book> initialBookSet) {
        if (firstName == null || lastName == null)
            throw new IllegalArgumentException("firstName and lastName must be non-null");


        Author author = new Author();

        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setSummary(summary);
        author.setEmail(email);
        if (initialBookSet != null)
            initialBookSet.forEach(author::addBook);


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
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setEmail(String email) {
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
