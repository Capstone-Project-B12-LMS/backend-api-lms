package com.capstoneprojectb12.lms.backendapilms.controllers.gql.user;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.base.BaseQuery;
import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;

import lombok.RequiredArgsConstructor;

@Controller
@SchemaMapping(typeName = "UserQuery")
@CrossOrigin
@PreAuthorize(value = "hasAnyAuthority('USER')")
@RequiredArgsConstructor
public class UserQuery implements BaseQuery<User> {
    private final UserService userService;

    @Override
    @SchemaMapping(field = "findAll")
    public List<User> findAll() {
        return this.userService.findAll();
    }

    @Override
    @SchemaMapping(field = "findAllWithPageable")
    public PaginationResponse<List<User>> findAllWithPageable(int page, int size) {
        var userPage = this.userService.findAll(page, size);
        var response = this.userService.toPaginationResponse(userPage);
        return response;
    }

    @Override
    @SchemaMapping(field = "findById")
    public User findById(@Argument(name = "id") String id) {
        var user = this.userService.findById(id);
        return user.orElse(null);
    }

    @Override
    @SchemaMapping(field = "findAllDeleted")
    public List<User> findAllDeleted() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @SchemaMapping(field = "findAllDeletedWithPageable")
    public PaginationResponse<List<User>> findAllDeletedWithPageable(int page, int size) {
        // TODO Auto-generated method stub
        return null;
    }

}
