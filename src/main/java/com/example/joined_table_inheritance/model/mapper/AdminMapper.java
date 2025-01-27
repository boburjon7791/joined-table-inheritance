package com.example.joined_table_inheritance.model.mapper;

import com.example.joined_table_inheritance.model.dto.request.AdminRequest;
import com.example.joined_table_inheritance.model.dto.response.AdminResponse;
import com.example.joined_table_inheritance.model.entity.Admin;
import com.example.joined_table_inheritance.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AdminMapper implements BaseMapper<AdminRequest, Admin, AdminResponse>{
    private final PasswordEncoder passwordEncoder;

    @Override
    public Admin toEntity(AdminRequest adminRequest) {
        Admin admin = new Admin(adminRequest.phone());
        admin.setFirstName(adminRequest.firstName());
        admin.setLastName(adminRequest.lastName());
        admin.setUsername(adminRequest.username());
        String encodedPassword = passwordEncoder.encode(Objects.requireNonNull(adminRequest.password(), "Password can not be null"));
        admin.setPassword(encodedPassword);
        return admin;
    }

    @Override
    public AdminResponse toResponse(Admin admin) {
        return new AdminResponse(admin.getId(), admin.getUsername(), admin.getFirstName(), admin.getLastName(), admin.getPhone());
    }

    @Override
    public void update(Admin admin, AdminRequest adminRequest) {
        admin.setUsername(adminRequest.username());
        admin.setFirstName(adminRequest.firstName());
        admin.setLastName(adminRequest.lastName());
        admin.setPhone(adminRequest.phone());
    }

    public AdminResponse toResponseFromUser(User user) {
        if (user instanceof Admin admin) {
            return toResponse(admin);
        }
        throw new RuntimeException("User is not Admin");
    }
}
