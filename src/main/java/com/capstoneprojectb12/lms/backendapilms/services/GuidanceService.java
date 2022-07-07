package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.guidance.GuidanceNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.guidance.GuidanceUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Guidance;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.ClassRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.GuidanceRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.services.mongodb.ActivityHistoryService;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.ClassNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.UserNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import java.util.List;
import java.util.NoSuchElementException;
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
public class GuidanceService implements BaseService<Guidance, GuidanceNew, GuidanceUpdate> {
	private final GuidanceRepository guidanceRepository;
	private final UserRepository userRepository;
	private final ClassRepository classRepository;
	private final ActivityHistoryService history;
	
	@Override
	public ResponseEntity<?> save(GuidanceNew newEntity) {
		try {
			var guidance = this.toEntity(newEntity);
			guidance = this.guidanceRepository.save(guidance);
			
			new Thread(() -> history.save(youAreSuccessfully("create new Guidance on Class " + this.classRepository.findById(newEntity.getClassId()).get().getName()))).start();
			
			return ok(guidance);
		} catch (UserNotFoundException e) {
			log.error(e.getMessage());
			return bad(e.getMessage());
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
			return bad(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	@Override
	public ResponseEntity<?> update(String entityId, GuidanceUpdate updateEntity) {
		return null;
	}
	
	@Override
	public ResponseEntity<?> deleteById(String id) {
		try {
			var guidance = this.guidanceRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Guidance not found"));
			this.guidanceRepository.deleteById(id);
			return ok(guidance);
		} catch (NoSuchElementException e) {
			log.error(e.getMessage());
			return bad(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
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
				.user(this.userRepository.findById(newEntity.getUserId())
						.orElseThrow(UserNotFoundException :: new))
				.classEntity(this.classRepository.findById(newEntity.getClassId())
						.orElseThrow(ClassNotFoundException :: new))
				.content(newEntity.getContent())
				.build();
	}
	
	public ResponseEntity<?> findAllByClassId(String classId) {
		try {
			var guidance = this.guidanceRepository.findByClassEntityId(classId);
			return ok(guidance);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
}
