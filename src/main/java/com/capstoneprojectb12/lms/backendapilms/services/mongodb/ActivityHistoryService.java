package com.capstoneprojectb12.lms.backendapilms.services.mongodb;

import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.models.entities.mongodb.ActivityHistory;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.mongodb.ActivityHistoryRepository;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.err;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.ok;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ActivityHistoryService {
	private final ActivityHistoryRepository activityHistoryRepository;
	private final UserService userService;
	private final UserRepository userRepository;

	public void save(String content) {
		try {
			var user = this.userRepository
					.findByEmailEqualsIgnoreCase(userService.getCurrentUser())
					.orElseGet(() -> User.builder().email("system@gmail.com").build());
			var activityHostory = ActivityHistory.builder()
					.user(user)
					.content(content)
					.build();
			this.activityHistoryRepository.save(activityHostory);
			log.info("Record activity history");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public ResponseEntity<?> findByUserId(String userId) {
		try {
			var activities = this.activityHistoryRepository.findByUserId(userId);
			return ok(activities);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
}
