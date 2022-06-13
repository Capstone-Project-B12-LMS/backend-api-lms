package com.capstoneprojectb12.lms.backendapilms.models.dtos.classes;

import java.io.Serializable;
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
public class ClassNew implements Serializable {
	
	@NotNull(message = "cannot be null")
	@NotBlank(message = "cannot be blank")
	@NotEmpty(message = "cannot be empty")
	private String name;
	
	//    @NotNull(message = "cannot be null")
//    @NotBlank(message = "cannot be blank")
//    @NotEmpty(message = "cannot be empty")
	private String room;
	
	// @NotNull(message = "cannot be null")
	// @NotEmpty(message = "cannot be empty")
	// private ClassStatus status;
}
