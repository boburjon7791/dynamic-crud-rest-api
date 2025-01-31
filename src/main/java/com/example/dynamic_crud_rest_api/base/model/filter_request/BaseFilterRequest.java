package com.example.dynamic_crud_rest_api.base.model.filter_request;

import com.example.dynamic_crud_rest_api.base.model.entity.BaseSuperEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BaseFilterRequest {
    private int page;
    private int size=10;
    private String search;
    private boolean all;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String sortJsonList;

    private static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();
    public Sort sort(){
        if (sortJsonList==null || sortJsonList.isBlank()) {
            return Sort.by(Sort.Direction.DESC, BaseSuperEntity._createdAt);
        }
        CollectionType collectionType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, OrderRequest.class);
        try {
            List<OrderRequest> orderRequests=OBJECT_MAPPER.readValue(sortJsonList, collectionType);
            List<Sort.Order> orders = orderRequests.stream()
                    .map(orderRequest -> orderRequest.type().equals(Sort.Direction.ASC) ? Sort.Order.asc(orderRequest.field()) : Sort.Order.desc(orderRequest.field()))
                    .toList();
            return Sort.by(orders);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Pageable pageable(){
        return PageRequest.of(page, size, sort());
    }
}
