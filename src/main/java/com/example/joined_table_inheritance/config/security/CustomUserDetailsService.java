package com.example.joined_table_inheritance.config.security;

import com.example.joined_table_inheritance.config.exception.ApiException;
import com.example.joined_table_inheritance.config.utils.HttpUtils;
import com.example.joined_table_inheritance.model.entity.User;
import com.example.joined_table_inheritance.model.enums.Role;
import com.example.joined_table_inheritance.repository.AdminRepository;
import com.example.joined_table_inheritance.repository.StudentRepository;
import com.example.joined_table_inheritance.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String roleHeader = HttpUtils.getRequest().getHeader(Role._role);
        if (roleHeader==null) {
            throw new ApiException("role header value is null");
        }
        Role role = Role.valueOf(roleHeader);
        User user=switch (role) {
            case TEACHER -> teacherRepository.findByUsernameAndDeletedFalse(username).orElseThrow(() -> new ApiException("Teacher not found"));
            case STUDENT -> studentRepository.findByUsernameAndDeletedFalse(username).orElseThrow(() -> new ApiException("Student not found"));
            case SUPER_ADMIN -> adminRepository.findByUsernameAndDeletedFalse(username).orElseThrow(() -> new ApiException("Admin not found"));
        };
        return CustomUserDetails.of(user, Set.of(role));
    }
}
