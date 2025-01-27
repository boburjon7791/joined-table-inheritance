package com.example.joined_table_inheritance.config.exception;

import com.example.joined_table_inheritance.config.utils.ResultCodes;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final Object data;
    private final String resultCode;

    public ApiException(String message, String resultCode, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.resultCode = resultCode;
        this.data = null;
    }

    public ApiException(String message){
        super(message);
        this.httpStatus = HttpStatus.CONFLICT;
        this.resultCode = ResultCodes.CONFLICT;
        this.data = null;
    }

    public ApiException(String message, String resultCode){
        super(message);
        this.httpStatus = HttpStatus.CONFLICT;
        this.resultCode = resultCode;
        this.data = null;
    }

    public ApiException(Object data, String resultCode, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.data = data;
        this.resultCode = resultCode;
    }
}
