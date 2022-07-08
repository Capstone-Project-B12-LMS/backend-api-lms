package com.capstoneprojectb12.lms.backendapilms.controllers.rest.material;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.*;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.material.MaterialNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.material.MaterialUpdate;
import com.capstoneprojectb12.lms.backendapilms.services.MaterialService;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@PreAuthorize(value = "hasAnyAuthority('USER')")
@RequestMapping(value = { "/restapi/v1/material" })
@RequiredArgsConstructor
public class MaterialController {
	private final MaterialService materialService;

	// TODO: only teacher can create material
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid MaterialNew request, @Parameter(hidden = true) Errors errors) {
		log.info("entering endpoint to create new material");
		if (errors.hasErrors()) {
			return errorValidation(errors);
		}
		return this.materialService.save(request);
	}

	// TODO: add sort by created at
	@GetMapping(value = { "/class/{classId}" })
	public ResponseEntity<?> findAllByClassId(@PathVariable(required = true, name = "classId") String classId) {
		return this.materialService.findAllByClassId(classId);
	}

	@GetMapping(value = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable(name = "id", required = true) String id) {
		return this.materialService.findById(id);
	}

	@PutMapping(value = { "/{id}" })
	public ResponseEntity<?> update(@PathVariable(name = "id") String id, @RequestBody @Valid MaterialUpdate request,
			Errors errors) {
		if (errors.hasErrors()) {
			return errorValidation(errors);
		}
		return this.materialService.update(id, request);
	}
}
