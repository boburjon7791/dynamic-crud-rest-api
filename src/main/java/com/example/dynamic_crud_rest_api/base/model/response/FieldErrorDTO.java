package com.example.dynamic_crud_rest_api.base.model.response;

public record FieldErrorDTO(String name, String message) {
    public static FieldErrorDTO of(String name, String message){
        return new FieldErrorDTO(name, message);
    }
}
