package com.capstoneprojectb12.lms.backendapilms.models.dtos.base;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ResponseDelete<T> implements Serializable {
	private Object error;
	private boolean status;
	private T deleted;
	
	public static <S> ResponseDelete<S> deleted(S deletedValue) {
		return ResponseDelete.<S>builder()
				.error(null)
				.deleted(deletedValue)
				.status(true)
				.build();
	}
	
	public static <S> ResponseDelete<S> notDeleted(Object errors) {
		return ResponseDelete.<S>builder()
				.error(errors)
				.deleted(null)
				.status(false)
				.build();
	}
}
