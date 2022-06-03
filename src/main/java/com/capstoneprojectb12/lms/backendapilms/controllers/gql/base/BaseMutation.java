package com.capstoneprojectb12.lms.backendapilms.controllers.gql.base;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.role.RoleNew;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;

public interface BaseMutation<T> {
    public Role save(RoleNew request);

    public Role update(RoleNew request);

    public ResponseDelete deleteById(String id);
}
