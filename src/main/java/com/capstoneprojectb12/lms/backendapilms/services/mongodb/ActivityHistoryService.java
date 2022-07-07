package com.capstoneprojectb12.lms.backendapilms.services.mongodb;

import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.models.entities.mongodb.ActivityHistory;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.mongodb.ActivityHistoryRepository;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
			var savedActivityHistory = this.activityHistoryRepository.save(activityHostory);
			log.info("Record activity history");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
