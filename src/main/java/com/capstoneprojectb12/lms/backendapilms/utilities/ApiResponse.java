package com.capstoneprojectb12.lms.backendapilms.utilities;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Slf4j
public class ApiResponse<T> implements Serializable {
    private Object errors;
    private boolean status = false;
    private T data;

    public static ApiResponse<?> failed(Object messages) {
        return ApiResponse.<Object>builder()
                .data(null)
                .status(false)
                .errors(new HashMap<String, Object>() {{
                    put("message", messages);
                }})
                .build();
    }

    public static ApiResponse<?> error(String errorMessage) {
        return ApiResponse.<Object>builder()
                .data(null)
                .status(false)
                .errors(new HashMap<String, Object>() {{
                    put("message", errorMessage);
                }})
                .build();
    }

    public static ApiResponse<?> success(Object data) {
        return ApiResponse.<Object>builder()
                .data(data)
                .status(true)
                .errors(null)
                .build();
    }

    public static ResponseEntity<?> errorValidation(Errors errors) {
        return ResponseEntity.badRequest().body(failed(ApiValidation.getErrorMessages(errors)));
    }

    public static ResponseEntity<?> ok(Object data) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(success(data));
    }

    public static ResponseEntity<?> bad(Object message) {
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(failed(message));
    }

    public static ResponseEntity<?> err(Exception message) {
        return ResponseEntity
                .internalServerError()
                .contentType(MediaType.APPLICATION_JSON)
                .body(error(message.getMessage()));
    }

    public static Map<String, Object> notFound(String message) {
        return new HashMap<>() {
            {
                put("message", message);
            }
        };
    }

    public static Map<String, Object> notFound() {
        return notFound(FinalVariable.DATA_NOT_FOUND);
    }

    public static ApiResponse<?> accessDenied() {
        return accessDenied("You are not allowed to access this endpoint");
    }

    public static ApiResponse<?> accessDenied(String caused) {
        return ApiResponse.builder()
                .data(null)
                .errors(new HashMap<String, Object>() {{
                    put("message", "access denied");
                    put("caused", caused);
                }})
                .status(false)
                .build();
    }

    @Deprecated(since = "deprecated, and replaced with ResponseDelete.deleted and ResponseDelete.notDeleted")
    public static ResponseDelete gqlResponseDelete(ResponseEntity<?> response) {
        var apiRes = (ApiResponse<?>) response.getBody();
        assert apiRes != null;
        return ResponseDelete.builder()
                .status(apiRes.isStatus())
                .error(apiRes.getErrors())
                .build();
    }

    public static ApiResponse<?> getResponse(ResponseEntity<?> response) {
        return (ApiResponse<?>) response.getBody();
    }

    public static <T> Optional<T> extract(
            @NotNull(message = "Needed value cannot be null") T neededValue,
            @NotNull(message = "Api Response cannot be null") ApiResponse<?> response) throws ClassCastException {
        if (response.getData() == null) {
            log.warn("Cannot convert, Data from Api Response is not instance of Needed value");
            return Optional.empty();
        }
        return Optional.ofNullable((T) response.getData());
    }

    public static <T> Optional<T> extract(
            @NotNull(message = "Needed value cannot be null") T neededValue,
            @NotNull(message = "Response Entity cannot be null") ResponseEntity<?> response) {
        return extract(neededValue, getResponse(response));
    }
}
