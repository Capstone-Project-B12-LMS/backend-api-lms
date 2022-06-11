package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.material.MaterialNew;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Material;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.MaterialRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.ClassNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MaterialService implements BaseService<Material> {
	private final MaterialRepository materialRepository;
	private final ClassService classService;
	
	
	@Override
	public Optional<Material> save(Material entity) {
		return Optional.ofNullable(this.materialRepository.save(entity));
	}
	
	@Override
	public Optional<Material> update(Material entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean deleteById(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean existsById(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Optional<Material> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Material> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Material> findAll(boolean showDeleted) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Page<Material> findAll(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Page<Material> findAll(int page, int size, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public PaginationResponse<List<Material>> toPaginationResponse(Page<Material> page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Material toEntity(MaterialNew materialNew) throws ClassNotFoundException {
		return Material.builder()
				.classes(this.classService
						.findById(materialNew
								.getClassId())
						.orElseThrow(ClassNotFoundException :: new))
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
