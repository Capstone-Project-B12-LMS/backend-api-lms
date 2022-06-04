package com.capstoneprojectb12.lms.backendapilms.models.dtos.base;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ResponseDelete {
    private Object error;
    private boolean status;
}
