package com.capstoneprojectb12.lms.backendapilms.controllers.gql.hello;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.responses.SayHelloResponse;

@Controller
@SchemaMapping(typeName = "HelloQuery")
public class HelloQuery {
    @SchemaMapping(field = "sayHello")
    public SayHelloResponse sayHello() {
        var response = SayHelloResponse.builder()
                .message("Hi, my name is KELOMPOK B 12")
                .build();
        return response;
    }
}
