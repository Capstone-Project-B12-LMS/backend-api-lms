package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.guidance.GuidanceNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.guidance.GuidanceUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Guidance;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

public class GuidanceService implements BaseService<Guidance, GuidanceNew, GuidanceUpdate> {
	@Override
	public ResponseEntity<?> save(GuidanceNew newEntity) {
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
		return null;
	}
}
