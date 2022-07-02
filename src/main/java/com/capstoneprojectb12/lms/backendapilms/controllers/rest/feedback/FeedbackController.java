package com.capstoneprojectb12.lms.backendapilms.controllers.rest.feedback;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.feedback.FeedbackNew;
import com.capstoneprojectb12.lms.backendapilms.services.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.errorValidation;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FeedbackController {
	private final FeedbackService feedbackService;
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid FeedbackNew request, Errors errors) {
		if (errors.hasErrors()) {
			return errorValidation(errors);
		}
		return this.feedbackService.save(request);
	}
}
