package com.capstoneprojectb12.lms.backendapilms.controllers.gql.role;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseMutation;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.role.RoleNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.role.RoleUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;
import com.capstoneprojectb12.lms.backendapilms.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.getResponse;

@Slf4j
@Controller
@SchemaMapping(typeName = "RoleMutation")
@RequiredArgsConstructor
public class RoleMutation implements BaseMutation<Role, RoleNew, RoleUpdate> {
	private final RoleService roleService;
	
	@SchemaMapping(field = "save")
	public Role save(@Argument(name = "request") RoleNew request) {
		log.info("Entering method to save new role");
		return extract(new Role(), getResponse(this.roleService.save(request))).orElse(null);
	}
	
	@Override
	public Role update(String id, RoleUpdate request) {
//		TODO: implement update role
		return null;
	}
	
	@Override
	public ResponseDelete deleteById(String id) {
		// TODO: implement delete role by id
		return null;
	}
}
