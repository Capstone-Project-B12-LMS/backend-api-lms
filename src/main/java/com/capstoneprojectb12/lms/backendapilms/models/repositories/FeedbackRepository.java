package com.capstoneprojectb12.lms.backendapilms.models.repositories;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Feedback;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {
	List<Feedback> findByClassEntityId(String classId);
	
	List<Feedback> findByUserId(String classId);
}
