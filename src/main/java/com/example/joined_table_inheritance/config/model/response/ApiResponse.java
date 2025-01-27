package com.example.joined_table_inheritance.config.model.response;

import com.example.joined_table_inheritance.config.utils.ResultCodes;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiResponse<T>(
        LocalDateTime transactionTime,
        T data,
        String resultMessage,
        String resultCode,
        PaginationDetails pagination
) {
    // success
    public static ApiResponse<Void> ok() {
        return ApiResponse.<Void>builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(ResultCodes.SUCCESS)
                .build();
    }
    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .transactionTime(LocalDateTime.now())
                .resultCode(ResultCodes.SUCCESS)
                .build();
    }
    public static <T> ApiResponse<T> ok(T data, PaginationDetails pagination) {
        return ApiResponse.<T>builder()
                .data(data)
                .pagination(pagination)
                .transactionTime(LocalDateTime.now())
                .resultCode(ResultCodes.SUCCESS)
                .build();
    }

    // error
    public static ApiResponse<Void> error(String resultMessage, String resultCode) {
        return ApiResponse.<Void>builder()
                .resultMessage(resultMessage)
                .resultCode(resultCode)
                .transactionTime(LocalDateTime.now())
                .build();
    }
    public static <T> ApiResponse<T> error(T data, String resultMessage, String resultCode){
        return ApiResponse.<T>builder()
                .data(data)
                .resultMessage(resultMessage)
                .resultCode(resultCode)
                .transactionTime(LocalDateTime.now())
                .build();
    }
}
