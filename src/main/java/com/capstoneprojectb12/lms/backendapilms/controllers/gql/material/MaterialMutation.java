package com.capstoneprojectb12.lms.backendapilms.controllers.gql.material;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseMutation;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.material.MaterialNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.material.MaterialUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Material;
import com.capstoneprojectb12.lms.backendapilms.services.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import static com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete.deleted;
import static com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete.notDeleted;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.getResponse;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "MaterialMutation")
public class MaterialMutation implements BaseMutation<Material, MaterialNew, MaterialUpdate> {
	private final MaterialService materialService;
	
	@Override
	@SchemaMapping(field = "save")
	public Material save(@Argument(name = "request") MaterialNew request) {
		return extract(new Material(), this.materialService.save(request)).orElse(null);
	}
	
	@Override
	@SchemaMapping(field = "update")
	public Material update(@Argument(name = "id") String id, @Argument(name = "request") MaterialUpdate request) {
		return extract(new Material(), this.materialService.update(id, request)).orElse(null);
	}
	
	@Override
	public ResponseDelete<Material> deleteById(@Argument(name = "id") String id) {
		var res = this.materialService.deleteById(id);
		return extract(deleted(new Material()), res).orElse(notDeleted(getResponse(res).getErrors()));
	}
}
