package com.capstoneprojectb12.lms.backendapilms.controllers.gql;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.classes.ClassMutation;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.feedback.FeedbackMutation;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.guidance.GuidanceMutation;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.hello.HelloMutation;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.material.MaterialMutation;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.role.RoleMutation;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.user.UserMutation;

import lombok.RequiredArgsConstructor;

@Controller
@SchemaMapping(typeName = "Mutation")
@RequiredArgsConstructor
public class GraphQLMutation {
	private final HelloMutation helloMutation;
	private final RoleMutation roleMutation;
	private final UserMutation userMutation;
	private final ClassMutation classMutation;
	private final MaterialMutation materialMutation;
	private final GuidanceMutation guidanceMutation;
	private final FeedbackMutation feedbackMutation;

	@SchemaMapping(field = "hello")
	public HelloMutation helloMutation() {
		return this.helloMutation;
	}

	@PreAuthorize(value = "hasAnyAuthority('USER')")
	@SchemaMapping(field = "role")
	public RoleMutation roleMutation() {
		return this.roleMutation;
	}

	@SchemaMapping(field = "user")
	public UserMutation userMutation() {
		return this.userMutation;
	}

	@PreAuthorize(value = "hasAnyAuthority('USER')") 
	@SchemaMapping(field = "class")
	public ClassMutation classMutation() {
		return this.classMutation;
	}

	@PreAuthorize(value = "hasAnyAuthority('USER')")
	@SchemaMapping(field = "material")
	public MaterialMutation materialMutation() {
		return this.materialMutation;
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER')")
	@SchemaMapping(field = "guidance")
	public GuidanceMutation guidanceMutation() {
		return this.guidanceMutation;
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER')")
	@SchemaMapping(field = "feedback")
	public FeedbackMutation feedbackMutation() {
		return this.feedbackMutation;
	}
}
