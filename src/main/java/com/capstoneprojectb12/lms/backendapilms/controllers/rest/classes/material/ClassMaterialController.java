package com.capstoneprojectb12.lms.backendapilms.controllers.rest.classes.material;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = {"/restapi/class/material"})
public class ClassMaterialController {
	@PostMapping
	public ResponseEntity<?> save() {
		log.info("entering endpoint to create new material");
		return null;
	}
}
