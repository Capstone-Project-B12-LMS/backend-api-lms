package com.capstoneprojectb12.lms.backendapilms.models.dtos.material;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MaterialNew {
	@NotNull(message = "cannot be null")
	@NotBlank(message = "cannot be blank")
	@NotEmpty(message = "cannot be empty")
	private String classId;
	
	@NotNull(message = "cannot be null")
	@NotBlank(message = "cannot be blank")
	@NotEmpty(message = "cannot be empty")
	private String title;
	
	@NotNull(message = "cannot be null")
	@NotBlank(message = "cannot be blank")
	@NotEmpty(message = "cannot be empty")
	private String content;
	
	private String topicId;
	
	@Schema(nullable = true, type = MediaType.MULTIPART_FORM_DATA_VALUE)
	private MultipartFile video;
	
	@Schema(nullable = true, type = MediaType.MULTIPART_FORM_DATA_VALUE)
	private MultipartFile file;
	
	@Schema(nullable = true)
	private String deadline;
	
	@NotNull(message = "cannot be null")
	private Integer point;
	
	@NotNull(message = "cannot be null")
	@NotBlank(message = "cannot be blank")
	@NotEmpty(message = "cannot be empty")
	private String category;
}
