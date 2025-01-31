package com.example.dynamic_crud_rest_api.base.model.response;

public interface ResultCodes {
    String OK="0000";
    String BAD_REQUEST ="E400";
    String SERVER_ERROR="E500";
    String CONFLICT ="E409";
    String NOT_FOUND="E404";
    String AUTHORIZATION_REQUIRED = "E401";
    String ACCESS_DENIED = "E403";
}
