package com.capstoneprojectb12.lms.backendapilms.controllers.gql.material;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseMutation;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.material.MaterialNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.material.MaterialUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Material;
import com.capstoneprojectb12.lms.backendapilms.services.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "MaterialMutation")
public class MaterialMutation implements BaseMutation<Material, MaterialNew, MaterialUpdate> {
	private final MaterialService materialService;
	
	@Override
	public Material save(MaterialNew request) {
		return extract(new Material(), this.materialService.save(request)).orElse(null);
	}
	
	@Override
	public Material update(String id, MaterialUpdate request) {
//		TODO: implement update material
		return null;
	}
	
	@Override
	public ResponseDelete<Material> deleteById(String id) {
//		TODO: implement delete material by id
		return null;
	}
}
