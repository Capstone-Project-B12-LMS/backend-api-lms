package com.capstoneprojectb12.lms.backendapilms.controllers.gql.material;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseQuery;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Material;
import com.capstoneprojectb12.lms.backendapilms.services.MaterialService;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "MaterialQuery")
public class MaterialQuery implements BaseQuery<Material> {
	private final MaterialService materialService;
	
	@SchemaMapping(field = "findAllByClassId")
	public List<Material> findAllByClassId(@Argument(name = "classId") String classId) {
		return extract(List.of(new Material()), this.materialService.findAllByClassId(classId)).orElse(new ArrayList<Material>());
	}
	
	@Override
	public List<Material> findAll() {
//		TODO: implement find all material
		return null;
	}
	
	@Override
	public PaginationResponse<List<Material>> findAllWithPageable(int page, int size) {
//		TODO: implement find all material with pagination response
		return null;
	}
	
	@Override
	public List<Material> findAllDeleted() {
//		TODO: implement find all deleted material
		return null;
	}
	
	@Override
	public PaginationResponse<List<Material>> findAllDeletedWithPageable(int page, int size) {
//		TODO: implement find all deleted material with pagination
		return null;
	}
	
	
	@Override
	@SchemaMapping(field = "findById")
	public Material findById(@Argument(name = "id") String id) {
		return extract(new Material(), this.materialService.findById(id)).orElse(null);
	}
}
