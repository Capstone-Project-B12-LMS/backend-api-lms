package com.capstoneprojectb12.lms.backendapilms.controllers.gql.hello;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@SchemaMapping(typeName = "HelloMutation")
public class HelloMutation {
    @SchemaMapping(field = "sayHello")
    public String sayHello(@Argument(name = "to") String to) {
        return "Hello " + to + ", my name is KELOMPOK B 12";
    }
}
