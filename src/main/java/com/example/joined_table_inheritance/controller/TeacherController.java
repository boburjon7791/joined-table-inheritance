package com.example.joined_table_inheritance.controller;

import com.example.joined_table_inheritance.config.utils.ApiConstants;
import com.example.joined_table_inheritance.model.dto.request.TeacherRequest;
import com.example.joined_table_inheritance.model.dto.response.TeacherResponse;
import com.example.joined_table_inheritance.model.entity.Teacher;
import com.example.joined_table_inheritance.model.filter_request.TeacherFilterRequest;
import com.example.joined_table_inheritance.service.TeacherService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.BASE_PATH+"/teacher")
public class TeacherController implements BaseController<TeacherRequest, Teacher, Long, TeacherResponse, TeacherFilterRequest>{
    private final TeacherService service;
}
