package com.capstoneprojectb12.lms.backendapilms.controllers.gql.base;

import java.util.List;

import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;

public interface BaseQuery<T> {
    public List<T> findAll();

    public PaginationResponse<List<T>> findAllWithPageable(int page, int size);

    public List<T> findAllDeleted();

    public PaginationResponse<List<T>> findAllDeletedWithPageable(int page, int size);

    public T findById(String id);

}
