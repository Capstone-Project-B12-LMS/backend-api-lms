package com.capstoneprojectb12.lms.backendapilms.models.dtos.hello;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SayHelloResponse implements Serializable {
    private String message;
    private Object errors;
}
