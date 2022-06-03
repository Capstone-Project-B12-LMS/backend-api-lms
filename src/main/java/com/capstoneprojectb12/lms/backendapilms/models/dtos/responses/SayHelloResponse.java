package com.capstoneprojectb12.lms.backendapilms.models.dtos.responses;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SayHelloResponse {
    private String message;
    private Object errors;
}
