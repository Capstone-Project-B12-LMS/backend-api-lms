package com.capstoneprojectb12.lms.backendapilms.filters;

import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse;
import com.capstoneprojectb12.lms.backendapilms.utilities.JSON;
import com.capstoneprojectb12.lms.backendapilms.utilities.jwt.JwtUtils;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(value = 1)
public class JwtFilter extends OncePerRequestFilter {
	private final JwtUtils jwtUtils;
	private final UserRepository userRepository;
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		log.info("Entering jwt auth filter");
		
		final var AUTHORIZATION = "Authorization";
		final var BEARER = "Bearer";
		
		final var authHeader = request.getHeader(AUTHORIZATION);
		if (authHeader != null && authHeader.startsWith(BEARER)) {
			try {
				final var tokenString = authHeader.substring(BEARER.length());
				final var claims = this.jwtUtils.getClaims(tokenString);
				final var user = this.userRepository.findById(claims.get("userId", String.class));
				
				if (this.jwtUtils.isValid(tokenString, user.get()) && SecurityContextHolder.getContext().getAuthentication() == null) {
					log.info("Jwt token is valid");
					final var authUser = new UsernamePasswordAuthenticationToken(
							user.get().getEmail(),
							user.get().getPassword(),
							user.get().getAuthorities());
					
					final var userDetails = new WebAuthenticationDetailsSource().buildDetails(request);
					authUser.setDetails(userDetails);
					
					log.info("Registered user to security context");
					SecurityContextHolder.getContext().setAuthentication(authUser);
					log.info("success registered");
				}
			} catch (Exception e) {
				log.error("Invalid jwt token : " + e.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				response.getWriter().println(JSON.create(ApiResponse.accessDenied(e.getMessage())));
				return;
			}
		} else {
			log.info("Authorization header is null");
		}
		
		log.info("Do next filter");
		filterChain.doFilter(request, response);
	}
	
}
