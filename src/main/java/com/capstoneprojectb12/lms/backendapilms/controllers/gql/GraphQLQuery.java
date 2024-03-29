package com.capstoneprojectb12.lms.backendapilms.controllers.gql;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.activityhistory.ActivityHistoryQuery;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.classes.ClassQuery;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.feedback.FeedbackQuery;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.guidance.GuidanceQuery;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.hello.HelloQuery;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.material.MaterialQuery;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.role.RoleQuery;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.user.UserQuery;

import lombok.RequiredArgsConstructor;

@Controller
@SchemaMapping(typeName = "Query")
@PreAuthorize(value = "hasAnyAuthority('USER')")
@RequiredArgsConstructor
public class GraphQLQuery {
	private final HelloQuery helloQuery;
	private final RoleQuery roleQuery;
	private final UserQuery userQuery;
	private final ClassQuery classQuery;
	private final MaterialQuery materialQuery;
	private final GuidanceQuery guidanceQuery;
	private final FeedbackQuery feedbackQuery;
	private final ActivityHistoryQuery activityHistoryQuery;

	@SchemaMapping(field = "hello")
	public HelloQuery helloQuery() {
		return this.helloQuery;
	}

	@SchemaMapping(field = "role")
	public RoleQuery roleQuery() {
		return this.roleQuery;
	}

	@SchemaMapping(field = "user")
	public UserQuery userQuery() {
		return this.userQuery;
	}

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

	@SchemaMapping(field = "feedback")
	public FeedbackQuery feedbackQuery() {
		return this.feedbackQuery;
	}

	@SchemaMapping(field = "activityHistory")
	public ActivityHistoryQuery activityHistoryQuery() {
		return this.activityHistoryQuery;
	}
}
