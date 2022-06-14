package com.capstoneprojectb12.lms.backendapilms.controllers.gql.hello;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.hello.SayHelloInput;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.hello.SayHelloResponse;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@SchemaMapping(typeName = "HelloMutation")
public class HelloMutation {
	@SchemaMapping(field = "sayHello")
	public SayHelloResponse sayHello(@Argument(name = "to") SayHelloInput to) {
		return SayHelloResponse.builder()
				.message("Hello " + to.getName().toUpperCase() + " how are you?")
				.errors(null)
				.build();
	}
}
