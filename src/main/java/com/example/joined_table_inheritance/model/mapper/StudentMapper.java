package com.example.joined_table_inheritance.model.mapper;

import com.example.joined_table_inheritance.model.dto.request.StudentRequest;
import com.example.joined_table_inheritance.model.dto.response.StudentResponse;
import com.example.joined_table_inheritance.model.entity.Student;
import com.example.joined_table_inheritance.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StudentMapper implements BaseMapper<StudentRequest, Student, StudentResponse>{
    private final PasswordEncoder passwordEncoder;

    @Override
    public Student toEntity(StudentRequest request) {
        Student student = new Student(request.course(), request.mainSubject());
        student.setFirstName(request.firstName());
        student.setLastName(request.lastName());
        student.setUsername(request.username());
        String encodedPassword = passwordEncoder.encode(Objects.requireNonNull(request.password(), "Password can not be null"));
        student.setPassword(encodedPassword);
        student.getSubjects().addAll(request.subjects());
        return student;
    }

    @Override
    public StudentResponse toResponse(Student student) {
        return new StudentResponse(student.getId(), student.getFirstName(), student.getLastName(), student.getUsername(), student.getCourse(), student.getMainSubject(), student.getSubjects());
    }

    @Override
    public void update(Student student, StudentRequest request) {
        student.setFirstName(request.firstName());
        student.setLastName(request.lastName());
        student.setUsername(request.username());
        student.getSubjects().clear();
        student.getSubjects().addAll(request.subjects());
        student.setCourse(request.course());
        student.setMainSubject(request.mainSubject());
    }

    public StudentResponse toResponseFromUser(User user) {
        if (user instanceof Student student) {
            return toResponse(student);
        }
        throw new RuntimeException("Not a student");
    }
}
