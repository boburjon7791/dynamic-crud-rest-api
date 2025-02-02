package com.example.dynamic_crud_rest_api.base.service;

import com.example.dynamic_crud_rest_api.base.config.internationalization.Localization;
import com.example.dynamic_crud_rest_api.base.exception.ApiException;
import com.example.dynamic_crud_rest_api.base.model.entity.BaseSuperEntity;
import com.example.dynamic_crud_rest_api.base.model.filter_request.BaseFilterRequest;
import com.example.dynamic_crud_rest_api.base.model.mapper.BaseMapper;
import com.example.dynamic_crud_rest_api.base.model.response.ApiResponse;
import com.example.dynamic_crud_rest_api.base.model.response.PaginationDetails;
import com.example.dynamic_crud_rest_api.base.repository.BaseRepository;
import com.example.dynamic_crud_rest_api.base.specification.BaseSpecification;
import com.example.dynamic_crud_rest_api.base.specification.UpdateSpecification;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public interface BaseService<REQUEST, ENTITY extends BaseSuperEntity, ID extends Serializable, RESPONSE, FILTER_REQUEST extends BaseFilterRequest> {
    /*
    * abstract methods
    * */
    BaseRepository<ENTITY, ID> getRepository();
    Localization getLocalization();
    BaseMapper<REQUEST, ENTITY, RESPONSE> getMapper();
    BaseSpecification<ENTITY, ID, FILTER_REQUEST> getSpecification();
    EntityManager getEntityManager();
    Class<ENTITY> getClassType();

    /*
    * you need to override this method and return ENTITY.class.getSimpleName() method
    * */
    String getClassName();

    /*
    * if these methods not necessary, don't write any code in these method's body
    * */
    void checkUniqueWhileCreating(REQUEST request);
    void checkUniqueWhileUpdating(REQUEST request, ID id);

    /*
    * if you don't want to clean request object, you need to return request object in this method's body like this:
    * public REQUEST cleanUpdateModelObject(REQUEST request){
    *     return request;
    * }
    *
    * if you want clean object, you need to deep copy then return copied object
    * */
    REQUEST cleanUpdateModelObject(REQUEST request);

    /*
    * concreate methods
    * if any logic not required in your concreate service class,
    * you need to override that method and throw any RuntimeException or instance
    * */
    default ApiResponse<RESPONSE> create(REQUEST request) {
        checkUniqueWhileCreating(request);
        ENTITY entity = getMapper().toEntity(request);
        ENTITY saved = getRepository().save(entity);
        RESPONSE response = getMapper().toResponse(saved);
        return ApiResponse.ok(response);
    }

    default ENTITY entity(ID id){
        Specification<ENTITY> specification = getSpecification().idEquals(id).and(getSpecification().deletedFalse());
        return getRepository().findOne(specification)
                .orElseThrow(() -> {
                    String className = getLocalization().getMessage(getClassName());
                    String notFound = getLocalization().getMessage("not_found");
                    return new ApiException(className + " " + notFound);
                });
    }

    // in this method, we can create dynamic update SQL query with CriteriaUpdate
    @Transactional
    default ApiResponse<RESPONSE> update(REQUEST request, ID id){
        checkUniqueWhileUpdating(request, id);
        request=cleanUpdateModelObject(request);
        UpdateSpecification<ENTITY> updateSpecification=(criteriaBuilder, criteriaUpdate, root) -> criteriaBuilder.equal(root.get(BaseSuperEntity._id), id);
        if (getRepository().executeUpdate(updateSpecification, getClassType(), request, getEntityManager())>0) {
            return ApiResponse.ok();
        }
        String className = getLocalization().getMessage(getClassName());
        String notFound = getLocalization().getMessage("not_found");
        throw new ApiException(className+" "+notFound);
    }

    default ApiResponse<RESPONSE> findById(ID id){
        ENTITY entity = entity(id);
        RESPONSE response = getMapper().toResponse(entity);
        return ApiResponse.ok(response);
    }

    default ApiResponse<List<RESPONSE>> findAll(FILTER_REQUEST request){
        if (request.isAll()) {
            List<RESPONSE> responseList = getRepository().findAll(request.sort()).stream().map(getMapper()::toResponse).toList();
            return ApiResponse.ok(responseList);
        }
        Specification<ENTITY> specification = getSpecification().getSpecification(request);
        Page<RESPONSE> page = getRepository().findAll(specification, request.pageable()).map(getMapper()::toResponse);
        return ApiResponse.ok(page.getContent(), PaginationDetails.of(page));
    }

    @Transactional
    default ApiResponse<Void> deleteById(ID id){
        ENTITY entity = entity(id);
        entity.setDeleted(true);
        getRepository().save(entity);
        return ApiResponse.ok();
    }

    /*
    * if id field is null or not exists by id create entity, else update entity
    * */
    @Transactional
    default ENTITY saveOrUpdateEntity(REQUEST request, ID id){
        if (id!=null) {
            checkUniqueWhileUpdating(request, id);
            update(request, id);
            ENTITY entity = entity(id);
            return getRepository().save(entity);
        }
        checkUniqueWhileCreating(request);
        ENTITY entity = getMapper().toEntity(request);
        return getRepository().save(entity);
    }
}
