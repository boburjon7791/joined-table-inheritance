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
@Table(name = "teacher")
public class Teacher extends User{
    @Column(nullable = false)
    private String specialization;

    @ElementCollection
    @JoinTable(name = "teacher_groups",
            joinColumns = @JoinColumn(name = "teacher_id", nullable = false),
            indexes = {
                @Index(name = "idx_teacher_groups_teacher_id", columnList = "teacher_id")
            }
    )
    private final Set<String> groups=new HashSet<>();

    // field names for JPA specification
    public static final String _specialization="specialization";
    public static final String _groups="groups";
}
