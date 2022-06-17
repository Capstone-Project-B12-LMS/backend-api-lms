package com.capstoneprojectb12.lms.backendapilms.controllers.gql;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.classes.ClassMutation;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.hello.HelloMutation;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.material.MaterialMutation;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.role.RoleMutation;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.user.UserMutation;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@SchemaMapping(typeName = "Mutation")
// @CrossOrigin(allowCredentials = "true")
@RequiredArgsConstructor
public class GraphQLMutation {
	private final HelloMutation helloMutation;
	private final RoleMutation roleMutation;
	private final UserMutation userMutation;
	private final ClassMutation classMutation;
	private final MaterialMutation materialMutation;
	
	@SchemaMapping(field = "hello")
	public HelloMutation helloMutation() {
		return this.helloMutation;
	}
	
	//	@PreAuthorize(value = "hasAnyAuthority('USER')") // TODO: enable security
	@SchemaMapping(field = "role")
	public RoleMutation roleMutation() {
		return this.roleMutation;
	}
	
	//	@PreAuthorize(value = "hasAnyAuthority('USER')") // TODO: enable security
	@SchemaMapping(field = "user")
	public UserMutation userMutation() {
		return this.userMutation;
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER')") // TODO: enable security
	@SchemaMapping(field = "class")
	public ClassMutation classMutation() {
		return this.classMutation;
	}
	
	//	@PreAuthorize(value = "hasAnyAuthority('USER')") // TODO: enable security
	@SchemaMapping(field = "material")
	public MaterialMutation materialMutation() {
		return this.materialMutation;
	}
}
