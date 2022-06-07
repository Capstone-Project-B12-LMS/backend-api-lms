package com.capstoneprojectb12.lms.backendapilms.utilities;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

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

    public static ResponseEntity<?> errorValidation(Errors errors) {
        return ResponseEntity.badRequest().body(failed(ApiValidation.getErrorMessages(errors)));
    }

    public static ResponseEntity<?> responseOk(Object data) {
        return ResponseEntity.ok(success(data));
    }

    public static ResponseEntity<?> responseBad(Object message) {
        return ResponseEntity.badRequest().body(failed(message));
    }

    public static ResponseEntity<?> responseError(Exception message) {
        return ResponseEntity.internalServerError().body(error(message.getMessage()));
    }
}
