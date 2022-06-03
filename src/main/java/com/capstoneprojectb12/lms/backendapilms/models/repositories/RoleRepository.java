package com.capstoneprojectb12.lms.backendapilms.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
