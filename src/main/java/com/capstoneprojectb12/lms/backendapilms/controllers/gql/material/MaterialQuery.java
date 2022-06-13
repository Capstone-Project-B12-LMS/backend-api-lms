package com.capstoneprojectb12.lms.backendapilms.controllers.gql.material;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseQuery;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Material;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "MaterialQuery")
public class MaterialQuery implements BaseQuery<Material> {
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
	public Material findById(String id) {
//		TODO: implement find material by id
		return null;
	}
}
