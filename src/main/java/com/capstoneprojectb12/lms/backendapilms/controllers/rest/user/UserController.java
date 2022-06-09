package com.capstoneprojectb12.lms.backendapilms.controllers.rest.user;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.*;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;
import com.capstoneprojectb12.lms.backendapilms.utilities.*;
import com.capstoneprojectb12.lms.backendapilms.utilities.jwt.JwtUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping(value = { "/restapi/v1" })
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping(value = { "/login" })
    public ResponseEntity<?> login(@RequestBody @Valid UserLogin request, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ApiValidation.getErrorMessages(errors));
        }

        try {
            var user = this.userService.findByEmail(request.getEmail());
            if (!user.isPresent()) {
                throw new UsernameNotFoundException("User not found");
            }
            var authUser = new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword(),
                    user.get().getAuthorities());

            log.info("Authenticate user");
            authenticationManager.authenticate(authUser);
            log.info("Success authenticate user");

            var tokenString = jwtUtils.generateTokenString(user.get());
            var response = ResponseToken.builder()
                    .error(null)
                    .status(true)
                    .token(tokenString)
                    .build();

            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            log.error("user not found", e);
            return ApiResponse.responseBad(e.getMessage());
        } catch (BadCredentialsException e) {
            log.error("password doesn't match", e);
            return ApiResponse.responseBad(e.getMessage());
        } catch (Exception e) {
            log.error("Login failed", e);
            return ApiResponse.responseError(e);
        }
    }

    @PostMapping(value = { "/register" })
    public ResponseEntity<?> register(@RequestBody @Valid UserNew request, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ApiResponse.failed(ApiValidation.getErrorMessages(errors)));
        }

        try {
            var user = this.userService.toEntity(request);
            var savedUser = this.userService.save(user);
            return ApiResponse.responseOk(savedUser);
        } catch (DataIntegrityViolationException e) {
            log.error("data already exists", e);
            return ApiResponse.responseBad("data already exists");
        } catch (Exception e) {
            log.error("Failed when register user", e);
            return ApiResponse.responseError(e);
        }
    }

    @PreAuthorize(value = "hasAnyAuthority('USER')")
    @GetMapping(value = { "/users/{id}" })
    public ResponseEntity<?> findById(@PathVariable(name = "id") String id) {

        try {
            var user = this.userService.findByEmail(id);
            if (user.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success(user.get()));
            }

            return ResponseEntity.badRequest().body(
                    ApiResponse.failed(new HashMap<>() {
                        {
                            put("message", "user not found");
                        }
                    }));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PreAuthorize(value = "hasAnyAuthority('USER')")
    @PutMapping(value = { "/users/{id}" })
    public ResponseEntity<?> updateById(
            @PathVariable(name = "id") String userId,
            @RequestBody @Valid UserUpdate request,
            Errors errors) {

        if (errors.hasErrors()) {
            return ApiResponse.errorValidation(errors);
        }

        try {
            var user = this.userService.findById(userId);
            if (!user.isPresent()) {
                return ApiResponse.responseOk(new HashMap<>() {
                    {
                        put("message", "user not found");
                    }
                });
            }

            user = this.userService.update(user.get(), request);
            return ApiResponse.responseOk(user.get());
        } catch (Exception e) {
            log.error("error when update user by id", e);
            return ApiResponse.responseError(e);
        }
    }
}
