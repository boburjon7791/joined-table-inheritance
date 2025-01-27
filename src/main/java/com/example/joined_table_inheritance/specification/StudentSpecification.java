package com.example.joined_table_inheritance.specification;

import com.example.joined_table_inheritance.model.entity.Student;
import com.example.joined_table_inheritance.model.filter_request.StudentFilterRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class StudentSpecification implements BaseSpecification<Student, Long, StudentFilterRequest>{
    @Override
    public Specification<Student> getSpecification(StudentFilterRequest request) {
        return emptySpecification()
                .and(deletedFalse())
                .and(fromDate(request.getFromDate()))
                .and(toDate(request.getToDate()));
    }
}
