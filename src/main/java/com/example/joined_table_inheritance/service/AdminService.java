package com.example.joined_table_inheritance.service;

import com.example.joined_table_inheritance.config.exception.ApiException;
import com.example.joined_table_inheritance.model.dto.request.AdminRequest;
import com.example.joined_table_inheritance.model.dto.response.AdminResponse;
import com.example.joined_table_inheritance.model.entity.Admin;
import com.example.joined_table_inheritance.model.filter_request.BaseFilterRequest;
import com.example.joined_table_inheritance.model.mapper.AdminMapper;
import com.example.joined_table_inheritance.repository.AdminRepository;
import com.example.joined_table_inheritance.repository.UserRepository;
import com.example.joined_table_inheritance.specification.BaseSpecification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class AdminService implements BaseService<AdminRequest, Admin, Long, AdminResponse, BaseFilterRequest>{
    private final AdminRepository repository;
    private final AdminMapper mapper;
    private final UserRepository userRepository;
    private final BaseSpecification<Admin, Long, BaseFilterRequest> specification=BaseSpecification.createAnonymousSpecification();

    @Override
    public void checkUniqueCreate(AdminRequest adminRequest) {
        if (userRepository.existsByUsernameAndDeletedFalse(adminRequest.username())) {
            throw new ApiException("Username already exists");
        }
    }

    @Override
    public void checkUniqueUpdate(AdminRequest adminRequest, Long id) {
        if (userRepository.existsByUsernameAndDeletedFalseAndIdNot(adminRequest.username(), id)) {
            throw new ApiException("Username already exists");
        }
    }

    @Override
    public String entityName() {
        return Admin.class.getSimpleName();
    }
}
