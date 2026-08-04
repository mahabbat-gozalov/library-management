package com.mg_devjoint.library_management.repository;

import com.mg_devjoint.library_management.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> , JpaSpecificationExecutor<Author> {
}
