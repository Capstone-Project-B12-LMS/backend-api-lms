package com.capstoneprojectb12.lms.backendapilms.models.dtos.user;

import java.util.List;

import javax.validation.constraints.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserNew {
    @NotNull(message = "cannot be null")
    @NotBlank(message = "cannot be  blank")
    @NotEmpty(message = "cannot be empty")
    private String fullName;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "cannot be  blank")
    @NotEmpty(message = "cannot be empty")
    @Email(message = "invalid email format")
    private String email;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "cannot be  blank")
    @NotEmpty(message = "cannot be empty")
    private String password;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "cannot be  blank")
    @NotEmpty(message = "cannot be empty")
    private List<@NotNull(message = "cannot be null") @NotBlank(message = "cannot be  blank") @NotEmpty(message = "cannot be empty") String> roles;
}
