package com.example.joined_table_inheritance.config.exception;

import com.example.joined_table_inheritance.config.model.response.ApiResponse;
import com.example.joined_table_inheritance.config.model.response.FieldErrorDTO;
import com.example.joined_table_inheritance.config.utils.ResultCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleException(Exception e) {
        log.error("Handled exception : {0}", e);
        return ApiResponse.error(e.getMessage(), ResultCodes.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<List<FieldErrorDTO>> handleValidationException(MethodArgumentNotValidException e) {
        log.warn("Handled validation exception : {0}", e);
        List<FieldErrorDTO> errorDTOList = e.getFieldErrors().stream().map(fieldError -> FieldErrorDTO.of(fieldError.getField(), fieldError.getDefaultMessage())).toList();
        return ApiResponse.error(errorDTOList, "", ResultCodes.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Object>> handleApiException(ApiException e) {
        log.warn("Handled exception : {0}", e);
        ApiResponse<Object> apiResponse = ApiResponse.error(e.getData(), e.getMessage(), e.getResultCode());
        return ResponseEntity.status(e.getHttpStatus()).body(apiResponse);
    }
}
