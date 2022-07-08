package com.capstoneprojectb12.lms.backendapilms.models.entities.mongodb;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.capstoneprojectb12.lms.backendapilms.models.entities.User;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Document(value = "activity_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ActivityHistory {
	@MongoId
	private String id;
	private String userEmail;
	private String content;
}
