package com.capstoneprojectb12.lms.backendapilms.controllers.rest.activityhistory;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.capstoneprojectb12.lms.backendapilms.services.mongodb.ActivityHistoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@PreAuthorize(value = "hasAnyAuthority('USER')")
@RequiredArgsConstructor
@RequestMapping(value = { "/restapi/v1/activityhistory" })
public class ActivityHistoryController {
	private final ActivityHistoryService activityHistoryService;

	@GetMapping(value = { "/user/{userId}" })
	public ResponseEntity<?> findByUserId(@PathVariable(name = "userId") String userId) {
		return this.activityHistoryService.findByUserId(userId);
	}
}
