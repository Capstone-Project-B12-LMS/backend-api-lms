package com.capstoneprojectb12.lms.backendapilms.controllers.rest.feedback;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.*;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.feedback.FeedbackNew;
import com.capstoneprojectb12.lms.backendapilms.services.FeedbackService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@PreAuthorize(value = "hasAnyAuthority('USER')")
@RestController
@RequestMapping(value = { "/restapi/v1/feedbacks" })
public class FeedbackController {
	private final FeedbackService feedbackService;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid FeedbackNew request, Errors errors) {
		if (errors.hasErrors()) {
			return errorValidation(errors);
		}
		return this.feedbackService.save(request);
	}

	@GetMapping(value = { "/class/{classId}" })
	public ResponseEntity<?> findAllByClassId(@PathVariable(name = "classId", required = true) String classId) {
		return this.feedbackService.findAllByClassId(classId);
	}
}
