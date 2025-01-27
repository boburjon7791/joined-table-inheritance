package com.example.joined_table_inheritance.model.mapper;

import com.example.joined_table_inheritance.model.dto.request.TeacherRequest;
import com.example.joined_table_inheritance.model.dto.response.TeacherResponse;
import com.example.joined_table_inheritance.model.entity.Teacher;
import com.example.joined_table_inheritance.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TeacherMapper implements BaseMapper<TeacherRequest, Teacher, TeacherResponse>{
    private final PasswordEncoder passwordEncoder;

    @Override
    public Teacher toEntity(TeacherRequest teacherRequest) {
        Teacher teacher = new Teacher(teacherRequest.specialization());
        teacher.setFirstName(teacherRequest.firstName());
        teacher.setLastName(teacherRequest.lastName());
        teacher.setUsername(teacherRequest.username());
        String encodedPassword = passwordEncoder.encode(Objects.requireNonNull(teacherRequest.password(), "Password can not be null"));
        teacher.setPassword(encodedPassword);
        teacher.getGroups().addAll(teacherRequest.groups());
        return teacher;
    }

    @Override
    public TeacherResponse toResponse(Teacher teacher) {
        return new TeacherResponse(teacher.getId(), teacher.getFirstName(), teacher.getLastName(), teacher.getUsername(), teacher.getSpecialization(), teacher.getGroups());
    }

    @Override
    public void update(Teacher teacher, TeacherRequest teacherRequest) {
        teacher.setFirstName(teacherRequest.firstName());
        teacher.setLastName(teacherRequest.lastName());
        teacher.setUsername(teacherRequest.username());
        teacher.getGroups().clear();
        teacher.getGroups().addAll(teacherRequest.groups());
    }

    public TeacherResponse toResponseFromUser(User user) {
        if (user instanceof Teacher teacher) {
            return toResponse(teacher);
        }
        throw new RuntimeException("Not a teacher");
    }
}
