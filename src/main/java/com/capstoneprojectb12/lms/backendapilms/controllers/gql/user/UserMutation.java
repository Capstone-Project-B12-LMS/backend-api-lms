package com.capstoneprojectb12.lms.backendapilms.controllers.gql.user;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.*;
import static com.capstoneprojectb12.lms.backendapilms.utilities.histories.ActivityHistoryUtils.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.*;
import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;
import com.capstoneprojectb12.lms.backendapilms.services.mongodb.ActivityHistoryService;
import com.capstoneprojectb12.lms.backendapilms.utilities.FinalVariable;
import com.capstoneprojectb12.lms.backendapilms.utilities.ResponseToken;
import com.capstoneprojectb12.lms.backendapilms.utilities.jwt.JwtUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SchemaMapping(typeName = "UserMutation")
@RequiredArgsConstructor
public class UserMutation {
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final ActivityHistoryService history;

	@SchemaMapping(field = "register")
	public User register(@Argument(name = "request") UserNew request) {
		log.info("Entering method to save new role");
		new Thread(() -> history.save(youAreSuccessfully(
				"register at " + new SimpleDateFormat(FinalVariable.DATE_FORMAT).format(new Date())))).start();
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

			new Thread(() -> history.save(youAreSuccessfully(
					"login at " + new SimpleDateFormat(FinalVariable.DATE_FORMAT).format(new Date())))).start();

			return ResponseToken.builder()
					.error(null)
					.token(tokenString)
					.build();

		} catch (Exception e) {
			log.error("login failed", e);

			new Thread(() -> history
					.save("Login failed at " + new SimpleDateFormat(FinalVariable.DATE_FORMAT).format(new Date())))
					.start();

			return ResponseToken.builder()
					.error(e.getMessage())
					.token(null)
					.build();
		}
	}

	@PreAuthorize(value = "hasAnyAuthority('USER')")
	@SchemaMapping(field = "updateById")
	public User updateById(@Argument(name = "id") String id, @Argument(name = "request") UserUpdate request) {
		return extract(new User(), getResponse(this.userService.update(id, request))).orElse(null);
	}
}
