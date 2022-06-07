package com.capstoneprojectb12.lms.backendapilms.models.dtos.classes;

import javax.validation.constraints.*;

import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.ClassStatus;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ClassNew {

    @NotNull(message = "cannot be null")
    @NotBlank(message = "cannot be blank")
    @NotEmpty(message = "cannot be empty")
    private String name;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "cannot be blank")
    @NotEmpty(message = "cannot be empty")
    private String room;

    // @NotNull(message = "cannot be null")
    // @NotEmpty(message = "cannot be empty")
    // private ClassStatus status;
}
