package com.capstoneprojectb12.lms.backendapilms.controllers.gql.classes;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseMutation;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.JoinClass;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.ClassRepository;
import com.capstoneprojectb12.lms.backendapilms.services.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.HashMap;

import static com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete.notDeleted;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.getResponse;

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
        return extract(new Class(), this.classService.save(request)).orElse(null);
    }

    @Override
    @SchemaMapping(field = "updateById")
    public Class update(@Argument(name = "id") String id, @Argument(name = "request") ClassUpdate request) {
        return extract(new Class(), this.classService.update(id, request)).orElse(null);
    }

    @Override
    @SchemaMapping(field = "deleteById")
    public ResponseDelete<Class> deleteById(@Argument(name = "id") String id) {
        var res = this.classService.deleteById(id);
        return extract(new ResponseDelete<Class>(), res).orElse(notDeleted(getResponse(res).getErrors()));
    }

    @SchemaMapping(field = "join")
    public Object join(@Argument(name = "classCode") String classCode, @Argument(name = "userId") String userId) {
        return extract(new HashMap<String, Object>(), this.classService.joinUserToClass(JoinClass.builder().classCode(classCode).userId(userId).build())).orElse(null);
    }

    @SchemaMapping(field = "deleteUserById")
    public Class deleteUserById(@Argument(name = "classId") String classId, @Argument(name = "userId") String userId) {
        return extract(new Class(), this.classService.deleteUserById(classId, userId)).orElse(null);
    }

}
