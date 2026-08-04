package com.mg_devjoint.library_management.repository;

import com.mg_devjoint.library_management.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.*;

public interface BookRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {

    @EntityGraph(attributePaths = {"authors", "categories"})
    @Query(value = "select b from Book  b where b.id = :bookId")
    Optional<Book> findBookByIdWithAuthorsAndCategories(@Param("bookId") UUID bookId);


    @Query(value = "select b.id from Book b")
    Page<UUID> findAllBookIds(Pageable pageable);


    @NonNull
    @EntityGraph(attributePaths = {"authors", "categories"})
    @Query(value = "select b from Book b where b.id in :bookIdCollection")
    List<Book> findAllBooksWithAuthorsAndCategoriesAsPage(@Param(value = "bookIdCollection") Collection<UUID> bookIdCollection);

}
