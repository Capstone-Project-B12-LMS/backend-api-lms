package com.capstoneprojectb12.lms.backendapilms.controllers.rest.user;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserLogin;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserNew;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;
import com.capstoneprojectb12.lms.backendapilms.utilities.*;
import com.capstoneprojectb12.lms.backendapilms.utilities.jwt.JwtUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping(value = { "/restapi" })
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
                    .token(tokenString)
                    .build();

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            var response = ResponseToken.builder()
                    .error(e.getMessage())
                    .token(null)
                    .build();
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            log.error("Login failed", e);
            return ResponseEntity.internalServerError().body(ResponseToken.builder().error(e.getMessage()).build());
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
            return ResponseEntity.ok(ApiResponse.success(savedUser));
        } catch (Exception e) {
            log.error("Failed when register user", e);
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }
}
