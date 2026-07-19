package com.mg_devjoint_task_one.library_management.service;

import com.mg_devjoint_task_one.library_management.model.Author;

import java.util.Set;
import java.util.UUID;

public interface AuthorService {

    Author getAuthorEntityById(UUID authorId);

    Set<Author> getAuthorEntitySetByIdSet(Set<UUID> authorIdSet);



}
