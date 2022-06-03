package com.capstoneprojectb12.lms.backendapilms.controllers.gql.role;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;
import com.capstoneprojectb12.lms.backendapilms.services.RoleService;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;

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

    @SchemaMapping(field = "findAllWithPageable")
    public PaginationResponse<List<Role>> findAll(
            @Argument(name = "page") int page,
            @Argument(name = "size") int size) {

        var pageRoles = this.roleService.findAll(page, size);
        var response = this.roleService.toPaginationResponse(pageRoles);
        return response;
    }
}
