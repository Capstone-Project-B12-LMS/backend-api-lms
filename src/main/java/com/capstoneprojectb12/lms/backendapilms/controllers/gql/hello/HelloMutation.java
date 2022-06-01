package com.capstoneprojectb12.lms.backendapilms.controllers.gql.hello;

import javax.validation.Valid;

import com.capstoneprojectb12.lms.backendapilms.entities.responses.SayHelloInput;
import com.capstoneprojectb12.lms.backendapilms.entities.responses.SayHelloResponse;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@SchemaMapping(typeName = "HelloMutation")
public class HelloMutation {
    @SchemaMapping(field = "sayHello")
    public SayHelloResponse sayHello(@Argument(name = "to") @Valid SayHelloInput to) {
        var response = SayHelloResponse.builder()
                .message("Hello " + to.getName().toUpperCase() + " how are you?")
                .errors(null)
                .build();
        return response;
    }
}
