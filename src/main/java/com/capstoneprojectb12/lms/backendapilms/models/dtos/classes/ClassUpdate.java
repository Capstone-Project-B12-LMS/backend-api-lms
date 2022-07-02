package com.capstoneprojectb12.lms.backendapilms.models.dtos.classes;

import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.ClassStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ClassUpdate extends ClassNew {
	@NotNull(message = "cannot be null")
	@Schema(allowableValues = "you can use ACTIVE, INACTIVE, or WILL_END")
	private ClassStatus status;
	private String reportUrl;
}
