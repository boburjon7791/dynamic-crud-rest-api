package com.example.dynamic_crud_rest_api.base.exception;

import com.example.dynamic_crud_rest_api.base.model.response.ApiResponse;
import com.example.dynamic_crud_rest_api.base.model.response.FieldErrorDTO;
import com.example.dynamic_crud_rest_api.base.model.response.ResultCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleException(Exception e){
        log.error("handled exception : {0}", e);
        return ApiResponse.error(e.toString(), ResultCodes.SERVER_ERROR);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Object>> handleApiException(ApiException e){
        log.warn("handled api exception : {0}", e);
        ApiResponse<Object> response = ApiResponse.error(e.getData() != null ? e.getData() : e.getMessage(), e.getResultCode());
        return ResponseEntity.status(e.getHttpStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<List<FieldErrorDTO>> handleValidationException(MethodArgumentNotValidException e){
        log.warn("handled validation exception : {0}", e);
        List<FieldErrorDTO> fieldErrorDTOList = e.getFieldErrors().stream().map(fieldError -> FieldErrorDTO.of(fieldError.getField(), fieldError.getDefaultMessage())).toList();
        return ApiResponse.error(fieldErrorDTOList, ResultCodes.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleNoResourceFoundException(NoResourceFoundException e){
        log.warn("handled no resource found exception : {0}", e);
        return ApiResponse.error(e.getMessage(), ResultCodes.NOT_FOUND);
    }
}
