package com.capstoneprojectb12.lms.backendapilms.controllers.rest.feedback;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.feedback.FeedbackNew;
import com.capstoneprojectb12.lms.backendapilms.services.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.errorValidation;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = {"/restapi/v1/feedbacks"})
public class FeedbackController {
	private final FeedbackService feedbackService;
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid FeedbackNew request, Errors errors) {
		if (errors.hasErrors()) {
			return errorValidation(errors);
		}
		return this.feedbackService.save(request);
	}
	
	@GetMapping(value = {"/class/{classId}"})
	public ResponseEntity<?> findAllByClassId(@PathVariable(name = "classId", required = true) String classId) {
		return this.feedbackService.findAllByClassId(classId);
	}
}
