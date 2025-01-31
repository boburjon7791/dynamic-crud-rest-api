package com.example.dynamic_crud_rest_api.base.model.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiResponse<T>(
        LocalDateTime transactionTime,
        T data,
        PaginationDetails pagination,
        String resultMessage,
        String resultCode
) {
    // success responses
    public static <T> ApiResponse<T> ok(){
        return ApiResponse.<T>builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(ResultCodes.OK)
                .build();
    }
    public static <T> ApiResponse<T> ok(T data){
        return ApiResponse.<T>builder()
                .data(data)
                .transactionTime(LocalDateTime.now())
                .resultCode(ResultCodes.OK)
                .build();
    }
    public static <T> ApiResponse<T> ok(T data, PaginationDetails pagination){
        return ApiResponse.<T>builder()
                .data(data)
                .pagination(pagination)
                .transactionTime(LocalDateTime.now())
                .resultCode(ResultCodes.OK)
                .build();
    }

    // error responses
    public static <T> ApiResponse<T> error(String resultMessage, String resultCode){
        return ApiResponse.<T>builder()
                .resultMessage(resultMessage)
                .resultCode(resultCode)
                .transactionTime(LocalDateTime.now())
                .build();
    }
    public static <T> ApiResponse<T> errorOnlyResultMessage(String resultMessage){
        return ApiResponse.<T>builder()
                .resultMessage(resultMessage)
                .transactionTime(LocalDateTime.now())
                .build();
    }
    public static <T> ApiResponse<T> errorOnlyResultCode(String resultCode){
        return ApiResponse.<T>builder()
                .resultCode(resultCode)
                .transactionTime(LocalDateTime.now())
                .build();
    }
    public static <T> ApiResponse<T> error(T data, String resultCode){
        return ApiResponse.<T>builder()
                .data(data)
                .resultCode(resultCode)
                .transactionTime(LocalDateTime.now())
                .build();
    }
}
