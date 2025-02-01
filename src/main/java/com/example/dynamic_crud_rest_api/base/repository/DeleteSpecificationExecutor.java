package com.example.dynamic_crud_rest_api.base.repository;

import com.example.dynamic_crud_rest_api.base.specification.DeleteSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;

public interface DeleteSpecificationExecutor<T> {
    default int executeDelete(DeleteSpecification<T> spec, Class<T> classType, EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<T> criteriaDelete = criteriaBuilder.createCriteriaDelete(classType);
        Root<T> root = criteriaDelete.from(classType);

        // query ni yaratish
        spec.apply(criteriaBuilder, criteriaDelete, root);

        // ishga tushirish
        return entityManager.createQuery(criteriaDelete).executeUpdate();
    }
}
