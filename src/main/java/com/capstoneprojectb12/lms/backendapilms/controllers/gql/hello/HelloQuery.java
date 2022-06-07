package com.capstoneprojectb12.lms.backendapilms.controllers.gql.hello;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.hello.SayHelloResponse;

@Controller
@CrossOrigin
@SchemaMapping(typeName = "HelloQuery")
public class HelloQuery {
    @SchemaMapping(field = "sayHello")
    @PreAuthorize(value = "hasAnyAuthority('USER')")
    public SayHelloResponse sayHello() {
        var response = SayHelloResponse.builder()
                .message("Hi, my name is KELOMPOK B 12")
                .build();
        return response;
    }
}
