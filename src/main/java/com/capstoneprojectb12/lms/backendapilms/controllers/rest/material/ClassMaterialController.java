package com.capstoneprojectb12.lms.backendapilms.controllers.rest.material;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.material.MaterialNew;
import com.capstoneprojectb12.lms.backendapilms.services.MaterialService;
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
//@PreAuthorize(value = "hasAnyAuthority('USER')") // TODO: enable security
@RequestMapping(value = {"/restapi/v1/material"})
@RequiredArgsConstructor
public class ClassMaterialController {
	private final MaterialService materialService;
	
	//	TODO: only teacher can create material
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid MaterialNew request, @Parameter(hidden = true) Errors errors) {
		log.info("entering endpoint to create new material");
		if (errors.hasErrors()) {
			return ApiResponse.errorValidation(errors);
		}
		return this.materialService.save(request);
	}
	
	//	TODO: add sort by created at
	@GetMapping(value = {"/class/{classId}"})
	public ResponseEntity<?> findAllByClassId(@PathVariable(required = true, name = "classId") String classId) {
		return this.materialService.findAllByClassId(classId);
	}
	
	@GetMapping(value = {"/{id}"})
	public ResponseEntity<?> findById(@PathVariable(name = "id", required = true) String id) {
		return this.materialService.findById(id);
	}
}
