package com.capstoneprojectb12.lms.backendapilms.models.dtos.hello;

import java.io.Serializable;

import javax.validation.constraints.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SayHelloInput implements Serializable {
    @NotNull(message = "cannot be null")
    @NotBlank(message = "cannot be  blank")
    @NotEmpty(message = "cannot be empty")
    private String name;
}
