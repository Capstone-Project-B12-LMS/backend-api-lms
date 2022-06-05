package com.capstoneprojectb12.lms.backendapilms.utilities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ApiResponse<T> {
    private Object errors;
    private T data;

    public static ApiResponse<?> failed(Object messages) {
        return ApiResponse.<Object>builder()
                .data(null)
                .errors(messages)
                .build();
    }

    public static ApiResponse<?> error(Object errorMessage) {
        return ApiResponse.<Object>builder()
                .data(null)
                .errors(errorMessage)
                .build();
    }

    public static ApiResponse<?> success(Object data) {
        return ApiResponse.<Object>builder()
                .data(data)
                .errors(null)
                .build();
    }
}
