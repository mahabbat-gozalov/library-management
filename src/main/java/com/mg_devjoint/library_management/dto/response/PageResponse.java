package com.mg_devjoint.library_management.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public record PageResponse<T>(
        List<T> content,
        int pageNumber,
        int size,
        int totalPages,
        long totalElements,
        boolean isFirst,
        boolean isLast,
        boolean hasNext,
        boolean hasPrevious
) {
    public static <T> PageResponse<T> of(Page<T> page) {

        return new PageResponse<>(
                page.getContent(),
                page.getNumber() + 1,
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }


    public static <T> PageResponse<T> of(Page<?> uuidPages, List<T> content) {

        return new PageResponse<>(
                content,
                uuidPages.getNumber() + 1,
                uuidPages.getSize(),
                uuidPages.getTotalPages(),
                uuidPages.getTotalElements(),
                uuidPages.isFirst(),
                uuidPages.isLast(),
                uuidPages.hasNext(),
                uuidPages.hasPrevious()
        );
    }


}
