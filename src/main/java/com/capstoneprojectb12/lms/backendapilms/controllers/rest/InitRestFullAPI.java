package com.capstoneprojectb12.lms.backendapilms.controllers.rest;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.hello.SayHelloInput;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.hello.SayHelloResponse;
import com.capstoneprojectb12.lms.backendapilms.utilities.ApiValidation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin
@RequestMapping(value = {"/restapi/v1"})
public class InitRestFullAPI {
	
	@GetMapping
	@ApiResponses(value = {
			@ApiResponse(description = "This endpoint currently can be accessed by role USER", responseCode = "200")
	})
	@PreAuthorize(value = "hasAnyAuthority('USER')")
	public ResponseEntity<?> init() {
		var response = SayHelloResponse.builder()
				.message("Hi, my name is KELOMPOK B 12")
				.build();
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	@ApiResponses(
			@ApiResponse(description = "All user can access this enpoint", responseCode = "200")
	)
	public ResponseEntity<?> init(@RequestBody @Valid SayHelloInput to, @Parameter(hidden = true) Errors errors) {
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(ApiValidation.getErrorMessages(errors));
		}
		
		var response = SayHelloResponse
				.builder()
				.message("Hello " + to.getName().toUpperCase() + " how are you?")
				.build();
		return ResponseEntity.ok(response);
	}
}