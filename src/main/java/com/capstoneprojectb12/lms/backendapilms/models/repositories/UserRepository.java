package com.capstoneprojectb12.lms.backendapilms.models.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstoneprojectb12.lms.backendapilms.models.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public Optional<User> findByEmailEqualsIgnoreCase(String email);

    public Page<User> findAll(Pageable pageable);
}
