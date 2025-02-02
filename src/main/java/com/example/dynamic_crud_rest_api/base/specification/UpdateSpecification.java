package com.example.dynamic_crud_rest_api.base.specification;

import com.example.dynamic_crud_rest_api.base.model.entity.BaseSuperEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@FunctionalInterface
public interface UpdateSpecification<T extends BaseSuperEntity> {
    Predicate apply(CriteriaBuilder criteriaBuilder, CriteriaUpdate<?> criteriaUpdate, Root<T> root);
}
