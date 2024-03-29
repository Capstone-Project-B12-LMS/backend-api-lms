package com.capstoneprojectb12.lms.backendapilms.controllers.gql.user;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseQuery;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.ClassStatus;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.services.ClassService;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;
import com.capstoneprojectb12.lms.backendapilms.utilities.VerifyStatus;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.getResponse;
import static com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse.empty;

@Slf4j
@Controller
@SchemaMapping(typeName = "UserQuery")
@RequiredArgsConstructor
public class UserQuery implements BaseQuery<User> {
	private final UserRepository userRepository;
	private final UserService userService;
	private final ClassService classService;
	
	@Override
	@SchemaMapping(field = "findAll")
	public List<User> findAll() {
		return extract(new ArrayList<User>(), this.userService.findAll()).orElse(new ArrayList<>());
	}
	
	@Override
	@SchemaMapping(field = "findAllWithPageable")
	public PaginationResponse<List<User>> findAllWithPageable(int page, int size) {
		return extract(new PaginationResponse<List<User>>(), this.userService.findAll(page, size)).orElse(empty(new ArrayList<>(), 0, 0));
	}
	
	@Override
	public List<User> findAllDeleted(boolean showDeleted) {
		return null;
	}
	
	@Override
	@SchemaMapping(field = "findById")
	public User findById(@Argument(name = "id") String id) {
		return extract(new User(), getResponse(this.userService.findById(id))).orElse(null);
	}
	
	@SchemaMapping(field = "findByClassByUserId")
	public List<Class> findByClassByUserId(@Argument(name = "id") String userId, @Argument(name = "classStatus") ClassStatus classStatus) {
		return extract(new ArrayList<Class>(), this.classService.findByUserId(userId, classStatus.name())).orElse(null);
	}
	
	@Override
	@SchemaMapping(field = "findAllDeletedWithPageable")
	public PaginationResponse<List<User>> findAllDeletedWithPageable(int page, int size) {
		// TODO: implement me
		return null;
	}
	
	@SchemaMapping(field = "getVerifyStatusByUserID")
	public VerifyStatus getVerifyStatusByUserID(@Argument(name = "userId") String userId) {
		return (VerifyStatus) this.userService.getVerifyStatusByUserId(userId).getBody();
	}
}
