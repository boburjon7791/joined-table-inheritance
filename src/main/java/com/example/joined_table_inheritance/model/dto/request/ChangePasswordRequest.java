package com.example.joined_table_inheritance.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(
        @NotBlank
        String oldPassword,

        @NotBlank
        String newPassword
) {
}
