package com.example.dynamic_crud_rest_api.base.specification;

import com.example.dynamic_crud_rest_api.base.model.entity.BaseSuperEntity;
import com.example.dynamic_crud_rest_api.base.model.filter_request.BaseFilterRequest;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.time.LocalDate;

public interface BaseSpecification<ENTITY extends BaseSuperEntity, ID extends Serializable, FILTER_REQUEST extends BaseFilterRequest> {
    String TO_DATE_FUNCTION="date";

    /*
    * abstract methods
    * */
    Specification<ENTITY> getSpecification(FILTER_REQUEST request);

    /*
    * concreate methods
    * */
    default Specification<ENTITY> emptySpecification() {
        return Specification.where(null);
    }

    default Specification<ENTITY> fromDate(LocalDate fromDate){
        return fromDate==null ? emptySpecification() : (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.function(TO_DATE_FUNCTION, LocalDate.class, root.get(BaseSuperEntity._createdAt)), fromDate);
    }

    default Specification<ENTITY> toDate(LocalDate toDate){
        return toDate==null ? emptySpecification() : (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.function(TO_DATE_FUNCTION, LocalDate.class, root.get(BaseSuperEntity._createdAt)), toDate);
    }

    default Specification<ENTITY> idEquals(ID id){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(BaseSuperEntity._id), id);
    }

    default Specification<ENTITY> idNotEquals(ID id){
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(BaseSuperEntity._id), id);
    }

    default Specification<ENTITY> deletedFalse(){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(BaseSuperEntity._deleted), false);
    }

    default Specification<ENTITY> deleted(boolean deleted){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(BaseSuperEntity._deleted), deleted);
    }

    default String likeExpression(String text){
        return "%"+text.toLowerCase()+"%";
    }

    default String likeExpressionWithoutLowerCase(String text){
        return "%"+text+"%";
    }

    static <ENTITY extends BaseSuperEntity, ID extends Serializable, FILTER_REQUEST extends BaseFilterRequest> BaseSpecification<ENTITY, ID, FILTER_REQUEST> createAnonymousSpecification(){
        return request -> (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(BaseSuperEntity._deleted), false);
    }
}
