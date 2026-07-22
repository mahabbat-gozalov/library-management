package com.mg_devjoint_task_one.library_management.repository;

import com.mg_devjoint_task_one.library_management.model.Book;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    @EntityGraph(attributePaths = {"authors", "categories"})
    @Query(value = "select b from Book  b where b.id = :bookId")
    Optional<Book> findBookByIdWithAuthorsAndCategories(@Param("bookId") UUID bookId);


}
