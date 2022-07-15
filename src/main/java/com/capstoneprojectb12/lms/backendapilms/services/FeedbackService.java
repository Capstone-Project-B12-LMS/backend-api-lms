package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.feedback.FeedbackNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.feedback.FeedbackUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Feedback;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.ClassRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.FeedbackRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.services.mongodb.ActivityHistoryService;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.ClassNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.UserNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.*;
import static com.capstoneprojectb12.lms.backendapilms.utilities.histories.ActivityHistoryUtils.youAreSuccessfully;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FeedbackService implements BaseService<Feedback, FeedbackNew, FeedbackUpdate> {
	private final ClassRepository classRepository;
	private final UserRepository userRepository;
	private final FeedbackRepository feedbackRepository;
	private final ActivityHistoryService history;
	private final UserService userService;
	
	@Override
	public ResponseEntity<?> save(FeedbackNew newEntity) {
		try {
			if (this.feedbackRepository.existsByUserEmailEqualsIgnoreCaseAndClassEntityId(this.userService.getCurrentUser(), newEntity.getClassId())) {
				log.error("Already created feedback");
				return bad("Already created feedback");
			}
			var feedback = this.toEntity(newEntity);
			feedback = this.feedbackRepository.save(feedback);
			
			history.save(youAreSuccessfully(String.format("create Feedback for Class \"%s\"", this.classRepository.findById(newEntity.getClassId()).get().getName())));
			
			return ok(feedback);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
			return bad(e.getMessage());
		} catch (UserNotFoundException e) {
			log.error(e.getMessage());
			return bad(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	@Override
	public ResponseEntity<?> update(String entityId, FeedbackUpdate updateEntity) {
		return null;
	}
	
	@Override
	public ResponseEntity<?> deleteById(String id) {
		return null;
	}
	
	@Override
	public ResponseEntity<?> findById(String id) {
		return null;
	}
	
	@Override
	public ResponseEntity<?> findAll() {
		return null;
	}
	
	@Override
	public ResponseEntity<?> findAll(boolean showDeleted) {
		return null;
	}
	
	@Override
	public ResponseEntity<?> findAll(int page, int size) {
		return null;
	}
	
	@Override
	public ResponseEntity<?> findAll(int page, int size, Sort sort) {
		return null;
	}
	
	@Override
	public PaginationResponse<List<Feedback>> toPaginationResponse(Page<Feedback> page) {
		return null;
	}
	
	@Override
	public Feedback toEntity(FeedbackNew newEntity) {
		return Feedback.builder()
				.classEntity(this.classRepository.findById(newEntity.getClassId()).orElseThrow(ClassNotFoundException :: new))
				.user(this.userRepository.findByEmailEqualsIgnoreCase(this.userService.getCurrentUser()).orElseThrow(UserNotFoundException :: new))
				.content(newEntity.getContent().trim())
				.build();
	}
	
	public ResponseEntity<?> findAllByClassId(String classId) {
		try {
			var feedbacks = this.feedbackRepository.findByClassEntityId(classId);
			return ok(feedbacks);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
}
