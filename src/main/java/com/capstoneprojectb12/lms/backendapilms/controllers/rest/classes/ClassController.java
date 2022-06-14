package com.capstoneprojectb12.lms.backendapilms.controllers.rest.classes;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassUpdate;
import com.capstoneprojectb12.lms.backendapilms.services.ClassService;
import com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = {"/restapi/v1/class"})
@RequiredArgsConstructor
//@PreAuthorize(value = "hasAnyAuthority('USER')") // TODO: enable security
public class ClassController {
	private final ClassService classService;
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid ClassNew request, Errors errors) {
		if (errors.hasErrors()) {
			return ApiResponse.errorValidation(errors);
		}
		return classService.save(request);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateById(@PathVariable(name = "id", required = true) String classId, @RequestBody @Valid ClassUpdate request, @Parameter(hidden = true) Errors errors) {
		if (errors.hasErrors()) {
			return ApiResponse.errorValidation(errors);
		}
		return this.classService.update(classId, request);
	}
	
	@DeleteMapping(value = {"/{id}"})
	public ResponseEntity<?> deleteById(@PathVariable(name = "id") String classId) {
		return this.classService.deleteById(classId);
	}
	
	@GetMapping(value = {"/{id}"})
	public ResponseEntity<?> finfById(@PathVariable(name = "id") String id) {
		return this.classService.findById(id);
	}
	
	@GetMapping
	public ResponseEntity<?> findAll() {
		return this.classService.findAll();
	}
}
