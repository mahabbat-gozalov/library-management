package com.mg_devjoint_task_one.library_management.repository;

import com.mg_devjoint_task_one.library_management.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
