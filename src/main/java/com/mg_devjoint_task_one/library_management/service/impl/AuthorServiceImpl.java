package com.mg_devjoint_task_one.library_management.service.impl;

import com.mg_devjoint_task_one.library_management.exception.NotFoundException;
import com.mg_devjoint_task_one.library_management.model.Author;
import com.mg_devjoint_task_one.library_management.repository.AuthorRepository;
import com.mg_devjoint_task_one.library_management.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getAuthorEntityById(UUID authorId) {
        return authorRepository
                .findById(authorId)
                .orElseThrow(() -> new NotFoundException("Author not found with id " + authorId));
    }

    @Override
    public Set<Author> getAuthorEntitySetByIdSet(Set<UUID> authorIdSet) {

        Set<Author> authorSet = new HashSet<>(authorRepository.findAllById(authorIdSet));

        Set<UUID> authorIdSetFromDB = authorSet
                .stream()
                .map(Author::getId)
                .collect(Collectors.toSet());

        Set<UUID> notFoundAuthorIdSet = authorIdSet.stream()
                .filter(id -> !authorIdSetFromDB.contains(id))
                .collect(Collectors.toSet());

        if (!notFoundAuthorIdSet.isEmpty()) {
            throw new NotFoundException(
                    "Authors not found with ids: " + notFoundAuthorIdSet
            );
        }

        return authorSet;
    }

}
