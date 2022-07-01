package com.capstoneprojectb12.lms.backendapilms.controllers.rest.guidance;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.guidance.GuidanceNew;
import com.capstoneprojectb12.lms.backendapilms.services.GuidanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.errorValidation;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = {"/restapi/v1/guidances"})
@RequiredArgsConstructor
public class GuidanceController {
	private final GuidanceService guidanceService;
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid GuidanceNew request, Errors errors) {
		if (errors.hasErrors()) {
			return errorValidation(errors);
		}
		return this.guidanceService.save(request);
	}
	
	@GetMapping(value = {"/class/{classId}"})
	public ResponseEntity<?> findAllByClassId(@PathVariable(required = true, name = "classId") String classId) {
		return this.guidanceService.findAllByClassId(classId);
	}
}
