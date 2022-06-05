package com.capstoneprojectb12.lms.backendapilms.utilities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ResponseToken {
    private String token;
    private Object error;
}
