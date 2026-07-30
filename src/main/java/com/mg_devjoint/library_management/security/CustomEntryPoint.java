package com.mg_devjoint.library_management.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mg_devjoint.library_management.exception.GlobalExceptionResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        GlobalExceptionResponse body = GlobalExceptionResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .statusCode(401)
                .reasonPhrase("Unauthorized")
                .exceptionMessage(authException.getMessage())
                .uri(request.getRequestURI())
                .build();

        objectMapper.writeValue(response.getWriter(), body);

    }
}

