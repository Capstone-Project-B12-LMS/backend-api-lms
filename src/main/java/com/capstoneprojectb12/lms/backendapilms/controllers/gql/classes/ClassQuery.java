package com.capstoneprojectb12.lms.backendapilms.controllers.gql.classes;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseQuery;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.services.ClassService;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SchemaMapping(typeName = "ClassQuery")
@RequiredArgsConstructor
@CrossOrigin
// @PreAuthorize(value = "hasAnyAuthority('USER')")
public class ClassQuery implements BaseQuery<Class> {
    private final ClassService classService;

    @Override
    @SchemaMapping(field = "findAll")
    public List<Class> findAll() {
        try {
            return this.classService.findAll();
        } catch (Exception e) {
            log.error("error while find all class", e);
            return null;
        }
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
    public Class findById(@Argument(name = "id") String id) {
        try {
            var className = this.classService.findById(id);
            return className.orElse(null);
        } catch (Exception e) {
            log.error("error while find class by ud");
            return null;
        }
    }

}
