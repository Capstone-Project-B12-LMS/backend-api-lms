package com.capstoneprojectb12.lms.backendapilms.controllers.gql.feedback;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseQuery;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Feedback;
import com.capstoneprojectb12.lms.backendapilms.services.FeedbackService;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;

@Slf4j
@SchemaMapping(typeName = "FeedbackQuery")
@Controller
@RequiredArgsConstructor
public class FeedbackQuery implements BaseQuery<Feedback> {
	private final FeedbackService feedbackService;

	@Override
	public List<Feedback> findAll() {
		return null;
	}

	@Override
	public PaginationResponse<List<Feedback>> findAllWithPageable(int page, int size) {
		return null;
	}

	@Override
	public List<Feedback> findAllDeleted(boolean showDeleted) {
		return null;
	}


	@Override
	public PaginationResponse<List<Feedback>> findAllDeletedWithPageable(int page, int size) {
		return null;
	}

	@Override
	public Feedback findById(String id) {
		return null;
	}

	@SchemaMapping(field = "findByClassId")
	public List<Feedback> findByClassId(@Argument(name = "classId") String classId) {
		return extract(new ArrayList<Feedback>(), this.feedbackService.findAllByClassId(classId)).orElse(null);
	}
}
