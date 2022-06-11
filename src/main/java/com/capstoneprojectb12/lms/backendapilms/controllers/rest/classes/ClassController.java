package com.capstoneprojectb12.lms.backendapilms.controllers.rest.classes;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassUpdate;
import com.capstoneprojectb12.lms.backendapilms.services.ClassService;
import com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = {"/restapi/v1/class"})
@RequiredArgsConstructor
@PreAuthorize(value = "hasAnyAuthority('USER')")
public class ClassController {
	private final ClassService classService;
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid ClassNew request, Errors errors) {
		if (errors.hasErrors()) {
			return ApiResponse.errorValidation(errors);
		}
		
		try {
			var classEntity = this.classService.toEntity(request);
			var savedClass = this.classService.save(classEntity);
			return ApiResponse.responseOk(savedClass.get());
		} catch (Exception e) {
			log.error("error while save new class", e);
			return ApiResponse.responseError(e);
		}
		
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateById(
			@PathVariable(name = "id", required = true) String classId,
			@RequestBody @Valid ClassUpdate request, Errors errors) {
		
		if (errors.hasErrors()) {
			return ApiResponse.errorValidation(errors);
		}
		
		try {
			var classEntity = this.classService.findById(classId);
			if (! classEntity.isPresent()) {
				return ApiResponse.responseBad("value not present");
			}
			
			classEntity.get().setName(request.getName());
			classEntity.get().setRoom(request.getRoom());
			classEntity.get().setStatus(request.getStatus());
			
			classEntity = this.classService.update(classEntity.get());
			return ApiResponse.responseOk(classEntity.get());
		} catch (NoSuchElementException e) {
			log.error("class not found", e);
			return ApiResponse.responseBad(e.getMessage());
		} catch (Exception e) {
			log.error("error while update user", e);
			return ApiResponse.responseError(e);
		}
		
	}
	
	@DeleteMapping(value = {"/{id}"})
	public ResponseEntity<?> deleteById(@PathVariable(name = "id") String classId) {
		
		try {
			var isDeleted = this.classService.deleteById(classId);
			if (isDeleted) {
				
				return ApiResponse.responseOk(
						ResponseDelete.builder()
								.error(null)
								.status(isDeleted)
								.build());
			} else {
				return ApiResponse.responseOk(
						ResponseDelete.builder()
								.error("failed to delete data maybe data doesn't exists")
								.status(isDeleted)
								.build());
			}
		} catch (Exception e) {
			log.error("error while delete class by id");
			return ApiResponse.responseError(e);
		}
	}
	
	@GetMapping(value = {"/{id}"})
	public ResponseEntity<?> finfById(@PathVariable(name = "id") String id) {
		try {
			var classEntity = this.classService.findById(id);
			if (! classEntity.isPresent()) {
				return ApiResponse.responseBad("value not present");
			}
			return ApiResponse.responseOk(classEntity.get());
		} catch (Exception e) {
			return ApiResponse.responseError(e);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> findAll() {
		try {
			return ApiResponse.responseOk(this.classService.findAll());
		} catch (Exception e) {
			return ApiResponse.responseError(e);
		}
	}
}
