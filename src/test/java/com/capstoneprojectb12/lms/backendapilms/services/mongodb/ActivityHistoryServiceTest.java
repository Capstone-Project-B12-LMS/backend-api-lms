package com.capstoneprojectb12.lms.backendapilms.services.mongodb;

import com.capstoneprojectb12.lms.backendapilms.models.entities.mongodb.ActivityHistory;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.mongodb.ActivityHistoryRepository;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.AnyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import static com.capstoneprojectb12.lms.backendapilms.services.UserServiceTest.user;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.getResponse;
import static org.junit.jupiter.api.Assertions.*;
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
			.userEmail(user.getEmail())
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
		// success
		when(this.userService.getCurrentUser()).thenReturn("myemail@gmail.com");
		when(this.userRepository.findById(anyString())).thenReturn(Optional.of(user));
		when(this.activityHistoryRepository.save(any(ActivityHistory.class))).thenReturn(activityHistory);
		this.activityHistoryService.save("This is something");
		reset(this.userRepository, this.userService, this.activityHistoryRepository);
		
		// any exception
		when(this.userService.getCurrentUser()).thenThrow(AnyException.class);
		this.activityHistoryService.save("This is something");
		reset(this.userService);
	}
	
	@Test
	public void testFindByUserEmail() {
		// success
		when(this.activityHistoryRepository.findByUserEmailEqualsIgnoreCase(anyString())).thenReturn(new ArrayList<>(List.of(activityHistory)));
		var res = this.activityHistoryService.findByUserEmail("email");
		var api = getResponse(res);
		var data = extract(new ArrayList<ActivityHistory>(), res);
		
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertNotNull(api);
		assertInstanceOf(ArrayList.class, api.getData());
		assertInstanceOf(ActivityHistory.class, data.get().get(0));
		reset(this.activityHistoryRepository);
		
		// any errors
		when(this.activityHistoryRepository.findByUserEmailEqualsIgnoreCase(anyString())).thenThrow(AnyException.class);
		res = this.activityHistoryService.findByUserEmail("email");
		api = getResponse(res);
		data = extract(new ArrayList<>(), res);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
	}
}
