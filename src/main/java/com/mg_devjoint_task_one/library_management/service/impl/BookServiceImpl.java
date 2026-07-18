package com.mg_devjoint_task_one.library_management.service.impl;

import com.mg_devjoint_task_one.library_management.model.Book;
import com.mg_devjoint_task_one.library_management.repository.BookRepository;
import com.mg_devjoint_task_one.library_management.service.BookService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Set<Book> getBookSetByIdSet(Set<UUID> idSet) {
        return bookRepository.getBooksByIdIsIn(idSet);
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }
}
