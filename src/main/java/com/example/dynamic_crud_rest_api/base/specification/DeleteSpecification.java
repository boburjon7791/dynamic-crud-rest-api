package com.example.dynamic_crud_rest_api.base.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;

@FunctionalInterface
public interface DeleteSpecification<T> {
    void apply(CriteriaBuilder criteriaBuilder, CriteriaDelete<?> criteriaDelete, Root<T> root);
}