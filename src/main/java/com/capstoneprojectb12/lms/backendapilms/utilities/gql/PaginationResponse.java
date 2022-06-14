package com.capstoneprojectb12.lms.backendapilms.utilities.gql;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PaginationResponse<T> {
	private T data;
	private int page;
	private int size;
	private int totalPage;
	private long totalSize;
	
	public static PaginationResponse<?> empty() {
		return empty(0, 0);
	}
	
	public static PaginationResponse<?> empty(int page, int size) {
		return empty(new ArrayList<>(), page, size);
	}
	
	public static <T> PaginationResponse<T> empty(T content, int page, int size) {
		return PaginationResponse.<T>builder()
				.data(null)
				.page(page)
				.size(size)
				.totalSize(0)
				.totalPage(0)
				.build();
	}
}
