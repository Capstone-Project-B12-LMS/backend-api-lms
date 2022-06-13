package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.material.MaterialNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.material.MaterialUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Material;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.ClassRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.MaterialRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.FinalVariable;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.ClassNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.DataNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.MethodNotImplementedException;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MaterialService implements BaseService<Material, MaterialNew, MaterialUpdate> {
	private final MaterialRepository materialRepository;
	private final ClassRepository classRepository;
	
	@Override
	public ResponseEntity<?> save(MaterialNew newEntity) {
		try {
			var material = this.toEntity(newEntity);
			material = this.materialRepository.save(material);
			return ok(material);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	@Override
	public ResponseEntity<?> update(String entityId, MaterialUpdate updateEntity) {
		try {
			var temp = this.toEntity(updateEntity);
			var material = this.materialRepository.findById(entityId).orElseThrow(DataNotFoundException :: new);
			
			material.setClasses(temp.getClasses());
			material.setContent(temp.getContent());
			material.setPoint(temp.getPoint());
			material.setDeadline(temp.getDeadline());
			material.setTitle(temp.getTitle());
//			material.setVideoUri(); // TODO: create file service first
//			material.setFileUrl(); // TODO: create file service first
//			material.setTopic(); // TODO: create topic repo/service first
			
			material = this.materialRepository.save(material);
			return ok(material);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	@Override
	public ResponseEntity<?> deleteById(String id) {
		try {
			var material = this.materialRepository.findById(id);
			if (material.isEmpty()) {
				log.warn(FinalVariable.DATA_NOT_FOUND);
				return bad(FinalVariable.DATA_NOT_FOUND);
			}
			this.classRepository.deleteById(id);
			log.info(FinalVariable.DELETE_SUCCESS);
			return ok(material.get());
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	@Override
	public ResponseEntity<?> findById(String id) {
		try {
			var material = this.materialRepository.findById(id).orElseThrow(DataNotFoundException :: new);
			return ok(material);
		} catch (DataNotFoundException e) {
			log.error(e.getMessage());
			return bad(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	@Override
	public ResponseEntity<?> findAll() {
		try {
			var materials = this.materialRepository.findAll();
			return ok(materials);
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
			var request = PageRequest.of(page, size, sort);
			var materials = this.materialRepository.findAll(request);
			var responsePage = this.toPaginationResponse(materials);
			return ok(materials);
		} catch (Exception e) {
			log.error(e.getMessage());
			return err(e);
		}
	}
	
	@Override
	public PaginationResponse<List<Material>> toPaginationResponse(Page<Material> page) {
		return PaginationResponse.<List<Material>>builder()
				.page(page.getNumber())
				.size(page.getSize())
				.totalPage(page.getTotalPages())
				.totalSize(page.getTotalElements())
				.data(page.getContent())
				.build();
	}
	
	@Override
	public Material toEntity(MaterialNew materialNew) {
		return Material.builder()
				.classes(this.classRepository.findById(materialNew.getClassId()).orElseThrow(ClassNotFoundException :: new))
//				.category() // TODO: create when category not found
//				.topic(materialNew.getTopicId()) // TODO: find topic by id or maybe create new topic if not exists
//				.fileUrl() // TODO: save file first
				.content(materialNew.getContent())
				.point(materialNew.getPoint())
				.deadline(materialNew.getDeadline())
				.title(materialNew.getTitle())
				.build();
	}
}
