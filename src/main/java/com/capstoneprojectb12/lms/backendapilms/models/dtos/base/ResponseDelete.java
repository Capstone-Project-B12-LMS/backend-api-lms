package com.capstoneprojectb12.lms.backendapilms.models.dtos.base;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ResponseDelete implements Serializable {
    private Object error;
    private boolean status;
}
