package com.example.dynamic_crud_rest_api.base.model.mapper;

public interface BaseMapper<REQUEST, ENTITY, RESPONSE> {
    ENTITY toEntity(REQUEST request);
    RESPONSE toResponse(ENTITY entity);
}
