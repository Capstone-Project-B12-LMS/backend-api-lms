package com.capstoneprojectb12.lms.backendapilms.controllers.gql;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.classes.ClassQuery;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.hello.HelloQuery;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.role.RoleQuery;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.user.UserQuery;

import lombok.RequiredArgsConstructor;

@Controller
@SchemaMapping(typeName = "Query")
@RequiredArgsConstructor
@CrossOrigin
public class GraphQLQuery {
    private final HelloQuery helloQuery;
    private final RoleQuery roleQuery;
    private final UserQuery userQuery;
    private final ClassQuery classQuery;

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
}
