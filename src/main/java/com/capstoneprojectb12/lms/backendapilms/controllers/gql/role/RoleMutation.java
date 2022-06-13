package com.capstoneprojectb12.lms.backendapilms.controllers.gql.role;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseMutation;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.role.RoleNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.role.RoleUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;
import com.capstoneprojectb12.lms.backendapilms.services.RoleService;
import com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@SchemaMapping(typeName = "RoleMutation")
@RequiredArgsConstructor
public class RoleMutation implements BaseMutation<Role, RoleNew, RoleUpdate> {
	private final RoleService roleService;
	
	@SchemaMapping(field = "save")
	public Role save(@Argument(name = "request") RoleNew request) {
		log.info("Entering method to save new role");
		var response = this.roleService.save(request);
		var apiRes = (ApiResponse<?>) Objects.requireNonNull(response.getBody());
		var role = (Role) apiRes.getData();
		return role;
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
