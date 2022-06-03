package com.capstoneprojectb12.lms.backendapilms.controllers.gql.user;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserNew;
import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SchemaMapping(typeName = "UserMutation")
@RequiredArgsConstructor
public class UserMutation {
    private final UserService userService;

    @SchemaMapping(field = "register")
    public User register(@Argument(name = "request") UserNew request) {
        log.info("Entering method to save new role");
        var user = this.userService.toEntity(request);

        var savedUser = this.userService.save(user);
        if (!savedUser.isPresent()) {
            log.error("error when save new user");
            return null;
        }

        return savedUser.get();
    }

    public User update(UserNew request) {
        // TODO Auto-generated method stub
        return null;
    }

    public ResponseDelete deleteById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

}
