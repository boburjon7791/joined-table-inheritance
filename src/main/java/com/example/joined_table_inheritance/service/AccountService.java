package com.example.joined_table_inheritance.service;

import com.example.joined_table_inheritance.config.exception.ApiException;
import com.example.joined_table_inheritance.config.model.response.ApiResponse;
import com.example.joined_table_inheritance.config.utils.ResultCodes;
import com.example.joined_table_inheritance.model.dto.request.ChangePasswordRequest;
import com.example.joined_table_inheritance.model.dto.response.BaseUserResponse;
import com.example.joined_table_inheritance.model.entity.User;
import com.example.joined_table_inheritance.model.enums.Role;
import com.example.joined_table_inheritance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final AdminService adminService;

    public void changePassword(ChangePasswordRequest changePasswordRequest, Long userId){
        User user = entity(userId);
        if (passwordEncoder.matches(changePasswordRequest.oldPassword(), user.getPassword())) {
            String encodedPassword = passwordEncoder.encode(changePasswordRequest.newPassword());
            user.setPassword(encodedPassword);
            repository.save(user);
            return;
        }
        throw new ApiException("Old password does not match");
    }

    public User entity(Long id){
        return repository.findOne((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User._id), id))
                .orElseThrow(() -> new ApiException("User not found", ResultCodes.NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public ApiResponse<? extends BaseUserResponse> findById(Long id, Role role) {
        return switch (role) {
            case TEACHER -> teacherService.findById(id);
            case STUDENT -> studentService.findById(id);
            case SUPER_ADMIN -> adminService.findById(id);
        };
    }
}
