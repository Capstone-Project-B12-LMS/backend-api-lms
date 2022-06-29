package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.guidance.GuidanceNew;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Guidance;
import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.GuidanceStatus;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.ClassRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.GuidanceRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.ClassNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.UserNotFoundException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(value = {MockitoExtension.class})
@Slf4j
@Tag(value = "guidanceServiceTest")
public class GuidanceServiceTest {
	public static final Guidance guidance = Guidance.builder()
			.id("id")
			.topic("topic name")
			.content("this is content")
			.user(UserServiceTest.user)
			.classEntity(ClassServiceTest.classEntity)
			.status(GuidanceStatus.ACCEPTED)
			.build();
	
	public static final GuidanceNew guidanceNew = GuidanceNew.builder()
			.topic("topic name")
			.content("this is content")
			.classId(ClassServiceTest.classEntity.getId())
			.userId(UserServiceTest.user.getId())
			.status("ACCEPTED")
			.build();
	
	@MockBean
	private ClassRepository classRepository;
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private GuidanceRepository guidanceRepository;
	@Autowired
	private GuidanceService guidanceService;
	
	@Test
	public void testToEntity() {
//	success
		when(this.userRepository.findById(anyString())).thenReturn(Optional.of(UserServiceTest.user));
		when(this.classRepository.findById(anyString())).thenReturn(Optional.of(ClassServiceTest.classEntity));
		var result = this.guidanceService.toEntity(guidanceNew);
		assertNotNull(result);
		assertEquals(guidance.getTopic(), result.getTopic());
		assertEquals(guidance.getContent(), result.getContent());
		assertEquals(guidance.getUser().getEmail(), result.getUser().getEmail());
		assertEquals(guidance.getClassEntity().getId(), result.getClassEntity().getId());
		assertEquals(GuidanceStatus.ACCEPTED, result.getStatus());
		reset(this.classRepository, this.userRepository, this.guidanceRepository);

//		user not found
		when(this.userRepository.findById(anyString())).thenReturn(Optional.empty());
		when(this.classRepository.findById(anyString())).thenReturn(Optional.of(ClassServiceTest.classEntity));
		assertThrows(UserNotFoundException.class, () -> this.guidanceService.toEntity(guidanceNew));
		reset(this.classRepository, this.userRepository, this.guidanceRepository);

//		class not found
		when(this.userRepository.findById(anyString())).thenReturn(Optional.of(UserServiceTest.user));
		when(this.classRepository.findById(anyString())).thenReturn(Optional.empty());
		assertThrows(ClassNotFoundException.class, () -> this.guidanceService.toEntity(guidanceNew));
	}
}
