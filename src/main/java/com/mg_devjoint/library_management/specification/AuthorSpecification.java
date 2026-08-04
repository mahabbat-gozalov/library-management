package com.mg_devjoint.library_management.specification;

import com.mg_devjoint.library_management.model.Author;
import com.mg_devjoint.library_management.model.Book;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;
import java.util.UUID;

public final class AuthorSpecification {
    private AuthorSpecification() {
    }

    public static Specification<Author> hasFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            return null;
        }

        String normalizedFirstName = firstName.trim().toLowerCase();

        return (root, query, cb) -> cb.like(cb.lower(root.get("firstName")), "%" + normalizedFirstName + "%");
    }

    public static Specification<Author> hasLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            return null;
        }

        String normalizedLastName = lastName.trim().toLowerCase();

        return (root, query, cb) -> cb.like(cb.lower(root.get("lastName")), "%" + normalizedLastName + "%");
    }

    public static Specification<Author> hasSummary(String summary) {
        if (summary == null || summary.isBlank()) {
            return null;
        }

        String normalizedSummary = summary.trim().toLowerCase();

        return (root, query, cb) -> cb.like(cb.lower(root.get("summary")), "%" + normalizedSummary + "%");
    }

    public static Specification<Author> hasEmail(String email) {
        if (email == null || email.isBlank()) {
            return null;
        }
        String normalizedEmail = email.trim().toLowerCase();

        return (root, query, cb) -> cb.like(cb.lower(root.get("email")), "%" + normalizedEmail + "%");
    }

    public static Specification<Author> hasBook(Set<UUID> bookIdSet) {

        if (bookIdSet == null || bookIdSet.isEmpty()) {
            return null;
        }

        return (root, query, cb) -> {
            if (query != null) query.distinct(true);

            Join<Author, Book> bookJoin = root.join("books");

            return bookJoin.get("id").in(bookIdSet);

        };
    }
}
