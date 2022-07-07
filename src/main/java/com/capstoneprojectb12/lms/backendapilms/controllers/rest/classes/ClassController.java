package com.capstoneprojectb12.lms.backendapilms.controllers.rest.classes;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.*;
import com.capstoneprojectb12.lms.backendapilms.services.ClassService;
import com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = { "/restapi/v1/class" })
@RequiredArgsConstructor
@PreAuthorize(value = "hasAnyAuthority('USER')") // TODO: enable security
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
	public ResponseEntity<?> updateById(@PathVariable(name = "id", required = true) String classId,
			@RequestBody @Valid ClassUpdate request, @Parameter(hidden = true) Errors errors) {
		if (errors.hasErrors()) {
			return ApiResponse.errorValidation(errors);
		}
		return this.classService.update(classId, request);
	}

	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> deleteById(@PathVariable(name = "id") String classId) {
		return this.classService.deleteById(classId);
	}

	@GetMapping(value = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable(name = "id") String id) {
		return this.classService.findById(id);
	}

	@GetMapping
	public ResponseEntity<?> findAll() {
		return this.classService.findAll();
	}

	@GetMapping(value = { "/{page}/{size}" })
	public ResponseEntity<?> findAll(@PathVariable(required = true, name = "page") Integer page,
			@PathVariable(required = true, name = "size") Integer size) {
		return this.classService.findAll(page, size);
	}

	@PostMapping(value = { "/join" })
	public ResponseEntity<?> join(@RequestBody @Valid JoinClass request, Errors errors) {
		if (errors.hasErrors()) {
			return ApiResponse.errorValidation(errors);
		}
		return this.classService.joinUserToClass(request);
	}

	@DeleteMapping(value = { "/users/{classId}/{userId}" })
	public ResponseEntity<?> deleteUserById(@PathVariable(name = "classId") String classId,
			@PathVariable(name = "userId") String userId) {
		return this.classService.deleteUserById(classId, userId);
	}
}
