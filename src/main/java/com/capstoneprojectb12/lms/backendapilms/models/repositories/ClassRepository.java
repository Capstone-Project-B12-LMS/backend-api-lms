package com.capstoneprojectb12.lms.backendapilms.models.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;

@Repository
public interface ClassRepository extends JpaRepository<Class, String> {
    public List<Class> findByCreatedByEqualsIgnoreCase(String createdBy);

    public Page<Class> findAll(Pageable pageable);
}
