package com.mg_devjoint_task_one.library_management.service.impl;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateAuthorRequest;
import com.mg_devjoint_task_one.library_management.dto.request.update.UpdateAuthorRequest;
import com.mg_devjoint_task_one.library_management.dto.response.AuthorResponse;
import com.mg_devjoint_task_one.library_management.dto.response.PageResponse;
import com.mg_devjoint_task_one.library_management.exception.NotFoundException;
import com.mg_devjoint_task_one.library_management.mapper.AuthorMapper;
import com.mg_devjoint_task_one.library_management.model.Author;
import com.mg_devjoint_task_one.library_management.model.Book;
import com.mg_devjoint_task_one.library_management.repository.AuthorRepository;
import com.mg_devjoint_task_one.library_management.service.AuthorService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorResponse createAuthor(CreateAuthorRequest request) {
        Author author = Author.create(request.firstName(), request.lastName(), request.summary(), request.email());

        Author savedAuthor = authorRepository.save(author);

        return AuthorMapper.toAuthorResponse(savedAuthor);
    }

    @Override
    public PageResponse<AuthorResponse> getAllAuthors(int page, int size) {

        Pageable pageable = getPageable(page, size);

        Page<Author> allAuthors = authorRepository.findAll(pageable);

        Page<AuthorResponse> allAuthorResponses = allAuthors.map(AuthorMapper::toAuthorResponse);

        return PageResponse.of(allAuthorResponses);
    }

    @Override
    public AuthorResponse getAuthorById(UUID authorId) {

        Author authorById = getAuthorEntityById(authorId);

        return AuthorMapper.toAuthorResponse(authorById);
    }

    @Override
    public AuthorResponse updateAuthor(UUID authorId, UpdateAuthorRequest request) {
        Author authorById = getAuthorEntityById(authorId);

        authorById.setFirstName(request.firstName());
        authorById.setLastName(request.lastName());
        authorById.setSummary(request.summary());
        authorById.setEmail(request.email());

        Author savedAuthor = authorRepository.save(authorById);

        return AuthorMapper.toAuthorResponse(savedAuthor);
    }

    @Override
    @Transactional
    public void deleteAuthorById(UUID authorId) {
        Author authorById = getAuthorEntityById(authorId);

        Set<Book> authorBooks = new HashSet<>(authorById.getBooks());

        authorBooks
                .forEach(authorById::removeBook);

        authorRepository.delete(authorById);
    }

    @Override
    public Author getAuthorEntityById(UUID authorId) {
        return authorRepository
                .findById(authorId)
                .orElseThrow(() -> new NotFoundException("Author not found with id " + authorId));
    }

    @Override
    public Set<Author> getAuthorSetByIdSet(Set<UUID> authorIdSet) {

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

    private Pageable getPageable(int pageNumber, int pageSize) {

        pageNumber = pageNumber <= 0 ? 1 : pageNumber;
        pageSize = pageSize <= 0 ? 10 : pageSize;

        return PageRequest.of(pageNumber - 1, pageSize);
    }

}
