package com.example.joined_table_inheritance.repository;

import com.example.joined_table_inheritance.model.entity.Student;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends BaseRepository<Student, Long> {
    Optional<Student> findByUsernameAndDeletedFalse(String username);

    @Override
    @EntityGraph(attributePaths = {Student._subjects})
    Optional<Student> findOne(Specification<Student> spec);

    boolean existsByUsernameAndDeletedFalse(String username);

    boolean existsByUsernameAndDeletedFalseAndIdNot(String username, Long id);
}
