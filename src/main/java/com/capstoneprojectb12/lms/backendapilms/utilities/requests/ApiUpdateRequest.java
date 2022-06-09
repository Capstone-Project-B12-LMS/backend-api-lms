package com.capstoneprojectb12.lms.backendapilms.utilities.requests;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ApiUpdateRequest<T> implements Serializable {
    private String targetId;
    private T request;
}
