package com.example.joined_table_inheritance.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record StudentRequest(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        String username,

        String password,

        @NotBlank
        String mainSubject,

        @NotEmpty
        Set<String> subjects,

        @NotBlank
        String course
) {
}
