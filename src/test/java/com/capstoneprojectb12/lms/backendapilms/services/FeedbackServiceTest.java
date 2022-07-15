package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.feedback.FeedbackNew;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Feedback;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.ClassRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.FeedbackRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.AnyException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.getResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
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
            .classId("id")
            .content("this is content")
            .build();

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ClassRepository classRepository;

    @Autowired
    private FeedbackService feedbackService;
    @MockBean
    private FeedbackRepository feedbackRepository;
    @MockBean
    private UserService userService;

    @Test
    public void testToEntity() {
//	success
        when(this.classRepository.findById(anyString())).thenReturn(Optional.of(ClassServiceTest.classEntity));
        when(this.userRepository.findByEmailEqualsIgnoreCase(anyString())).thenReturn(Optional.of(UserServiceTest.user));
        when(this.userService.getCurrentUser()).thenReturn(UserServiceTest.user.getEmail());
        var data = this.feedbackService.toEntity(feedbackNew);
        assertNotNull(data);
        assertNotNull(data.getClassEntity());
        assertNotNull(data.getUser());
        assertEquals(ClassServiceTest.classEntity.getId(), data.getClassEntity().getId());
        assertEquals(UserServiceTest.user.getId(), data.getUser().getId());
        assertEquals(feedbackNew.getContent(), data.getContent());
    }

    @Test
    public void testSave() {
//		success
        when(this.classRepository.findById(anyString())).thenReturn(Optional.of(ClassServiceTest.classEntity));
        when(this.userRepository.findByEmailEqualsIgnoreCase(anyString())).thenReturn(Optional.of(UserServiceTest.user));
        when(this.feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);
        when(this.userService.getCurrentUser()).thenReturn(UserServiceTest.user.getEmail());
        var res = this.feedbackService.save(feedbackNew);
        var api = getResponse(res);
        var data = extract(feedback, api);
        assertNotNull(res);
        assertNotNull(api);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertNull(api.getErrors());
        assertTrue(api.isStatus());
        assertNotNull(api.getData());
        assertTrue(data.isPresent());
        assertEquals(feedbackNew.getClassId(), data.get().getClassEntity().getId());
        assertNotNull(data.get().getUser());
        assertEquals(feedbackNew.getContent(), data.get().getContent());
        assertNotNull(data.get().getId());
        reset(this.feedbackRepository, this.classRepository, this.userRepository);

//		success
		when(this.classRepository.findById(anyString())).thenReturn(Optional.of(ClassServiceTest.classEntity));
		when(this.userRepository.findByEmailEqualsIgnoreCase(anyString())).thenReturn(Optional.of(UserServiceTest.user));
		when(this.feedbackRepository.existsByUserEmailEqualsIgnoreCaseAndClassEntityId(anyString(), anyString())).thenReturn(true);
		when(this.feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);
		when(this.userService.getCurrentUser()).thenReturn(UserServiceTest.user.getEmail());
		res = this.feedbackService.save(feedbackNew);
		api = getResponse(res);
		data = extract(feedback, api);
		assertNotNull(res);
		assertNotNull(api);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertNotNull(api.getErrors());
		assertFalse(api.isStatus());
		assertNull(api.getData());
		assertTrue(data.isEmpty());
		reset(this.feedbackRepository, this.classRepository, this.userRepository);

//		class not found
        when(this.classRepository.findById(anyString())).thenReturn(Optional.empty());
        when(this.userRepository.findByEmailEqualsIgnoreCase(anyString())).thenReturn(Optional.of(UserServiceTest.user));
        when(this.feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);
        res = this.feedbackService.save(feedbackNew);
        api = getResponse(res);
        data = extract(feedback, api);
        assertNotNull(res);
        assertNotNull(api);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        assertNotNull(api.getErrors());
        assertFalse(api.isStatus());
        assertNull(api.getData());
        assertTrue(data.isEmpty());
        reset(this.feedbackRepository, this.classRepository, this.userRepository);

//		user not found
        when(this.classRepository.findById(anyString())).thenReturn(Optional.of(ClassServiceTest.classEntity));
        when(this.userRepository.findByEmailEqualsIgnoreCase(anyString())).thenReturn(Optional.empty());
        when(this.feedbackRepository.save(any(Feedback.class))).thenReturn(feedback);
        res = this.feedbackService.save(feedbackNew);
        api = getResponse(res);
        data = extract(feedback, api);
        assertNotNull(res);
        assertNotNull(api);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        assertNotNull(api.getErrors());
        assertFalse(api.isStatus());
        assertNull(api.getData());
        assertTrue(data.isEmpty());
        reset(this.feedbackRepository, this.classRepository, this.userRepository);

//		any exception
        when(this.classRepository.findById(anyString())).thenReturn(Optional.of(ClassServiceTest.classEntity));
        when(this.userRepository.findByEmailEqualsIgnoreCase(anyString())).thenReturn(Optional.of(UserServiceTest.user));
        when(this.feedbackRepository.save(any(Feedback.class))).thenThrow(AnyException.class);
        res = this.feedbackService.save(feedbackNew);
        api = getResponse(res);
        data = extract(feedback, api);
        assertNotNull(res);
        assertNotNull(api);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
        assertNotNull(api.getErrors());
        assertFalse(api.isStatus());
        assertNull(api.getData());
        assertTrue(data.isEmpty());
        reset(this.feedbackRepository, this.classRepository, this.userRepository);
    }

    @Test
    public void testFindAllByClassId() {
//		success
        when(this.feedbackRepository.findByClassEntityId(anyString())).thenReturn(new ArrayList<>(List.of(feedback)));
        var res = this.feedbackService.findAllByClassId("id");
        var api = getResponse(res);
        var data = extract(new ArrayList<Feedback>(), api);
        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertNotNull(api);
        assertNull(api.getErrors());
        assertTrue(api.isStatus());
        assertTrue(data.isPresent());
        assertEquals(feedback.getId(), data.get().get(0).getId());
        reset(this.feedbackRepository);

//		any exception
        when(this.feedbackRepository.findByClassEntityId(anyString())).thenThrow(AnyException.class);
        res = this.feedbackService.findAllByClassId("id");
        api = getResponse(res);
        data = extract(new ArrayList<Feedback>(), api);
        assertNotNull(res);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
        assertNotNull(api);
        assertNotNull(api.getErrors());
        assertFalse(api.isStatus());
        assertTrue(data.isEmpty());
    }
}
