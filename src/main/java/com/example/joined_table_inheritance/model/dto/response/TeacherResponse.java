package com.example.joined_table_inheritance.model.dto.response;

import java.util.Set;

public record TeacherResponse(
        Long id,
        String firstName,
        String lastName,
        String username,
        String specialization,
        Set<String> groups
) implements BaseUserResponse {
}
