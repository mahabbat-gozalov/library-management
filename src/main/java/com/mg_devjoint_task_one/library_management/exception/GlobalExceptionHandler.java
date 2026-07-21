package com.mg_devjoint_task_one.library_management.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    private GlobalExceptionResponse createExceptionBody(HttpStatus status,
                                                        String message,
                                                        String uri
    ) {

        return GlobalExceptionResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .statusCode(status.value())
                .reasonPhrase(status.getReasonPhrase())
                .exceptionMessage(message)
                .uri(uri)
                .build();

    }

    @ExceptionHandler(InvalidEntityDataException.class)
    public ResponseEntity<GlobalExceptionResponse> invalidEntityDataExceptionHandler(InvalidEntityDataException exception,
                                                                                     HttpServletRequest request) {
        GlobalExceptionResponse body = createExceptionBody(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GlobalExceptionResponse> notFoundExceptionHandler(NotFoundException exception,
                                                                            HttpServletRequest request) {

        GlobalExceptionResponse body = createExceptionBody(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<GlobalExceptionResponse> invalidOperationExceptionHandler(InvalidOperationException exception,
                                                                                    HttpServletRequest request) {
        GlobalExceptionResponse body = createExceptionBody(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalExceptionResponse> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException exception,
                                                                                          HttpServletRequest request) {

        GlobalExceptionResponse body = createExceptionBody(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalExceptionResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception,
                                                                                          HttpServletRequest request
    ) {

        Map<String, List<String>> validationErrors = exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));

        GlobalExceptionResponse response = GlobalExceptionResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .reasonPhrase(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .exceptionMessage(exception.getMessage())
                .uri(request.getRequestURI())
                .validationErrors(validationErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}
