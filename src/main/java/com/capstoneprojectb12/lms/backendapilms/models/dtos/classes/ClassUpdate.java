package com.capstoneprojectb12.lms.backendapilms.models.dtos.classes;

import javax.validation.constraints.NotNull;

import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.ClassStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ClassUpdate extends ClassNew {
    @NotNull(message = "cannot be null")
    @ApiModelProperty(allowableValues = "you can use ACTIVE, INACTIVE, or WILL_END")
    private ClassStatus status;
}
