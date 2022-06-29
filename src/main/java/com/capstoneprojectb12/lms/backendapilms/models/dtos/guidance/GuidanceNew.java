package com.capstoneprojectb12.lms.backendapilms.models.dtos.guidance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GuidanceNew {
	private String topic;
	
	@NotNull(message = "cannot be null")
	@NotBlank(message = "cannot be blank")
	@NotEmpty(message = "cannot be empty")
	private String userId;
	
	@NotNull(message = "cannot be null")
	@NotBlank(message = "cannot be blank")
	@NotEmpty(message = "cannot be empty")
	private String classId;
	
	
	@NotNull(message = "cannot be null")
	@NotBlank(message = "cannot be blank")
	@NotEmpty(message = "cannot be empty")
	private String content;
	
	@Schema(description = "Only values ACCEPTED, DECLINE, SENDED can be allowed")
	@NotNull(message = "cannot be null")
	@NotBlank(message = "cannot be blank")
	@NotEmpty(message = "cannot be empty")
	private String status;
}
