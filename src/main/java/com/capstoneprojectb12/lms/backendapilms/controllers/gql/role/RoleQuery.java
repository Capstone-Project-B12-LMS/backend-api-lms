package com.capstoneprojectb12.lms.backendapilms.controllers.gql.role;

import java.util.List;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;
import com.capstoneprojectb12.lms.backendapilms.services.RoleService;

import lombok.RequiredArgsConstructor;

@Controller
@SchemaMapping(typeName = "RoleQuery")
@RequiredArgsConstructor
public class RoleQuery {
    private final RoleService roleService;

    @SchemaMapping(field = "findAll")
    public List<Role> findAll() {
        return this.roleService.findAll();
    }
}
