package com.capstoneprojectb12.lms.backendapilms.controllers.gql.classes;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseMutation;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassNew;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;

import lombok.RequiredArgsConstructor;

@Controller
@SchemaMapping(typeName = "ClassMutation")
@RequiredArgsConstructor
@CrossOrigin
public class ClassMutation implements BaseMutation<Class, ClassNew> {

    @Override
    public Class save(ClassNew request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Class update(String id, ClassNew request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseDelete deleteById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

}
