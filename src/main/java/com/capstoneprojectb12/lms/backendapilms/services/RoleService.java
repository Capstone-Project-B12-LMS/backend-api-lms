package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.role.RoleNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.role.RoleUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.RoleRepository;
import com.capstoneprojectb12.lms.backendapilms.services.mongodb.ActivityHistoryService;
import com.capstoneprojectb12.lms.backendapilms.utilities.FinalVariable;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.DataNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.MethodNotImplementedException;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class RoleService implements BaseService<Role, RoleNew, RoleUpdate> {
	private final RoleRepository roleRepository;
	private final ActivityHistoryService history;
	
	@Override
	public ResponseEntity<?> save(RoleNew newEntity) {
		try {
			var role = this.toEntity(newEntity);
			role = this.roleRepository.save(role);
			
			new Thread(() -> history.save(youAreSuccessfully("created new Role " + newEntity.getName()))).start();
			
			return ok(role);
		} catch (DataIntegrityViolationException e) {
			log.warn(e.getMessage());
			return bad(FinalVariable.ALREADY_EXISTS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	@Override
	public ResponseEntity<?> update(String entityId, RoleUpdate updateEntity) {
		try {
			var role = this.roleRepository.findById(entityId).orElseThrow(DataNotFoundException :: new);
			
			role.setName(updateEntity.getName());
			role.setDescription(updateEntity.getDescription());
			
			role = this.roleRepository.save(role);
			
			new Thread(() -> history.save(youAreSuccessfully("update Role " + updateEntity.getName()))).start();
			
			return ok(role);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	@Override
	public ResponseEntity<?> deleteById(String id) {
		try {
			var role = this.roleRepository.findById(id);
			if (role.isPresent() && ! role.get().getIsDeleted()) {
				this.roleRepository.deleteById(id);
				log.info(FinalVariable.DELETE_SUCCESS);
				
				history.save(youAreSuccessfully(String.format("delete Role \"%s\"", role.get().getName())));
				
				return ok(role.get());
			}
			log.warn(FinalVariable.DATA_NOT_FOUND);
			return bad(FinalVariable.DATA_NOT_FOUND);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	@Override
	public ResponseEntity<?> findById(String id) {
		try {
			var role = this.roleRepository.findById(id).orElseThrow(DataNotFoundException :: new);
			return ok(role);
		} catch (DataNotFoundException e) {
			log.warn(e.getMessage());
			return bad(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	@Override
	public ResponseEntity<?> findAll() {
		try {
			var roles = this.roleRepository.findAll();
			return ok(roles);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	@Override
	public ResponseEntity<?> findAll(boolean showDeleted) {
		throw new MethodNotImplementedException();
	}
	
	@Override
	public ResponseEntity<?> findAll(int page, int size) {
		return this.findAll(page, size, Sort.unsorted());
	}
	
	@Override
	public ResponseEntity<?> findAll(int page, int size, Sort sort) {
		try {
			Pageable pageable = PageRequest.of(page, size, sort);
			var roles = this.roleRepository.findAll(pageable);
			var pageResponse = this.toPaginationResponse(roles);
			return ok(pageResponse);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	
	@Override
	public PaginationResponse<List<Role>> toPaginationResponse(Page<Role> page) {
		return PaginationResponse.<List<Role>>builder()
				.data(page.getContent())
				.page(page.getPageable().getPageNumber())
				.size(page.getPageable().getPageSize())
				.totalPage(page.getTotalPages())
				.totalSize(page.getTotalElements())
				.build();
	}
	
	@Override
	public Role toEntity(RoleNew newEntity) {
		return Role.builder()
				.name(newEntity.getName())
				.description(newEntity.getDescription())
				.build();
	}
	
	public List<Role> findByNames(String... names) {
		var roles = new HashSet<Role>();
		for (var name : names) {
			var role = this.findByName(name);
			if (role.isEmpty()) {
				var roleNew = Role.builder()
						.name(name)
						.description("-")
						.isDeleted(false)
						.build();
				role = Optional.of(this.roleRepository.save(roleNew));
			}
			role.ifPresent(roles :: add);
		}
		return new ArrayList<Role>(roles);
	}
	
	public Optional<Role> findByName(String name) {
		return this.roleRepository.findByNameEqualsIgnoreCase(name);
	}
	
}
