package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.feedback.FeedbackNew;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Feedback;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.ClassRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(value = {MockitoExtension.class})
@Tag(value = "FeedbackServiceTest")
public class FeedbackServiceTest {
	private static final Feedback feedback = Feedback.builder()
			.id("id")
			.content("this is content")
			.user(UserServiceTest.user)
			.classEntity(ClassServiceTest.classEntity)
			.build();
	
	private static final FeedbackNew feedbackNew = FeedbackNew.builder()
			.userId("id")
			.classId("id")
			.content("this is content")
			.build();
	
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private ClassRepository classRepository;
	@Autowired
	private FeedbackService feedbackService;
	
	@Test
	public void testToEntity() {
//	success
		when(this.classRepository.findById(anyString())).thenReturn(Optional.of(ClassServiceTest.classEntity));
		when(this.userRepository.findById(anyString())).thenReturn(Optional.of(UserServiceTest.user));
		var data = this.feedbackService.toEntity(feedbackNew);
		assertNotNull(data);
		assertNotNull(data.getClassEntity());
		assertNotNull(data.getUser());
		assertEquals(ClassServiceTest.classEntity.getId(), data.getClassEntity().getId());
		assertEquals(UserServiceTest.user.getId(), data.getUser().getId());
		assertEquals(feedbackNew.getContent(), data.getContent());
	}
}
