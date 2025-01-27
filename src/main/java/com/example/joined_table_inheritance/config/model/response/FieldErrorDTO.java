package com.example.joined_table_inheritance.config.model.response;

public record FieldErrorDTO(String field, String message) {
    public static FieldErrorDTO of(String field, String message) {
        return new FieldErrorDTO(field, message);
    }
}
