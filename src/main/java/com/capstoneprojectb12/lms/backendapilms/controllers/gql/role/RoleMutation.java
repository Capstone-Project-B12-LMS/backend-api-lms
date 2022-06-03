package com.capstoneprojectb12.lms.backendapilms.controllers.gql.role;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseMutation;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.role.RoleNew;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;
import com.capstoneprojectb12.lms.backendapilms.services.RoleService;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.DataAlreadyExistsException;

import lombok.RequiredArgsConstructor;

@Controller
@SchemaMapping(typeName = "RoleMutation")
@RequiredArgsConstructor
public class RoleMutation implements BaseMutation<Role> {
    private final RoleService roleService;

    @SchemaMapping(field = "save")
    public Role save(@Argument(name = "request") RoleNew request) {
        var savedRole = this.roleService.save(request.toEntity());
        if (!savedRole.isPresent()) {
            throw new DataAlreadyExistsException();
        }
        return savedRole.get();
    }

    @Override
    public Role update(RoleNew request) {
        // TODO: implement me
        return null;
    }

    @Override
    public ResponseDelete deleteById(String id) {
        // TODO: implement me
        return null;
    }
}
