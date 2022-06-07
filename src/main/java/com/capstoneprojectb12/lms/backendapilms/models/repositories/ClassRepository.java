package com.capstoneprojectb12.lms.backendapilms.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;

@Repository
public interface ClassRepository extends JpaRepository<Class, String> {
    public List<Class> findByCreateByEqualsIgnoreCase(String createdBy);
}
