package com.example.joined_table_inheritance.repository;

import com.example.joined_table_inheritance.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    boolean existsByUsernameAndDeletedFalse(String username);
    boolean existsByUsernameAndDeletedFalseAndIdNot(String username, Long id);
}
