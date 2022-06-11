package com.capstoneprojectb12.lms.backendapilms.controllers.rest.classes.material;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.material.MaterialNew;
import com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = {"/restapi/v1/class/material"})
public class ClassMaterialController {
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid MaterialNew request, Errors errors) {
		log.info("entering endpoint to create new material");
		if (errors.hasErrors()) {
			return ApiResponse.errorValidation(errors);
		}
		
		try {
		
		} catch (Exception e) {
		
		}
		
		return null;
	}
}
