package com.example.joined_table_inheritance.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record BaseUserRequest (
        @NotBlank
        String username,

        @NotBlank
        String firstName,

        @NotBlank
        String lastName
){
}
