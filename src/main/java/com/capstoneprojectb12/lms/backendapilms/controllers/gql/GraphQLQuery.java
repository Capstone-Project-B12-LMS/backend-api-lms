package com.capstoneprojectb12.lms.backendapilms.controllers.gql;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.hello.HelloQuery;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.role.RoleQuery;

import lombok.RequiredArgsConstructor;

@Controller
@SchemaMapping(typeName = "Query")
@RequiredArgsConstructor
public class GraphQLQuery {
    private final HelloQuery helloQuery;
    private final RoleQuery roleQuery;

    @SchemaMapping(field = "hello")
    public HelloQuery helloQuery() {
        return this.helloQuery;
    }

    @SchemaMapping(field = "role")
    public RoleQuery roleQuery() {
        return this.roleQuery;
    }
}
