package com.capstoneprojectb12.lms.backendapilms.models.dtos.classes;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.ClassStatus;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ClassUpdate extends ClassNew {
    @NotNull(message = "cannot be null")
    @NotEmpty(message = "cannot be empty")
    private ClassStatus status;
}
