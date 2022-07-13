package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;
import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.RoleRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.FinalVariable;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.DataNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.UserNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements BaseService<User, UserNew, UserUpdate>, UserDetailsService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final EntityManager entityManager;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
	}

	@Override
	public ResponseEntity<?> save(UserNew newEntity) {
		try {
			newEntity.setPassword(passwordEncoder.encode(newEntity.getPassword()));
			var user = this.toEntity(newEntity);
			user = this.userRepository.save(user);
			return ok(user);
		} catch (DataIntegrityViolationException e) {
			log.warn(FinalVariable.ALREADY_EXISTS);
			return bad(FinalVariable.ALREADY_EXISTS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}

	@Override
	public ResponseEntity<?> update(String entityId, UserUpdate updateEntity) {
		try {
			var user = this.userRepository.findById(entityId).orElseThrow(UserNotFoundException::new);

			user.setFullName(updateEntity.getFullName());
			user.setEmail(updateEntity.getEmail());
			user.setTelepon(updateEntity.getTelepon());

			user = this.userRepository.save(user);
			return ok(user);
		} catch (UserNotFoundException e) {
			log.warn(e.getMessage());
			return bad(notFound(e.getMessage()));
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}

	@Override
	public ResponseEntity<?> deleteById(String id) {
		try {
			var user = this.userRepository.findById(id);
			if (user.isPresent() && !user.get().getIsDeleted()) {
				this.userRepository.deleteById(id);
				log.info(FinalVariable.DELETE_SUCCESS);
				return ok(user.get());
			}
			log.error(FinalVariable.DATA_NOT_FOUND);
			return bad(FinalVariable.DATA_NOT_FOUND);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}

	@Override
	public ResponseEntity<?> findById(String id) {
		return this.findById(id, false);
	}

	public ResponseEntity<?> findById(String id, boolean showDeleted) {
		try {
			var session = entityManager.unwrap(Session.class);
			var filter = session.enableFilter("showDeleted");

			filter.setParameter("isDeleted", showDeleted);
			var user = this.userRepository.findById(id).orElseThrow(DataNotFoundException::new);
			session.disableFilter("showDeleted");

			return ok(user);
		} catch (DataNotFoundException e) {
			log.warn(e.getMessage());
			return bad(notFound(e.getMessage()));
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
			var users = this.userRepository.findAll();
			session.disableFilter("showDeleted");

			return ok(users);
		} catch (Exception e) {
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
			var users = this.userRepository.findAll(pageable);
			session.disableFilter("showDeleted");

			var pageResponse = this.toPaginationResponse(users);
			return ok(pageResponse);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}

	@Override
	public PaginationResponse<List<User>> toPaginationResponse(Page<User> page) {
		return PaginationResponse.<List<User>>builder()
				.data(page.getContent())
				.page(page.getPageable().getPageNumber())
				.size(page.getPageable().getPageSize())
				.totalPage(page.getTotalPages())
				.totalSize(page.getTotalElements())
				.build();
	}

	@Override
	public User toEntity(UserNew userNew) {
		var role = this.roleRepository.findByNameEqualsIgnoreCase("user").orElse(null);
		if (role == null) {
			role = this.roleRepository.save(Role.builder().name("user").description("-").build());
		}
		return User.builder()
				.fullName(userNew.getFullName())
				.email(userNew.getEmail())
				.telepon(userNew.getTelepon())
				.password(userNew.getPassword())
				.roles(List.of(role))
				.build();
	}

	public Optional<User> findByEmail(String email) {
		return this.findByEmail(email, false);
	}

	public Optional<User> findByEmail(String email, boolean showDeleted) {
		var session = entityManager.unwrap(Session.class);
		var filter = session.enableFilter("showDeleted");

		filter.setParameter("isDeleted", showDeleted);
		var user = this.userRepository.findByEmailEqualsIgnoreCase(email);
		session.disableFilter("showDeleted");

		return user;
	}

	public String getCurrentUser() {
		try {
			return SecurityContextHolder.getContext().getAuthentication().getName();
		} catch (Exception e) {
			log.error("Error: " + e.getMessage());
			return "SYSTEM";
		}
	}
}
