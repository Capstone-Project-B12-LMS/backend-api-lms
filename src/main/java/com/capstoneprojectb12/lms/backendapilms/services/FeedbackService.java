package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.feedback.FeedbackNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.feedback.FeedbackUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Feedback;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.ClassRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.FeedbackRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FeedbackService implements BaseService<Feedback, FeedbackNew, FeedbackUpdate> {
	private final ClassRepository classRepository;
	private final UserRepository userRepository;
	private final FeedbackRepository feedbackRepository;
	
	@Override
	public ResponseEntity<?> save(FeedbackNew newEntity) {
		return null;
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
				.classEntity()
				.build();
	}
}
