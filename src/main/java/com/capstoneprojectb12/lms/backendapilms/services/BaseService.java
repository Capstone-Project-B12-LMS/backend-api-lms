package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

public interface BaseService<ENTITY, NEW, UPDATE> extends Serializable {
	ResponseEntity<?> save(NEW newEntity);
	
	ResponseEntity<?> update(String entityId, UPDATE updateEntity);
	
	ResponseEntity<?> deleteById(String id);
	
	ResponseEntity<?> findById(String id);
	
	ResponseEntity<?> findAll();
	
	ResponseEntity<?> findAll(boolean showDeleted);
	
	ResponseEntity<?> findAll(int page, int size);
	
	ResponseEntity<?> findAll(int page, int size, Sort sort);
	
	PaginationResponse<List<ENTITY>> toPaginationResponse(Page<ENTITY> page);
	
	ENTITY toEntity(NEW newEntity);
	
}
