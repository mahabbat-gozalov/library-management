package com.mg_devjoint_task_one.library_management.service;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateAuthorRequest;
import com.mg_devjoint_task_one.library_management.dto.request.update.UpdateAuthorRequest;
import com.mg_devjoint_task_one.library_management.dto.response.AuthorResponse;
import com.mg_devjoint_task_one.library_management.dto.response.PageResponse;
import com.mg_devjoint_task_one.library_management.model.Author;

import java.util.Set;
import java.util.UUID;

public interface AuthorService {

    AuthorResponse createAuthor(CreateAuthorRequest request);

    AuthorResponse updateAuthor(UUID authorId, UpdateAuthorRequest request);

    AuthorResponse getAuthorById(UUID authorId);

    void deleteAuthorById(UUID authorId);

    Author getAuthorEntityById(UUID authorId);

    Set<Author> getAuthorEntitySetByIdSet(Set<UUID> authorIdSet);

    PageResponse<AuthorResponse> getAllAuthors(int page, int size);
}
