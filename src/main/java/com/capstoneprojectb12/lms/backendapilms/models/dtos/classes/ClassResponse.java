package com.capstoneprojectb12.lms.backendapilms.models.dtos.classes;

import com.capstoneprojectb12.lms.backendapilms.models.entities.BaseEntity;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.ClassStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ClassResponse extends BaseEntity {
	private String name;
	
	private String room;
	
	private String code;
	
	private ClassStatus status;
	
	private List<HashMap<String, Object>> users = new ArrayList<>();
	
	public static ClassResponse parseFromClass(Class classEntity) {
		var users = new ArrayList<HashMap<String, Object>>();
		for (var user : classEntity.getUsers()) {
			users.add(new HashMap<String, Object>() {{
				put("id", user.getId());
				put("email", user.getEmail());
				put("fullName", user.getFullName());
			}});
		}
		return ClassResponse.builder()
				.id(classEntity.getId())
				.name(classEntity.getName())
				.room(classEntity.getRoom())
				.code(classEntity.getCode())
				.status(classEntity.getStatus())
				.users(users)
				.createdAt(classEntity.getCreatedAt())
				.createdBy(classEntity.getCreatedBy())
				.updatedAt(classEntity.getUpdatedAt())
				.updatedBy(classEntity.getUpdatedBy())
				.isDeleted(classEntity.getIsDeleted())
				.build();
	}
}
