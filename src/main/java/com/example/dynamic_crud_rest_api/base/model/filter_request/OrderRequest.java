package com.example.dynamic_crud_rest_api.base.model.filter_request;

import org.springframework.data.domain.Sort;

public record OrderRequest(String field, Sort.Direction type) {
}
