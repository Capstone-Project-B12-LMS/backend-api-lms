package com.capstoneprojectb12.lms.backendapilms.controllers.gql.user;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseQuery;
import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@SchemaMapping(typeName = "UserQuery")
// @CrossOrigin(allowCredentials = "true")
@PreAuthorize(value = "hasAnyAuthority('USER')")
@RequiredArgsConstructor
public class UserQuery implements BaseQuery<User> {
	private final UserRepository userRepository;
	private final UserService userService;
	
	@Override
	@SchemaMapping(field = "findAll")
	public List<User> findAll() {
		return this.userRepository.findAll();
	}
	
	@Override
	@SchemaMapping(field = "findAllWithPageable")
	public PaginationResponse<List<User>> findAllWithPageable(int page, int size) {
		var userPage = this.userRepository.findAll(PageRequest.of(page, size));
		return this.userService.toPaginationResponse(userPage);
	}
	
	@Override
	@SchemaMapping(field = "findById")
	public User findById(@Argument(name = "id") String id) {
		var user = this.userRepository.findById(id);
		return user.orElse(null);
	}
	
	@Override
	@SchemaMapping(field = "findAllDeleted")
	public List<User> findAllDeleted() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@SchemaMapping(field = "findAllDeletedWithPageable")
	public PaginationResponse<List<User>> findAllDeletedWithPageable(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
