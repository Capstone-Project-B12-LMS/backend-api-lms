package com.capstoneprojectb12.lms.backendapilms.controllers.gql.role;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseQuery;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;
import com.capstoneprojectb12.lms.backendapilms.services.RoleService;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;

import lombok.RequiredArgsConstructor;

@Controller
@SchemaMapping(typeName = "RoleQuery")
@RequiredArgsConstructor
public class RoleQuery implements BaseQuery<Role> {
    private final RoleService roleService;

    @Override
    @SchemaMapping(field = "findAll")
    public List<Role> findAll() {
        return this.roleService.findAll();
    }

    @Override
    @SchemaMapping(field = "findAllWithPageable")
    public PaginationResponse<List<Role>> findAllWithPageable(
            @Argument(name = "page") int page,
            @Argument(name = "size") int size) {

        var pageRoles = this.roleService.findAll(page, size);
        var response = this.roleService.toPaginationResponse(pageRoles);
        return response;
    }

    @Override
    public List<Role> findAllDeleted() {
        // TODO : implement me
        return null;
    }

    @Override
    public PaginationResponse<List<Role>> findAllDeletedWithPageable(int page, int size) {
        // TODO : implement me
        return null;
    }

    @Override
    public Role findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

}
