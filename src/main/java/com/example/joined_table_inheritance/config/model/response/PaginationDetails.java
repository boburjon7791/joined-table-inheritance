package com.example.joined_table_inheritance.config.model.response;

import org.springframework.data.domain.Page;

public record PaginationDetails(int currentPage, int pageSize, int totalPages, long totalElements) {
    public static PaginationDetails of(Page<?> page){
        return new PaginationDetails(page.getNumber(), page.getSize(), page.getTotalPages(), page.getTotalElements());
    }
}
