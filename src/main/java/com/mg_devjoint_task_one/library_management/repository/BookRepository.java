package com.mg_devjoint_task_one.library_management.repository;

import com.mg_devjoint_task_one.library_management.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
