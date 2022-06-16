package com.capstoneprojectb12.lms.backendapilms.models.repositories;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Material;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, String> {
	Optional<List<Material>> findByClassesId(String classId);
}
