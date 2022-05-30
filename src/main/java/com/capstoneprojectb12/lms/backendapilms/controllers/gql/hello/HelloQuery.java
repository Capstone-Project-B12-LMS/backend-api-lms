package com.capstoneprojectb12.lms.backendapilms.controllers.gql.hello;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@SchemaMapping(typeName = "HelloQuery")
public class HelloQuery {
    @SchemaMapping(field = "sayHello")
    public String s() {
        return "Hi, my name is KELOMPOK B 12";
    }
}
