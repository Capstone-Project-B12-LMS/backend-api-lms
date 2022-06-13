package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.ClassRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.FinalVariable;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.ClassNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.DataNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.MethodNotImplementedException;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClassService implements BaseService<Class, ClassNew, ClassUpdate> {
	private final ClassRepository classRepository;
	
	
	@Override
	public ResponseEntity<?> update(String entityId, ClassUpdate classUpdate) {
		try {
			var classEntity = this.classRepository.findById(entityId)
					.orElseThrow(ClassNotFoundException :: new);
			
			classEntity.setName(classUpdate.getName());
			classEntity.setRoom(classUpdate.getRoom());
			classEntity.setStatus(classUpdate.getStatus());
			
			classEntity = this.classRepository.save(classEntity);
			log.info(FinalVariable.UPDATE_SUCCESS);
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
			var savedClass = Optional.of(this.classRepository.save(classEntity));
			log.info(FinalVariable.SAVE_SUCCESS);
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
			}
			return (value.isPresent()) ? ok(value.get()) : bad(FinalVariable.DATA_NOT_FOUND);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	
	@Override
	public ResponseEntity<?> findById(String id) {
		try {
			var value = this.classRepository.findById(id)
					.orElseThrow(DataNotFoundException :: new);
			return ok(value);
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
		try {
			var values = this.classRepository.findAll();
			return ok(values);
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
			var classPage = this.classRepository.findAll(pageable);
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
}
