package com.capstoneprojectb12.lms.backendapilms.controllers.gql.base;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;

public interface BaseMutation<ENTITY, NEW, UPDATE> {
	ENTITY save(NEW request);
	
	ENTITY update(String id, UPDATE request);
	
	ResponseDelete deleteById(String id);
}
