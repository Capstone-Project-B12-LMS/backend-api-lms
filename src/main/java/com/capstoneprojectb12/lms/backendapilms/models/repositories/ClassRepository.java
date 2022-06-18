package com.capstoneprojectb12.lms.backendapilms.models.repositories;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.ClassStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, String> {
	List<Class> findByCreatedByEqualsIgnoreCase(String createdBy);
	
	Optional<Class> findByCode(String code);
	
	Page<Class> findAll(Pageable pageable);
	
	List<Class> findByUsersIdAndStatus(String userId, ClassStatus classStatus);
}
