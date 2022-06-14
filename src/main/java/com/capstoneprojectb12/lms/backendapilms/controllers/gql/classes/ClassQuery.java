package com.capstoneprojectb12.lms.backendapilms.controllers.gql.classes;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseQuery;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.services.ClassService;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;
import static com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse.empty;

@Slf4j
@Controller
@SchemaMapping(typeName = "ClassQuery")
@RequiredArgsConstructor
public class ClassQuery implements BaseQuery<Class> {
	private final ClassService classService;
	
	@Override
	@SchemaMapping(field = "findAll")
	public List<Class> findAll() {
		return extract(new ArrayList<Class>(), this.classService.findAll()).orElse(new ArrayList<>());
	}
	
	@Override
	@SchemaMapping(field = "findAllWithPageable")
	public PaginationResponse<List<Class>> findAllWithPageable(@Argument(name = "page") int page, @Argument(name = "size") int size) {
		return extract(new PaginationResponse<List<Class>>(), this.classService.findAll(page, size)).orElse(empty(new ArrayList<>(), 0, 0));
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
		return extract(new Class(), this.classService.findById(id)).orElse(null);
	}
	
}
