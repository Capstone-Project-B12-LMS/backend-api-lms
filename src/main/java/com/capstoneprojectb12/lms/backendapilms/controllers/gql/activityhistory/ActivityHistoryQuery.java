package com.capstoneprojectb12.lms.backendapilms.controllers.gql.activityhistory;

import com.capstoneprojectb12.lms.backendapilms.models.entities.mongodb.ActivityHistory;
import com.capstoneprojectb12.lms.backendapilms.services.mongodb.ActivityHistoryService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;

@Controller
@Slf4j
@RequiredArgsConstructor
@SchemaMapping(typeName = "ActivityHistoryQuery")
public class ActivityHistoryQuery {
	private final ActivityHistoryService activityHistoryService;
	
	@SchemaMapping(field = "findByUserEmail")
	public List<ActivityHistory> findByUserEmail(@Argument(name = "email") String email) {
		return extract(new ArrayList<ActivityHistory>(), this.activityHistoryService.findByUserEmail(email)).orElse(new ArrayList<>());
	}
}
