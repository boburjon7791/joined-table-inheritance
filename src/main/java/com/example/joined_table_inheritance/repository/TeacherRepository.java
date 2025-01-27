package com.example.joined_table_inheritance.repository;

import com.example.joined_table_inheritance.model.entity.Teacher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends BaseRepository<Teacher, Long> {
    Optional<Teacher> findByUsernameAndDeletedFalse(String username);

    @Override
    @EntityGraph(attributePaths = {Teacher._groups})
    Optional<Teacher> findOne(Specification<Teacher> spec);

    boolean existsByUsernameAndDeletedFalse(String username);

    boolean existsByUsernameAndDeletedFalseAndIdNot(String username, Long id);
}
