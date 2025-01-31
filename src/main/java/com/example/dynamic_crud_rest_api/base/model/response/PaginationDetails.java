package com.example.dynamic_crud_rest_api.base.model.response;

import org.springframework.data.domain.Page;

public record PaginationDetails(int currentPage, int totalPages, long totalElements, int pageSize) {
    public static PaginationDetails of(Page<?> page){
        return new PaginationDetails(page.getNumber(), page.getTotalPages(), page.getTotalElements(), page.getSize());
    }
}
