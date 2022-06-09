package com.capstoneprojectb12.lms.backendapilms.utilities.requests;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ApiUpdateRequest<T> {
    private String targetId;
    private T request;
}
