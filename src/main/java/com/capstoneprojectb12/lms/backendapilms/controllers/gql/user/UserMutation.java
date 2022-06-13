package com.capstoneprojectb12.lms.backendapilms.controllers.gql.user;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserLogin;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;
import com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse;
import com.capstoneprojectb12.lms.backendapilms.utilities.ResponseToken;
import com.capstoneprojectb12.lms.backendapilms.utilities.jwt.JwtUtils;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.getResponse;

@Slf4j
@Controller
@SchemaMapping(typeName = "UserMutation")
@RequiredArgsConstructor
public class UserMutation {
	private final UserService userService;
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	
	@SchemaMapping(field = "register")
	public User register(@Argument(name = "request") UserNew request) {
		log.info("Entering method to save new role");
		var response = this.userService.save(request);
		return (User) getResponse(response).getData();
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
			return ResponseToken.builder()
					.error(null)
					.token(tokenString)
					.build();
			
		} catch (Exception e) {
			log.error("login failed", e);
			return ResponseToken.builder()
					.error(e.getMessage())
					.token(null)
					.build();
		}
	}
	
	@PreAuthorize(value = "hasAnyAuthority('USER')")
	@SchemaMapping(field = "updateById")
	public User updateById(@Argument(name = "id") String id, @Argument(name = "request") UserUpdate request) {
		try {
			var response = this.userService.update(id, request);
			var apiRes = (ApiResponse<?>) response.getBody();
			assert apiRes != null;
			var user = (User) Objects.requireNonNull(apiRes.getData());
			return user;
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
