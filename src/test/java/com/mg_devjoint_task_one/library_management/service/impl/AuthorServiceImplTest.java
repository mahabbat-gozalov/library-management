package com.mg_devjoint_task_one.library_management.service.impl;

import com.mg_devjoint_task_one.library_management.dto.request.create.CreateAuthorRequest;
import com.mg_devjoint_task_one.library_management.dto.response.AuthorResponse;
import com.mg_devjoint_task_one.library_management.exception.NotFoundException;
import com.mg_devjoint_task_one.library_management.model.Author;
import com.mg_devjoint_task_one.library_management.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;


    @Test
    void shouldCreateAuthorSuccessfully() {
        CreateAuthorRequest request =
                new CreateAuthorRequest(
                        "John",
                        "Doe",
                        "Famous writer",
                        "john.doe@gmail.com"
                );


        Author savedAuthor =
                Author.create(
                        "John",
                        "Doe",
                        "Famous writer",
                        "john.doe@gmail.com"
                );

        Mockito.when(authorRepository.save(Mockito.any(Author.class)))
                .thenReturn(savedAuthor);

        AuthorResponse authorResponse = authorService.createAuthor(request);

        Assertions.assertNotNull(authorResponse);

        Assertions.assertEquals(
                request.firstName(),
                authorResponse.firstName()
        );
        Assertions.assertEquals(
                request.lastName(),
                authorResponse.lastName()
        );
        Assertions.assertEquals(
                request.summary(),
                authorResponse.summary()
        );
        Assertions.assertEquals(
                request.email(),
                authorResponse.email()
        );

        Mockito.verify(authorRepository)
                .save(Mockito.any(Author.class));

    }


    @Test
    void shouldReturnAuthorWhenAuthorExists() {
        UUID authorId = UUID.randomUUID();

        Author author = Author.create(
                "John",
                "Doe",
                "Famous writer",
                "john.doe@gmail.com");

        Mockito.when(authorRepository.findById(authorId))
                .thenReturn(Optional.of(author));

        AuthorResponse authorResponse = authorService.getAuthorById(authorId);

        Assertions.assertNotNull(authorResponse);

        Assertions.assertEquals(author.getFirstName(), authorResponse.firstName());
        Assertions.assertEquals(author.getLastName(), authorResponse.lastName());
        Assertions.assertEquals(author.getSummary(), authorResponse.summary());
        Assertions.assertEquals(author.getEmail(), authorResponse.email());

        Mockito.verify(authorRepository)
                .findById(authorId);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenAuthorDoesNotExist() {

        UUID authorId = UUID.randomUUID();

        Mockito.when(authorRepository.findById(authorId))
                .thenReturn(Optional.empty());


        NotFoundException notFoundException = Assertions.assertThrows(
                NotFoundException.class,
                () -> authorService.getAuthorById(authorId)
        );

        Assertions.assertEquals(
                "Author not found with id " + authorId,
                notFoundException.getMessage()
        );

        Mockito.verify(authorRepository)
                .findById(authorId);

    }


}
