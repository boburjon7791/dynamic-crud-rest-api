package com.example.dynamic_crud_rest_api.base.exception;

import com.example.dynamic_crud_rest_api.base.model.response.ResultCodes;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final String resultCode;
    private final Object data;

    public ApiException(String message, String resultCode){
        super(message);
        this.resultCode=resultCode;
        this.httpStatus=HttpStatus.CONFLICT;
        this.data=null;
    }
    public ApiException(String message, String resultCode, HttpStatus httpStatus){
        super(message);
        this.httpStatus=httpStatus;
        this.resultCode=resultCode;
        this.data=null;
    }
    public ApiException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus=httpStatus;
        this.resultCode= ResultCodes.CONFLICT;
        this.data=null;
    }

    public ApiException(String message){
        super(message);
        this.resultCode= ResultCodes.CONFLICT;
        this.httpStatus=HttpStatus.CONFLICT;
        this.data=null;
    }

    public ApiException(Exception e) {
        super(e);
        this.resultCode=ResultCodes.CONFLICT;
        this.httpStatus=HttpStatus.CONFLICT;
        this.data=null;
    }

    public ApiException(Object data, String resultCode, HttpStatus httpStatus) {
        this.resultCode=resultCode;
        this.httpStatus=httpStatus;
        this.data=data;
    }
}
