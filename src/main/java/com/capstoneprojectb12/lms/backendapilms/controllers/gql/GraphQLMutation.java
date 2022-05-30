package com.capstoneprojectb12.lms.backendapilms.controllers.gql;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.hello.HelloMutation;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@SchemaMapping(typeName = "Mutation")
public class GraphQLMutation {
    @SchemaMapping(field = "hello")
    public HelloMutation helloMutation() {
        return new HelloMutation();
    }
}
