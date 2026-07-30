package com.mg_devjoint.library_management.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mg_devjoint.library_management.exception.GlobalExceptionResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");

        GlobalExceptionResponse body = GlobalExceptionResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .statusCode(403)
                .reasonPhrase("Forbidden")
                .exceptionMessage(accessDeniedException.getMessage())
                .uri(request.getRequestURI())
                .build();


        objectMapper.writeValue(response.getWriter(), body);


    }
}
