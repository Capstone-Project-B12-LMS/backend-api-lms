package com.capstoneprojectb12.lms.backendapilms.controllers.gql.role;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseQuery;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.RoleRepository;
import com.capstoneprojectb12.lms.backendapilms.services.RoleService;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@SchemaMapping(typeName = "RoleQuery")
@RequiredArgsConstructor
public class RoleQuery implements BaseQuery<Role> {
	private final RoleRepository roleRepository;
	private final RoleService roleService;
	
	@Override
	@SchemaMapping(field = "findAll")
	public List<Role> findAll() {
		return this.roleRepository.findAll();
	}
	
	@Override
	@SchemaMapping(field = "findAllWithPageable")
	public PaginationResponse<List<Role>> findAllWithPageable(
			@Argument(name = "page") int page,
			@Argument(name = "size") int size) {
		
		var pageRoles = this.roleRepository.findAll(PageRequest.of(page, size));
		return this.roleService.toPaginationResponse(pageRoles);
	}
	
	@Override
	public List<Role> findAllDeleted() {
		// TODO : implement me
		return null;
	}
	
	@Override
	public PaginationResponse<List<Role>> findAllDeletedWithPageable(int page, int size) {
		// TODO : implement me
		return null;
	}
	
	@Override
	public Role findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
