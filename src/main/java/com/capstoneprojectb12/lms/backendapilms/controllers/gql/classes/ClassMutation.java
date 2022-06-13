package com.capstoneprojectb12.lms.backendapilms.controllers.gql.classes;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseMutation;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.ClassRepository;
import com.capstoneprojectb12.lms.backendapilms.services.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.getResponse;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.gqlResponseDelete;

@Slf4j
@Controller
@SchemaMapping(typeName = "ClassMutation")
@RequiredArgsConstructor
public class ClassMutation implements BaseMutation<Class, ClassNew, ClassUpdate> {
	private final ClassService classService;
	private final ClassRepository classRepository;
	
	@Override
	@SchemaMapping(field = "save")
	public Class save(@Argument(name = "request") ClassNew request) {
		var classRequest = this.classService.toEntity(request);
		try {
			return this.classRepository.save(classRequest);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	@SchemaMapping(field = "updateById")
	public Class update(@Argument(name = "id") String id, @Argument(name = "request") ClassUpdate request) {
		try {
			log.info("Get class by id");
			var response = this.classService.update(id, request);
			log.info("Success updated class");
			var classEntity = (Class) getResponse(response).getData();
			assert classEntity != null;
			return classEntity;
		} catch (Exception e) {
			log.error("failed when update class", e);
			return null;
		}
	}
	
	@Override
	@SchemaMapping(field = "deleteById")
	public ResponseDelete deleteById(@Argument(name = "id") String id) {
		return gqlResponseDelete(this.classService.deleteById(id));
	}
	
}
