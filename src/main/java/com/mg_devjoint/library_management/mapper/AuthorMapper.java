package com.mg_devjoint.library_management.mapper;

import com.mg_devjoint.library_management.dto.response.AuthorResponse;
import com.mg_devjoint.library_management.model.Author;

public final class AuthorMapper {
    private AuthorMapper() {
    }

    public static AuthorResponse toAuthorResponse(Author author) {
    return new AuthorResponse(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getSummary(),
                author.getEmail()
        );


    }
}
