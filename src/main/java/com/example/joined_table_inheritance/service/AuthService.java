package com.example.joined_table_inheritance.service;

import com.example.joined_table_inheritance.config.exception.ApiException;
import com.example.joined_table_inheritance.config.model.response.ApiResponse;
import com.example.joined_table_inheritance.config.security.CustomUserDetails;
import com.example.joined_table_inheritance.config.security.JwtProvider;
import com.example.joined_table_inheritance.config.security.TokenDTO;
import com.example.joined_table_inheritance.config.utils.HttpUtils;
import com.example.joined_table_inheritance.config.utils.ResultCodes;
import com.example.joined_table_inheritance.model.dto.request.LoginRequest;
import com.example.joined_table_inheritance.model.dto.response.BaseUserResponse;
import com.example.joined_table_inheritance.model.dto.response.LoginResponse;
import com.example.joined_table_inheritance.model.enums.Role;
import com.example.joined_table_inheritance.model.mapper.AdminMapper;
import com.example.joined_table_inheritance.model.mapper.StudentMapper;
import com.example.joined_table_inheritance.model.mapper.TeacherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final TeacherMapper teacherMapper;
    private final StudentMapper studentMapper;
    private final AdminMapper adminMapper;

    public ApiResponse<LoginResponse> login(LoginRequest loginRequest, Role role) {
        CustomUserDetails customUserDetails;
        try {
            // started authentication process in Spring Security AuthenticationManager and DaoAuthenticationProvider
            Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
            customUserDetails= (CustomUserDetails) authentication.getPrincipal();
        }catch (AuthenticationException e){
            // if any AuthenticationException throws, we catch and throw our ApiException with the following message
            throw new ApiException("Login or password incorrect", ResultCodes.CONFLICT, HttpStatus.CONFLICT);
        }

        // then read from header role value and return dynamic user response object
        String headerRole = HttpUtils.getRequest().getHeader(Role._role);
        BaseUserResponse userResponse=switch (role) {
            case STUDENT -> studentMapper.toResponseFromUser(customUserDetails.user());
            case TEACHER -> teacherMapper.toResponseFromUser(customUserDetails.user());
            case SUPER_ADMIN -> adminMapper.toResponseFromUser(customUserDetails.user());
        };
        TokenDTO tokenDTO = jwtProvider.generateToken(loginRequest.username());
        LoginResponse loginResponse = LoginResponse.of(userResponse, tokenDTO);
        return ApiResponse.ok(loginResponse);
    }
}
