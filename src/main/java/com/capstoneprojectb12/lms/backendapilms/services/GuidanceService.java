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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

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
	private final EntityManager entityManager;

	@Override
	public ResponseEntity<?> save(GuidanceNew newEntity) {
		try {
			var guidance = this.toEntity(newEntity);
			guidance = this.guidanceRepository.save(guidance);

			history.save(youAreSuccessfully(String.format("create new Guidance on Class \"%s\"", this.classRepository.findById(newEntity.getClassId()).get().getName())));

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

			final var guidanceTopic = guidance.getTopic();
			history.save(youAreSuccessfully(String.format("delete Guidance \"%s\"", guidanceTopic)));

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
						.orElseThrow(UserNotFoundException::new))
				.classEntity(this.classRepository.findById(newEntity.getClassId())
						.orElseThrow(ClassNotFoundException::new))
				.content(newEntity.getContent())
				.build();
	}

	public ResponseEntity<?> findAllByClassId(String classId) {
		return this.findAllByClassId(classId, false);
	}

	public ResponseEntity<?> findAllByClassId(String classId, boolean showDeleted) {
		try {
			var session = entityManager.unwrap(Session.class);
			var filter = session.enableFilter("showDeleted");

			filter.setParameter("isDeleted", showDeleted);
			var guidance = this.guidanceRepository.findByClassEntityId(classId);
			session.disableFilter("showDeleted");

			return ok(guidance);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
}
