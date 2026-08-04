package com.mg_devjoint.library_management.specification;

import com.mg_devjoint.library_management.model.*;
import com.mg_devjoint.library_management.model.enums.BookStatus;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;
import java.util.UUID;

public final class BookSpecification {
    private BookSpecification() {
    }

    public static Specification<Book> hasTitle(String title) {
        if (title == null || title.isEmpty()) return null;

        title = title.trim().toLowerCase();

        String finalTitle = title;

        return (root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + finalTitle + "%");
    }

    public static Specification<Book> hasStatus(BookStatus status) {
        if (status == null) return null;

        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<Book> hasCategory(Set<UUID> categoryIdSet) {
        if (categoryIdSet == null || categoryIdSet.isEmpty()) return null;

        return (root, query, cb) -> {
            if (query != null) query.distinct(true);

            Join<Book, Category> categoryJoin = root.join("categories");

            return categoryJoin.get("id").in(categoryIdSet);
        };
    }

    public static Specification<Book> hasAuthor(Set<UUID> authorIdSet) {
        if (authorIdSet == null || authorIdSet.isEmpty()) return null;

        return (root, query, criteriaBuilder) -> {
            if (query != null) query.distinct(true);

            Join<Book, Author> authorJoin = root.join("authors");

            return authorJoin.get("id").in(authorIdSet);
        };
    }

    public static Specification<Book> hasFullQuantityBetween(Integer min, Integer max) {
        if (min == null && max == null) {
            return null;
        }

        return (root, query, cb) -> {
            if (min != null && max != null) {
                return cb.between(root.get("fullQuantity"), min, max);
            }

            if (min != null) {
                return cb.greaterThanOrEqualTo(root.get("fullQuantity"), min);
            }

            return cb.lessThanOrEqualTo(root.get("fullQuantity"), max);
        };
    }

    public static Specification<Book> hasAvailableQuantityBetween(Integer min, Integer max) {
        if (min == null && max == null) {
            return null;
        }

        return (root, query, cb) -> {
            if (min != null && max != null) {
                return cb.between(root.get("availableQuantity"), min, max);
            }

            if (min != null) {
                return cb.greaterThanOrEqualTo(root.get("availableQuantity"), min);
            }

            return cb.lessThanOrEqualTo(root.get("availableQuantity"), max);
        };
    }

}
