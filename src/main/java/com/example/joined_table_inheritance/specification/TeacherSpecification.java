package com.example.joined_table_inheritance.specification;

import com.example.joined_table_inheritance.model.entity.Teacher;
import com.example.joined_table_inheritance.model.filter_request.TeacherFilterRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TeacherSpecification implements BaseSpecification<Teacher, Long, TeacherFilterRequest>{
    @Override
    public Specification<Teacher> getSpecification(TeacherFilterRequest request) {
        return emptySpecification()
                .and(deletedFalse())
                .and(fromDate(request.getFromDate()))
                .and(toDate(request.getToDate()));
    }
}
