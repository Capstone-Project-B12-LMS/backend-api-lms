package com.capstoneprojectb12.lms.backendapilms.controllers.gql.user;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserLogin;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;
import com.capstoneprojectb12.lms.backendapilms.utilities.ResponseToken;
import com.capstoneprojectb12.lms.backendapilms.utilities.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.getResponse;

@Slf4j
@Controller
@SchemaMapping(typeName = "UserMutation")
@RequiredArgsConstructor
public class UserMutation {
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	
	@SchemaMapping(field = "register")
	public User register(@Argument(name = "request") UserNew request) {
		log.info("Entering method to save new role");
		return extract(new User(), getResponse(this.userService.save(request))).orElse(null);
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
	
	//	@PreAuthorize(value = "hasAnyAuthority('USER')") // TODO: enable security
	@SchemaMapping(field = "updateById")
	public User updateById(@Argument(name = "id") String id, @Argument(name = "request") UserUpdate request) {
		return extract(new User(), getResponse(this.userService.update(id, request))).orElse(null);
	}
}
