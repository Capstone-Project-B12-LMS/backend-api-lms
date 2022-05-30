package com.capstoneprojectb12.lms.backendapilms.controllers.gql;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.hello.HelloQuery;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@SchemaMapping(typeName = "Query")
public class GraphQLQuery {
    @SchemaMapping(field = "hello")
    public HelloQuery helloQuery() {
        return new HelloQuery();
    }
}
