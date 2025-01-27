package com.example.joined_table_inheritance.model.dto.response;

public record AdminResponse(
        Long id,
        String username,
        String firstName,
        String lastName,
        String phone
) implements BaseUserResponse{
}
