package com.capstoneprojectb12.lms.backendapilms.models.dtos.role;

import javax.validation.constraints.*;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RoleNew {
    @NotNull(message = "cannot be null")
    @NotBlank(message = "cannot be  blank")
    @NotEmpty(message = "cannot be empty")
    private String name;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "cannot be  blank")
    @NotEmpty(message = "cannot be empty")
    private String description;

    public Role toEntity() {
        return Role.builder()
                .name(this.name)
                .description(this.description)
                .isDeleted(false)
                .build();
    }
}
