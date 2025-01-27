package com.example.joined_table_inheritance.service;

import com.example.joined_table_inheritance.config.exception.ApiException;
import com.example.joined_table_inheritance.model.dto.request.TeacherRequest;
import com.example.joined_table_inheritance.model.dto.response.TeacherResponse;
import com.example.joined_table_inheritance.model.entity.Teacher;
import com.example.joined_table_inheritance.model.filter_request.TeacherFilterRequest;
import com.example.joined_table_inheritance.model.mapper.TeacherMapper;
import com.example.joined_table_inheritance.repository.TeacherRepository;
import com.example.joined_table_inheritance.repository.UserRepository;
import com.example.joined_table_inheritance.specification.TeacherSpecification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class TeacherService implements BaseService<TeacherRequest, Teacher, Long, TeacherResponse, TeacherFilterRequest> {
    private final TeacherRepository repository;
    private final TeacherSpecification specification;
    private final UserRepository userRepository;
    private final TeacherMapper mapper;

    @Override
    public void checkUniqueCreate(TeacherRequest teacherRequest) {
        if (userRepository.existsByUsernameAndDeletedFalse(teacherRequest.username())) {
            throw new ApiException("Username already exists");
        }
    }

    @Override
    public void checkUniqueUpdate(TeacherRequest teacherRequest, Long id) {
        if (userRepository.existsByUsernameAndDeletedFalseAndIdNot(teacherRequest.username(), id)) {
            throw new ApiException("Username already exists");
        }
    }

    @Override
    public String entityName() {
        return Teacher.class.getSimpleName();
    }
}
