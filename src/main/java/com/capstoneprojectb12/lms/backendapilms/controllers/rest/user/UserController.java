package com.capstoneprojectb12.lms.backendapilms.controllers.rest.user;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserLogin;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.ClassStatus;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.services.ClassService;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;
import com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse;
import com.capstoneprojectb12.lms.backendapilms.utilities.ApiValidation;
import com.capstoneprojectb12.lms.backendapilms.utilities.ResponseToken;
import com.capstoneprojectb12.lms.backendapilms.utilities.jwt.JwtUtils;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.bad;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.err;

@Slf4j
@RestController
@RequestMapping(value = {"/restapi/v1"})
@RequiredArgsConstructor
public class UserController {
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final ClassService classService;
	private final UserRepository userRepository;
	private final JwtUtils jwtUtils;
	
	@PostMapping(value = {"/login"})
	public ResponseEntity<?> login(@RequestBody @Valid UserLogin request, @Parameter(hidden = true) Errors errors) {
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(ApiValidation.getErrorMessages(errors));
		}
		
		try {
			var user = this.userRepository.findByEmailEqualsIgnoreCase(request.getEmail());
			if (user.isEmpty()) {
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
			return bad(e.getMessage());
		} catch (BadCredentialsException e) {
			log.error("password doesn't match", e);
			return bad("password doesn't match");
		} catch (Exception e) {
			log.error("Login failed", e);
			return err(e);
		}
	}
	
	@PostMapping(value = {"/register"})
	public ResponseEntity<?> register(@RequestBody @Valid UserNew request, @Parameter(hidden = true) Errors errors) {
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(ApiResponse.failed(ApiValidation.getErrorMessages(errors)));
		}
		return this.userService.save(request);
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER')")
	@GetMapping(value = {"/users/{id}"})
	public ResponseEntity<?> findById(@PathVariable(name = "id") String id) {
		log.info("entering endpoint to find user by id");
		return this.userService.findById(id);
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER')")
	@PutMapping(value = {"/users/{id}"})
	public ResponseEntity<?> updateById(@PathVariable(name = "id") String userId,
	                                    @RequestBody @Valid UserUpdate request, @Parameter(hidden = true) Errors errors) {
		if (errors.hasErrors()) {
			return ApiResponse.errorValidation(errors);
		}
		return this.userService.update(userId, request);
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER')")
	@GetMapping(value = {"/users/class/{userId}/{classStatus}"})
	public ResponseEntity<?> getUserClassByUserId(@PathVariable(name = "userId") String userId,
	                                              @PathVariable(name = "classStatus") ClassStatus classStatus) {
		return this.classService.findByUserId(userId, classStatus.name());
	}
}
