package com.example.joined_table_inheritance.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
public class Student extends User{
    @Column(nullable = false)
    private String course;

    @Column(name = "main_subject", nullable = false)
    private String mainSubject;

    @ElementCollection
    @JoinTable(name = "student_subjects",
            joinColumns = @JoinColumn(name = "student_id", nullable = false),
            indexes = {
                @Index(name = "idx_student_subjects_student_id", columnList = "student_id")
            }
    )
    private final Set<String> subjects = new HashSet<>();

    // field names for JPA specification
    public static final String _course="course";
    public static final String _mainSubject="mainSubject";
    public static final String _subjects="subjects";
}
