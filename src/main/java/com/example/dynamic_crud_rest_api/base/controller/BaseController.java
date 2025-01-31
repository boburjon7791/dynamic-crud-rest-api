package com.example.dynamic_crud_rest_api.base.controller;

import com.example.dynamic_crud_rest_api.base.model.entity.BaseSuperEntity;
import com.example.dynamic_crud_rest_api.base.model.filter_request.BaseFilterRequest;
import com.example.dynamic_crud_rest_api.base.model.response.ApiResponse;
import com.example.dynamic_crud_rest_api.base.service.BaseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.io.Serializable;

public interface BaseController<REQUEST, ENTITY extends BaseSuperEntity, ID extends Serializable, RESPONSE, FILTER_REQUEST extends BaseFilterRequest> {
    /*
    * abstract methods
    * */
    BaseService<REQUEST, ENTITY, ID, RESPONSE, FILTER_REQUEST> getService();

    /*
    * base CRUD APIs.
    * if any API not required in your concreate controller class,
    * you need to override that method and throw any RuntimeException or instance
    * */
    @PostMapping
    default ApiResponse<RESPONSE> create(@RequestBody @Valid REQUEST request){
        return getService().create(request);
    }

    @PutMapping("/{id}")
    default ApiResponse<RESPONSE> update(@RequestBody REQUEST request, @PathVariable ID id){
        return getService().update(request, id);
    }

    @GetMapping("/{id}")
    default ApiResponse<RESPONSE> findById(@PathVariable ID id){
        return getService().findById(id);
    }

    @GetMapping
    default ApiResponse<List<RESPONSE>> findAll(@ModelAttribute FILTER_REQUEST request){
        return getService().findAll(request);
    }

    @DeleteMapping("/{id}")
    default ApiResponse<Void> delete(@PathVariable ID id){
        return getService().deleteById(id);
    }
}
