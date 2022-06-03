package com.capstoneprojectb12.lms.backendapilms.controllers.gql.role;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.role.RoleNew;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;
import com.capstoneprojectb12.lms.backendapilms.services.RoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SchemaMapping(typeName = "RoleMutation")
@RequiredArgsConstructor
public class RoleMutation {
    private final RoleService roleService;

    @SchemaMapping(field = "save")
    public Role save(@Argument(name = "request") RoleNew request) {
        var savedRole = this.roleService.save(request.toEntity());
        return savedRole.orElseThrow();
    }
}
