package com.example.joined_table_inheritance.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record TeacherRequest(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        String username,

        String password,

        @NotBlank
        String specialization,

        @NotEmpty
        Set<String> groups
) {
}
