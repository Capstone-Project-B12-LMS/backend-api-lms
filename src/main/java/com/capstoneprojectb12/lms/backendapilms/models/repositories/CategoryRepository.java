package com.capstoneprojectb12.lms.backendapilms.models.repositories;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
	Optional<Category> findByNameEqualsIgnoreCase(String name);
}
