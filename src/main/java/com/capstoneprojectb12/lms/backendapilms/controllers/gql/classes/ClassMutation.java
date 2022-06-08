package com.capstoneprojectb12.lms.backendapilms.controllers.gql.classes;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseMutation;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.services.ClassService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SchemaMapping(typeName = "ClassMutation")
@RequiredArgsConstructor
@CrossOrigin
// @PreAuthorize(value = "hasAnyAuthority('USER')")
public class ClassMutation implements BaseMutation<Class, ClassNew> {
    private final ClassService classService;

    @Override
    @SchemaMapping(field = "save")
    public Class save(@Argument(name = "request") ClassNew request) {
        var classRequest = this.classService.toEntity(request);
        try {
            var savedClass = this.classService.save(classRequest);
            return savedClass.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    // @SchemaMapping(field = "update")
    public Class update(String id, ClassNew request) {
        // TODO Auto-generated method stub
        return null;
    }

    @SchemaMapping(field = "updateById")
    public Class update(@Argument(name = "id") String id, @Argument(name = "request") ClassUpdate request) {
        try {
            log.info("Get class by id");
            var className = this.classService.findById(id);
            if (!className.isPresent()) {
                log.info("Class not found");
                return null;
            }
            var savedClass = this.classService.update(className.get(), request);
            log.info("Success updated class");
            return savedClass.orElse(null);
        } catch (Exception e) {
            log.error("failed when update class", e);
            return null;
        }
    }

    @Override
    @SchemaMapping(field = "deleteById")
    public ResponseDelete deleteById(@Argument(name = "id") String id) {
        return null;
    }

}
