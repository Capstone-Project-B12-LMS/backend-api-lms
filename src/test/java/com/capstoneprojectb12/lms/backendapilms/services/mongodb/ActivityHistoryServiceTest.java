package com.capstoneprojectb12.lms.backendapilms.services.mongodb;

import com.capstoneprojectb12.lms.backendapilms.models.entities.mongodb.ActivityHistory;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.mongodb.ActivityHistoryRepository;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.AnyException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.capstoneprojectb12.lms.backendapilms.services.UserServiceTest.user;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
@Tag(value = "activityHistoryTest")
@ExtendWith(value = {MockitoExtension.class})
public class ActivityHistoryServiceTest {
	public static final ActivityHistory activityHistory = ActivityHistory.builder()
			.id("id")
			.user(user)
			.content("This is something")
			.build();
	
	@MockBean
	private ActivityHistoryRepository activityHistoryRepository;
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private UserService userService;
	
	@Autowired
	private ActivityHistoryService activityHistoryService;
	
	@Test
	public void testSave() {
//	success
		when(this.userService.getCurrentUser()).thenReturn("myemail@gmail.com");
		when(this.userRepository.findById(anyString())).thenReturn(Optional.of(user));
		when(this.activityHistoryRepository.save(any(ActivityHistory.class))).thenReturn(activityHistory);
		this.activityHistoryService.save("This is something");
		reset(this.userRepository, this.userService, this.activityHistoryRepository);

//		any exception
		when(this.userService.getCurrentUser()).thenThrow(AnyException.class);
		this.activityHistoryService.save("This is something");
	}
}
