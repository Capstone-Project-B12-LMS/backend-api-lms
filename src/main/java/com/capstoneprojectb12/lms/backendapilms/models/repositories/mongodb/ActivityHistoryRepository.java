package com.capstoneprojectb12.lms.backendapilms.models.repositories.mongodb;

import com.capstoneprojectb12.lms.backendapilms.models.entities.mongodb.ActivityHistory;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityHistoryRepository extends MongoRepository<ActivityHistory, String> {
	List<ActivityHistory> findByUserEmail(String userEmail);
}
