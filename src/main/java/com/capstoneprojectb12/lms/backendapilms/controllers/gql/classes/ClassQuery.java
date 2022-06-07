package com.capstoneprojectb12.lms.backendapilms.controllers.gql.classes;

import java.util.List;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize(value = "hasAnyAuthority('USER')")
public class ClassQuery implements BaseQuery<Class> {
    @Override
    @SchemaMapping(field = "findAll")
    public List<Class> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SchemaMapping(field = "findAllWithPageable")
    public PaginationResponse<List<Class>> findAllWithPageable(int page, int size) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SchemaMapping(field = "findAllDeleted")
    public List<Class> findAllDeleted() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SchemaMapping(field = "findAllDeletedWithPageable")
    public PaginationResponse<List<Class>> findAllDeletedWithPageable(int page, int size) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SchemaMapping(field = "findById")
    public Class findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

}
