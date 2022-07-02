package com.capstoneprojectb12.lms.backendapilms.controllers.gql.feedback;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseMutation;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.feedback.FeedbackNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.feedback.FeedbackUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Feedback;
import com.capstoneprojectb12.lms.backendapilms.services.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;

@Slf4j
@Controller
@SchemaMapping(typeName = "FeedbackMutation")
@RequiredArgsConstructor
public class FeedbackMutation implements BaseMutation<Feedback, FeedbackNew, FeedbackUpdate> {
	private final FeedbackService feedbackService;
	
	@Override
	@SchemaMapping(field = "save")
	public Feedback save(@Argument(name = "request") FeedbackNew request) {
		return extract(new Feedback(), this.feedbackService.save(request)).orElse(null);
	}
	
	@Override
	public Feedback update(String id, FeedbackUpdate request) {
		return null;
	}
	
	@Override
	public ResponseDelete<Feedback> deleteById(String id) {
		return null;
	}
}
