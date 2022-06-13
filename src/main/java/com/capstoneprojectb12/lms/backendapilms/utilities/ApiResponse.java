package com.capstoneprojectb12.lms.backendapilms.utilities;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
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
	
	public static ApiResponse<?> error(Object errorMessage) {
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
		return ResponseEntity.ok(success(data));
	}
	
	public static ResponseEntity<?> bad(Object message) {
		return ResponseEntity.badRequest().body(failed(message));
	}
	
	public static ResponseEntity<?> err(Exception message) {
		return ResponseEntity.internalServerError().body(error(message.getMessage()));
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
}
