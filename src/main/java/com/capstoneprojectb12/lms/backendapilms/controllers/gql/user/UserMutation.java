package com.capstoneprojectb12.lms.backendapilms.controllers.gql.user;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.*;
import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;
import com.capstoneprojectb12.lms.backendapilms.utilities.ResponseToken;
import com.capstoneprojectb12.lms.backendapilms.utilities.jwt.JwtUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SchemaMapping(typeName = "UserMutation")
// @CrossOrigin(allowCredentials = "true")
@RequiredArgsConstructor
public class UserMutation {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

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

    @SchemaMapping(field = "login")
    public ResponseToken login(@Argument(name = "request") UserLogin request) {
        log.info("Entering method to login");
        try {
            var user = this.userService.findByEmail(request.getEmail());
            var authUser = new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword(),
                    user.get().getAuthorities());

            log.info("Authenticate user");
            authenticationManager.authenticate(authUser);

            var tokenString = jwtUtils.generateTokenString(user.get());
            var response = ResponseToken.builder()
                    .error(null)
                    .token(tokenString)
                    .build();
            return response;

        } catch (Exception e) {
            log.error("login failed", e);
            var response = ResponseToken.builder()
                    .error(e.getMessage())
                    .token(null)
                    .build();
            return response;
        }
    }

    @PreAuthorize(value = "hasAnyAuthority('USER')")
    @SchemaMapping(field = "updateById")
    public User updateById(@Argument(name = "id") String id, @Argument(name = "request") UserUpdate request) {
        try {
            var user = this.userService.findById(id);
            if (!user.isPresent()) {
                throw new UsernameNotFoundException("user not found");
            }

            user = this.userService.update(user.get(), request);
            return user.orElse(null);
        } catch (Exception e) {
            log.error("error when update user by id", e);
            return null;
        }
    }

    public ResponseDelete deleteById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

}
