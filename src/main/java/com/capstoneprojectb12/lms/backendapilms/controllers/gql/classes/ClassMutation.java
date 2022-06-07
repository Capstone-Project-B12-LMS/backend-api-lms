package com.capstoneprojectb12.lms.backendapilms.controllers.gql.classes;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseMutation;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassNew;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.services.ClassService;

import lombok.RequiredArgsConstructor;

@Controller
@SchemaMapping(typeName = "ClassMutation")
@RequiredArgsConstructor
@CrossOrigin
// @PreAuthorize(value = "hasAnyAuthority('USER')")
public class ClassMutation implements BaseMutation<Class, ClassNew> {
    private final ClassService classService;

    @Override
    @SchemaMapping(field = "save")
    public Class save(@Argument(name = "request") ClassNew request) {
        var classRequest = this.classService.toEntity(request);
        try {
            var savedClass = this.classService.save(classRequest);
            return savedClass.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @SchemaMapping(field = "update")
    public Class update(String id, ClassNew request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SchemaMapping(field = "deleteById")
    public ResponseDelete deleteById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

}
