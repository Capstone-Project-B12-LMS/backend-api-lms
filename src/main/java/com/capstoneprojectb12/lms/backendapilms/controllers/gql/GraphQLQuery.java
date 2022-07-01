package com.capstoneprojectb12.lms.backendapilms.controllers.gql;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.classes.ClassQuery;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.guidance.GuidanceQuery;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.hello.HelloQuery;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.material.MaterialQuery;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.role.RoleQuery;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.user.UserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@SchemaMapping(typeName = "Query")
@RequiredArgsConstructor
// @CrossOrigin(allowCredentials = "true")
public class GraphQLQuery {
	private final HelloQuery helloQuery;
	private final RoleQuery roleQuery;
	private final UserQuery userQuery;
	private final ClassQuery classQuery;
	private final MaterialQuery materialQuery;
	private final GuidanceQuery guidanceQuery;
	
	@SchemaMapping(field = "hello")
	public HelloQuery helloQuery() {
		return this.helloQuery;
	}
	
	
	//	@PreAuthorize(value = "hasAnyAuthority('USER')") // TODO: enable security
	@SchemaMapping(field = "role")
	public RoleQuery roleQuery() {
		return this.roleQuery;
	}
	
	//	@PreAuthorize(value = "hasAnyAuthority('USER')") // TODO: enable security
	@SchemaMapping(field = "user")
	public UserQuery userQuery() {
		return this.userQuery;
	}
	
	//	@PreAuthorize(value = "hasAnyAuthority('USER')") // TODO: enable security
	@SchemaMapping(value = "class")
	public ClassQuery classQuery() {
		return this.classQuery;
	}
	
	@SchemaMapping(field = "material")
	public MaterialQuery materialQuery() {
		return this.materialQuery;
	}
	
	@SchemaMapping(field = "guidance")
	public GuidanceQuery guidanceQuery() {
		return this.guidanceQuery;
	}
}
