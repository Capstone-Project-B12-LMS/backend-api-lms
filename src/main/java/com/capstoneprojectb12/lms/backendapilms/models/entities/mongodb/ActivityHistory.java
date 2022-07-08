package com.capstoneprojectb12.lms.backendapilms.models.entities.mongodb;

import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(value = "activity_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ActivityHistory {
	@MongoId
	private String id;
	private User user;
	private String content;
}
