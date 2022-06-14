package com.capstoneprojectb12.lms.backendapilms.utilities.jwt;

import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.UserNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {
	private final UserRepository userRepository;
	
	@Value(value = "${jwt.secret.key}")
	private String secretkey;
	
	@Value(value = "${jwt.token.expired.in.minute}")
	private long expiredInMinute;
	
	public Map<String, Object> generateClaims(User user) {
		log.info("Generating claim");
		var claims = new HashMap<String, Object>();
		claims.put("userId", user.getId());
		claims.put("roles", user.getAuthorities());
		return claims;
	}
	
	public String generateTokenString(User user) {
		log.info("Generating token string");
		var claims = this.generateClaims(user);
		var createdAt = new Date(System.currentTimeMillis());
		var expiredAt = new Date(System.currentTimeMillis() + ((1000L * 60) * expiredInMinute));
		
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS256, secretkey)
				.setClaims(claims)
				.setIssuedAt(createdAt)
				.setExpiration(expiredAt)
				.compact();
	}
	
	public Claims getClaims(String tokenString) {
		log.info("Get claims from token string");
		var claims = Jwts.parser()
				.setSigningKey(secretkey)
				.parseClaimsJws(tokenString)
				.getBody();
		log.info("Success get claims");
		return claims;
	}
	
	public boolean isExpired(String tokenString) {
		var expiredAt = this.getClaims(tokenString).getExpiration();
		return expiredAt.before(new Date(System.currentTimeMillis()));
	}
	
	public boolean isValid(String tokenString, User user) {
		var userId = this.getClaims(tokenString).get("userId", String.class);
		var userToken = this.userRepository.findById(userId).orElseThrow(UserNotFoundException :: new);
		var isValid = (user.getId().equals(userToken.getId())
				&& userToken
				.getEmail()
				.equalsIgnoreCase(user.getEmail())
				&& userToken
				.getPassword()
				.equals(user.getPassword())
				&& ! this.isExpired(tokenString));
		log.info("Is token valid : " + isValid);
		return isValid;
	}
}
