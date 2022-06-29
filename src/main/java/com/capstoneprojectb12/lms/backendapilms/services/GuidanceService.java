package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.guidance.GuidanceNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.guidance.GuidanceUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Guidance;
import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.GuidanceStatus;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.ClassRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.GuidanceRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
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

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GuidanceService implements BaseService<Guidance, GuidanceNew, GuidanceUpdate> {
	private final GuidanceRepository guidanceRepository;
	private final UserRepository userRepository;
	private final ClassRepository classRepository;
	
	@Override
	public ResponseEntity<?> save(GuidanceNew newEntity) {
		try {
		
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	@Override
	public ResponseEntity<?> update(String entityId, GuidanceUpdate updateEntity) {
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
	public PaginationResponse<List<Guidance>> toPaginationResponse(Page<Guidance> page) {
		return null;
	}
	
	@Override
	public Guidance toEntity(GuidanceNew newEntity) {
		return Guidance.builder()
				.topic(newEntity.getTopic())
				.user(this.userRepository.findById(newEntity.getUserId()).orElseThrow(UserNotFoundException :: new))
				.className(this.classRepository.findById(newEntity.getClassId()).orElseThrow(ClassNotFoundException :: new))
				.content(newEntity.getContent())
				.status(GuidanceStatus.valueOf(newEntity.getStatus()))
				.build();
	}
}
