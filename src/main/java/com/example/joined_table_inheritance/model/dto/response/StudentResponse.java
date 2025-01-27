package com.example.joined_table_inheritance.model.dto.response;

import com.example.joined_table_inheritance.model.entity.Student;
import com.example.joined_table_inheritance.model.entity.User;

import java.util.Set;

public record StudentResponse(
        Long id,
        String firstName,
        String lastName,
        String username,
        String course,
        String mainSubject,
        Set<String> subjects
) implements BaseUserResponse {
}
