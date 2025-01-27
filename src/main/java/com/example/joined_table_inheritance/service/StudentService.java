package com.example.joined_table_inheritance.service;

import com.example.joined_table_inheritance.config.exception.ApiException;
import com.example.joined_table_inheritance.model.dto.request.StudentRequest;
import com.example.joined_table_inheritance.model.dto.response.StudentResponse;
import com.example.joined_table_inheritance.model.entity.Student;
import com.example.joined_table_inheritance.model.filter_request.StudentFilterRequest;
import com.example.joined_table_inheritance.model.mapper.StudentMapper;
import com.example.joined_table_inheritance.repository.StudentRepository;
import com.example.joined_table_inheritance.repository.UserRepository;
import com.example.joined_table_inheritance.specification.StudentSpecification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class StudentService implements BaseService<StudentRequest, Student, Long, StudentResponse, StudentFilterRequest>{
    private final StudentRepository repository;
    private final StudentMapper mapper;
    private final UserRepository userRepository;
    private final StudentSpecification specification;

    @Override
    public void checkUniqueCreate(StudentRequest request){
        if (userRepository.existsByUsernameAndDeletedFalse(request.username())) {
            throw new ApiException("Username already exists");
        }
    }

    @Override
    public void checkUniqueUpdate(StudentRequest request, Long id){
        if (userRepository.existsByUsernameAndDeletedFalseAndIdNot(request.username(), id)) {
            throw new ApiException("Username already exists");
        }
    }

    @Override
    public String entityName() {
        return Student.class.getSimpleName();
    }

}
