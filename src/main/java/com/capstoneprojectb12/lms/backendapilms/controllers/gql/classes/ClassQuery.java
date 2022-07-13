package com.capstoneprojectb12.lms.backendapilms.controllers.gql.classes;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseQuery;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassResponse;
import com.capstoneprojectb12.lms.backendapilms.services.ClassService;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;
import static com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse.empty;

@Slf4j
@Controller
@SchemaMapping(typeName = "ClassQuery")
@RequiredArgsConstructor
public class ClassQuery implements BaseQuery<ClassResponse> {
    private final ClassService classService;

    @Override
    @SchemaMapping(field = "findAll")
    public List<ClassResponse> findAll() {
        return extract(new ArrayList<ClassResponse>(), this.classService.findAll()).orElse(new ArrayList<>());
    }

    @Override
    @SchemaMapping(field = "findAllWithPageable")
    public PaginationResponse<List<ClassResponse>> findAllWithPageable(@Argument(name = "page") int page, @Argument(name = "size") int size) {
        return extract(new PaginationResponse<List<ClassResponse>>(), this.classService.findAll(page, size)).orElse(empty(new ArrayList<>(), 0, 0));
    }

    @Override
    @SchemaMapping(field = "findAllDeleted")
    public List<ClassResponse> findAllDeleted(@Argument(name = "showDeleted") boolean showDeleted) {
        return extract(new ArrayList<ClassResponse>(), this.classService.findAll(showDeleted)).orElse(new ArrayList<>());
    }

    @Override
    @SchemaMapping(field = "findAllDeletedWithPageable")
    public PaginationResponse<List<ClassResponse>> findAllDeletedWithPageable(int page, int size) {
        // TODO: implement find all deleted class with pageable
        return null;
    }

    @Override
    @SchemaMapping(field = "findById")
    public ClassResponse findById(@Argument(name = "id") String id) {
        return extract(new ClassResponse(), this.classService.findById(id)).orElse(null);
    }

}
