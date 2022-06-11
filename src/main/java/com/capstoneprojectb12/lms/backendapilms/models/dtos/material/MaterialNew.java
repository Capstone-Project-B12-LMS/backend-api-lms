package com.capstoneprojectb12.lms.backendapilms.models.dtos.material;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
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
	
	@ApiModelProperty(allowEmptyValue = true, dataType = MediaType.MULTIPART_FORM_DATA_VALUE)
	private MultipartFile video;
	
	@ApiModelProperty(allowEmptyValue = true, dataType = MediaType.MULTIPART_FORM_DATA_VALUE)
	private MultipartFile file;
	
	@ApiModelProperty(allowEmptyValue = true)
	private LocalDateTime deadline;
	
	@NotNull(message = "cannot be null")
	@NotBlank(message = "cannot be blank")
	private Integer point;
	
	@NotNull(message = "cannot be null")
	@NotBlank(message = "cannot be blank")
	@NotEmpty(message = "cannot be empty")
	private String category;
}
