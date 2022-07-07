package com.capstoneprojectb12.lms.backendapilms.controllers.rest.activityhistory;

import com.capstoneprojectb12.lms.backendapilms.services.mongodb.ActivityHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = {"/restapi/v1/activityhistory"})
public class ActivityHistoryController {
	private final ActivityHistoryService activityHistoryService;
	
	@GetMapping(value = {"/user/{userId}"})
	public ResponseEntity<?> findByUserId(@PathVariable(name = "userId") String userId) {
		return this.activityHistoryService.findByUserId(userId);
	}
}
