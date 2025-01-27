package com.example.joined_table_inheritance.controller;

import com.example.joined_table_inheritance.config.model.response.ApiResponse;
import com.example.joined_table_inheritance.config.utils.ApiConstants;
import com.example.joined_table_inheritance.model.dto.request.LoginRequest;
import com.example.joined_table_inheritance.model.dto.response.LoginResponse;
import com.example.joined_table_inheritance.model.enums.Role;
import com.example.joined_table_inheritance.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.BASE_PATH+"/auth")
public class AuthController {
    private final AuthService service;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request, @RequestHeader Role role){
        return service.login(request, role);
    }
}
