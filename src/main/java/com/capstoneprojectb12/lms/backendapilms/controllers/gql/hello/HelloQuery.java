package com.capstoneprojectb12.lms.backendapilms.controllers.gql.hello;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.hello.SayHelloResponse;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
// @CrossOrigin(allowCredentials = "true")
@SchemaMapping(typeName = "HelloQuery")
public class HelloQuery {
	@SchemaMapping(field = "sayHello")
	@PreAuthorize(value = "hasAnyAuthority('USER')")
	public SayHelloResponse sayHello() {
		return SayHelloResponse.builder()
				.message("Hi, my name is KELOMPOK B 12")
				.build();
	}
}
