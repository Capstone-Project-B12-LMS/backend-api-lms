package com.capstoneprojectb12.lms.backendapilms.models.repositories;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Guidance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuidanceRepository extends JpaRepository<Guidance, String> {
	List<Guidance> findByClassEntityId(String classEntityId);
}
