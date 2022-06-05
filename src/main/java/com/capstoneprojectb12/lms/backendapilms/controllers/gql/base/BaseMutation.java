package com.capstoneprojectb12.lms.backendapilms.controllers.gql.base;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;

public interface BaseMutation<T, S> {
    public T save(S request);

    public T update(S request);

    public ResponseDelete deleteById(String id);
}
