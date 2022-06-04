package com.capstoneprojectb12.lms.backendapilms.utilities.gql;

import lombok.*;
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
}
