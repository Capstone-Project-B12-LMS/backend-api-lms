package com.capstoneprojectb12.lms.backendapilms.models.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    public Optional<Role> findByNameEqualsIgnoreCase(String name);

    public Page<Role> findAll(Pageable pageable);
}
