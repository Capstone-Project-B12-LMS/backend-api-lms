package com.capstoneprojectb12.lms.backendapilms.controllers.gql.guidance;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseQuery;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Guidance;
import com.capstoneprojectb12.lms.backendapilms.services.GuidanceService;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;

@Slf4j
@RequiredArgsConstructor
@Controller
@SchemaMapping(typeName = "GuidanceQuery")
public class GuidanceQuery implements BaseQuery<Guidance> {
	private final GuidanceService guidanceService;
	
	@SchemaMapping(field = "findByClassId")
	public List<Guidance> findByClassId(@Argument(name = "classId") String classId) {
		return extract(new ArrayList<Guidance>(), this.guidanceService.findAllByClassId(classId)).orElse(new ArrayList<>());
	}
	
	@Override
	public List<Guidance> findAll() {
		return null;
	}
	
	@Override
	public PaginationResponse<List<Guidance>> findAllWithPageable(int page, int size) {
		return null;
	}
	
	@Override
	public List<Guidance> findAllDeleted() {
		return null;
	}
	
	@Override
	public PaginationResponse<List<Guidance>> findAllDeletedWithPageable(int page, int size) {
		return null;
	}
	
	@Override
	public Guidance findById(String id) {
		return null;
	}
}
