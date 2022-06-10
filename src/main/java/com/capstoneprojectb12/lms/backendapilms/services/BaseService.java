package com.capstoneprojectb12.lms.backendapilms.services;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;

public interface BaseService<T> extends Serializable {
    public Optional<T> save(T entity);

    public Optional<T> update(T entity);

    public boolean deleteById(String id);

    public boolean existsById(String id);

    public Optional<T> findById(String id);

    public List<T> findAll();

    public List<T> findAll(boolean showDeleted);

    public Page<T> findAll(int page, int size);

    public Page<T> findAll(int page, int size, Sort sort);

    public PaginationResponse<List<T>> toPaginationResponse(Page<T> page);
}
