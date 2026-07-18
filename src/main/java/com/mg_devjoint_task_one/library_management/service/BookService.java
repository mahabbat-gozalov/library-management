package com.mg_devjoint_task_one.library_management.service;

import com.mg_devjoint_task_one.library_management.model.Book;

import java.util.Set;
import java.util.UUID;

public interface BookService {

    Set<Book> getBookSetByIdSet(Set<UUID> idSet);

    void save(Book book);
}
