package com.capstoneprojectb12.lms.backendapilms.controllers.gql.guidance;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseMutation;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.guidance.GuidanceNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.guidance.GuidanceUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Guidance;
import com.capstoneprojectb12.lms.backendapilms.services.GuidanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import static com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete.deleted;
import static com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete.notDeleted;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.getResponse;

@Controller
@RequiredArgsConstructor
@Slf4j
@SchemaMapping(typeName = "GuidanceMutation")
public class GuidanceMutation implements BaseMutation<Guidance, GuidanceNew, GuidanceUpdate> {
    private final GuidanceService guidanceService;

    @Override
    @SchemaMapping(field = "save")
    public Guidance save(@Argument(name = "request") GuidanceNew request) {
        return extract(new Guidance(), this.guidanceService.save(request)).orElse(null);
    }

    @Override
    public Guidance update(String id, GuidanceUpdate request) {
        return null;
    }

    @Override
    @SchemaMapping(field = "deleteById")
    public ResponseDelete<Guidance> deleteById(@Argument(name = "id") String id) {
        var res = this.guidanceService.deleteById(id);
        var guidance = extract(new Guidance(), res);
        if (guidance.isPresent()) {
            return deleted(guidance.get());
        }
        return notDeleted(getResponse(res).getErrors());
    }
}
