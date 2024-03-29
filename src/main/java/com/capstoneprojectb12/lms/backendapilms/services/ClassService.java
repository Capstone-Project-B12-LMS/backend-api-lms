package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassResponse;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.JoinClass;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.ClassStatus;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.ClassRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.services.mongodb.ActivityHistoryService;
import com.capstoneprojectb12.lms.backendapilms.utilities.FinalVariable;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.ClassNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.DataNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.UserNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete.deleted;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.*;
import static com.capstoneprojectb12.lms.backendapilms.utilities.histories.ActivityHistoryUtils.youAreSuccessfully;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClassService implements BaseService<Class, ClassNew, ClassUpdate> {
	private final ClassRepository classRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	private final ActivityHistoryService history;
	private final EntityManager entityManager;
	
	@Override
	public ResponseEntity<?> update(String entityId, ClassUpdate classUpdate) {
		try {
			var classEntity = this.classRepository.findById(entityId)
					.orElseThrow(ClassNotFoundException :: new);
			
			classEntity.setName(classUpdate.getName());
			classEntity.setRoom(classUpdate.getRoom());
			classEntity.setStatus(classUpdate.getStatus());
			classEntity.setReportUrl(classUpdate.getReportUrl());
			
			classEntity = this.classRepository.save(classEntity);
			log.info(FinalVariable.UPDATE_SUCCESS);
			
			final var className = classEntity.getName();
			history.save(youAreSuccessfully("updated Class " + className));
			
			return ok(classEntity);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
			return bad(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	@Override
	public ResponseEntity<?> save(ClassNew newEntity) {
		try {
			var classEntity = this.toEntity(newEntity);
			var currentUserEmail = this.userService.getCurrentUser();
			var currentUser = this.userRepository.findByEmailEqualsIgnoreCase(currentUserEmail).orElseThrow(UserNotFoundException :: new);
			
			classEntity.setUsers(new ArrayList<User>() {
				{
					add(currentUser);
				}
			});
			
			var savedClass = Optional.of(this.classRepository.save(classEntity));
			log.info(FinalVariable.SAVE_SUCCESS);
			
			history.save(youAreSuccessfully(String.format("created new Class \"%s\"", savedClass.get().getName())));
			
			return ok(savedClass.get());
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	@Override
	public ResponseEntity<?> deleteById(String id) {
		try {
			var value = this.classRepository.findById(id);
			if (value.isPresent()) {
				this.classRepository.deleteById(id);
				log.info(FinalVariable.DELETE_SUCCESS);
				value.get().setIsDeleted(true);
				history.save(youAreSuccessfully(String.format("deleted Class \"%s\"", value.get().getName())));
			}
			return (value.isPresent()) ? ok(deleted(value.get())) : bad(FinalVariable.DATA_NOT_FOUND);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	public ResponseEntity<?> deleteUserById(String classId, String userId) {
		try {
			var classEntity = this.classRepository.findById(classId).orElseThrow(ClassNotFoundException :: new);
			final var teacher = classEntity.getCreatedBy();
			if (! classEntity.getUsers().removeIf((u) -> u.getId().equals(userId) && (! teacher.equalsIgnoreCase(u.getEmail())))) {
				return bad("user not found");
			}
			classEntity = this.classRepository.save(classEntity);
			
			final var className = classEntity.getName();
			history.save(youAreSuccessfully(String.format("deleted %s on Class \"%s\"", this.userRepository.findById(userId).get().getEmail(), className)));
			return ok(classEntity);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
			return bad(e.getMessage());
		}
		// catch (Exception e) {
		// log.error(e.getMessage());
		// return err(e);
		// }
	}
	
	@Override
	public ResponseEntity<?> findById(String id) {
		try {
			var value = this.classRepository.findById(id).orElseThrow(DataNotFoundException :: new);
			return ok(ClassResponse.parseFromClass(value));
		} catch (DataNotFoundException e) {
			log.warn(FinalVariable.DATA_NOT_FOUND);
			return bad(FinalVariable.DATA_NOT_FOUND);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	@Override
	public ResponseEntity<?> findAll() {
		return this.findAll(false);
	}
	
	@Override
	public ResponseEntity<?> findAll(boolean showDeleted) {
		try {
			var session = entityManager.unwrap(Session.class);
			var filter = session.enableFilter("showDeleted");
			
			filter.setParameter("isDeleted", showDeleted);
			var classes = this.classRepository.findAll();
			session.disableFilter("showDeleted");
			
			var responses = new ArrayList<ClassResponse>();
			classes.forEach((c) -> responses.add(ClassResponse.parseFromClass(c)));
			return ok(responses);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	@Override
	public ResponseEntity<?> findAll(int page, int size) {
		return this.findAll(page, size, Sort.unsorted());
	}
	
	@Override
	public ResponseEntity<?> findAll(int page, int size, Sort sort) {
		return this.findAll(page, size, sort, false);
	}
	
	public ResponseEntity<?> findAll(int page, int size, Sort sort, boolean showDeleted) {
		try {
			Pageable pageable = PageRequest.of(page, size, sort);
			var session = entityManager.unwrap(Session.class);
			var filter = session.enableFilter("showDeleted");
			
			filter.setParameter("isDeleted", showDeleted);
			var classPage = this.classRepository.findAll(pageable);
			session.disableFilter("showDeleted");
			
			var pageResponse = this.toPaginationResponse(classPage);
			return ok(pageResponse);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	@Override
	public PaginationResponse<List<Class>> toPaginationResponse(Page<Class> page) {
		return PaginationResponse.<List<Class>>builder()
				.page(page.getPageable().getPageNumber())
				.size(page.getPageable().getPageSize())
				.totalPage(page.getTotalPages())
				.totalSize(page.getTotalElements())
				.data(page.getContent())
				.build();
	}
	
	@Override
	public Class toEntity(ClassNew classNew) {
		return Class.builder()
				.name(classNew.getName())
				.room(classNew.getRoom())
				.build();
	}
	
	public ResponseEntity<?> joinUserToClass(JoinClass request) {
		try {
			var classEntity = this.classRepository.findByCode(request.getClassCode()).orElseThrow(ClassNotFoundException :: new);
			var user = this.userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException :: new);
			
			var users = new HashSet<>(classEntity.getUsers());
			users.add(user);
			classEntity.setUsers(new ArrayList<>(users));
			classEntity = this.classRepository.save(classEntity);
			
			final var className = classEntity.getName();
			history.save(youAreSuccessfully(String.format("joined to Class \"%s\"", className)));
			
			return ok(classEntity);
		} catch (ClassNotFoundException e) {
			log.warn(e.getMessage());
			return bad(e.getMessage());
		} catch (UserNotFoundException e) {
			log.warn(e.getMessage());
			return bad(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	public ResponseEntity<?> joinUserToClass(String classCode, String userId) {
		return this.joinUserToClass(JoinClass.builder().classCode(classCode).userId(userId).build());
	}
	
	public ResponseEntity<?> findByUserId(String id, String status) {
		try {
			var classStatus = ClassStatus.valueOf(status.trim().toUpperCase());
			var classes = this.classRepository.findByUsersIdAndStatus(id, classStatus);
			return ok(classes);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	public ResponseEntity<?> updateStatus(String classId, ClassStatus status) {
		try {
			var classEntity = this.classRepository.findById(classId).orElseThrow(ClassNotFoundException :: new);
			var oldStatus = classEntity.getStatus();
			classEntity.setStatus(status);
			this.classRepository.save(classEntity);
			history.save(String.format("You change the status of Class \"%s\" from \"%s\" to \"%s\"", classEntity.getName(), oldStatus.name().replace("_", " "), classEntity.getStatus().name().replace("_", " ")));
			return ok(classEntity);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
			return bad(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	public ResponseEntity<?> simpleFindAll(int page, int size) {
		return this.simpleFindAll(page, size, ClassStatus.ACTIVE);
	}
	
	public ResponseEntity<?> simpleFindAll(int page, int size, ClassStatus status) {
		try {
//         var email =    this.userRepository.findByEmailEqualsIgnoreCase(this.userService.getCurrentUser());
			var email = this.userService.getCurrentUser();
			var classPage = this.classRepository.findByIdContainsAndCreatedByEqualsIgnoreCaseOrUsersEmailEqualsIgnoreCaseAndStatus("", email, email, status, PageRequest.of(page, size));
			return ok(classPage.getContent());
		} catch (Exception e) {
			return err(e);
		}
	}
}
