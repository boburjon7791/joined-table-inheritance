package com.example.joined_table_inheritance.controller;

import com.example.joined_table_inheritance.config.utils.ApiConstants;
import com.example.joined_table_inheritance.model.dto.request.StudentRequest;
import com.example.joined_table_inheritance.model.dto.response.StudentResponse;
import com.example.joined_table_inheritance.model.entity.Student;
import com.example.joined_table_inheritance.model.filter_request.StudentFilterRequest;
import com.example.joined_table_inheritance.service.StudentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.BASE_PATH+"/student")
public class StudentController implements BaseController<StudentRequest, Student, Long, StudentResponse, StudentFilterRequest>{
    private final StudentService service;
}
