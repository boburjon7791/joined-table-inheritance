package com.example.joined_table_inheritance.repository;

import com.example.joined_table_inheritance.model.entity.Admin;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends BaseRepository<Admin, Long>{
    Optional<Admin> findByUsernameAndDeletedFalse(String username);

    boolean existsByUsernameAndDeletedFalse(String username);

    boolean existsByUsernameAndDeletedFalseAndIdNot(String username, Long id);
}
