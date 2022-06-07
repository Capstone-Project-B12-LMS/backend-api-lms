package com.capstoneprojectb12.lms.backendapilms.controllers.gql.classes;

import java.util.List;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseQuery;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;

import lombok.RequiredArgsConstructor;

@Controller
@SchemaMapping(typeName = "ClassQuery")
@RequiredArgsConstructor
@CrossOrigin
public class ClassQuery implements BaseQuery<Class> {
    @Override
    public List<Class> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PaginationResponse<List<Class>> findAllWithPageable(int page, int size) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Class> findAllDeleted() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PaginationResponse<List<Class>> findAllDeletedWithPageable(int page, int size) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Class findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

}
