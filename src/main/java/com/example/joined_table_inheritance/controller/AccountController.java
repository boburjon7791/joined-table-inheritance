package com.example.joined_table_inheritance.controller;

import com.example.joined_table_inheritance.config.model.response.ApiResponse;
import com.example.joined_table_inheritance.config.security.CustomUserDetails;
import com.example.joined_table_inheritance.config.utils.ApiConstants;
import com.example.joined_table_inheritance.model.dto.request.*;
import com.example.joined_table_inheritance.model.dto.response.AdminResponse;
import com.example.joined_table_inheritance.model.dto.response.BaseUserResponse;
import com.example.joined_table_inheritance.model.dto.response.StudentResponse;
import com.example.joined_table_inheritance.model.dto.response.TeacherResponse;
import com.example.joined_table_inheritance.model.enums.Role;
import com.example.joined_table_inheritance.service.AccountService;
import com.example.joined_table_inheritance.service.AdminService;
import com.example.joined_table_inheritance.service.StudentService;
import com.example.joined_table_inheritance.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.BASE_PATH+"/account")
public class AccountController {
    private final AccountService service;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final AdminService adminService;

    @GetMapping
    public ApiResponse<? extends BaseUserResponse> getAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestHeader Role role){
        return service.findById(customUserDetails.getId(), role);
    }


    @PutMapping("/teacher")
    public ApiResponse<TeacherResponse> updateTeacherAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody @Valid TeacherRequest request){
        return teacherService.update(request, customUserDetails.getId());
    }
    @PutMapping("/student")
    public ApiResponse<StudentResponse> updateStudentAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody @Valid StudentRequest request){
        return studentService.update(request, customUserDetails.getId());
    }
    @PutMapping("/admin")
    public ApiResponse<AdminResponse> updateAdminAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody @Valid AdminRequest request){
        return adminService.update(request, customUserDetails.getId());
    }

    @PutMapping("/change-password")
    public ApiResponse<Void> changePassword(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody @Valid ChangePasswordRequest request){
        service.changePassword(request, customUserDetails.getId());
        return ApiResponse.ok();
    }
}
