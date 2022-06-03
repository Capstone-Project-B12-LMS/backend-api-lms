package com.capstoneprojectb12.lms.backendapilms.utilities.jwt;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class Jwtutils {
    private final UserService userService;

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

        var tokenString = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, secretkey)
                .setClaims(claims)
                .setIssuedAt(createdAt)
                .setExpiration(expiredAt)
                .compact();
        return tokenString;
    }

    public Claims getClaims(String tokenString) {
        log.info("Get claims from token string");
        var claims = Jwts.parser()
                .setSigningKey(secretkey)
                .parseClaimsJws(tokenString)
                .getBody();
        return claims;
    }

    public boolean isExpired(String tokenString) {
        var expiredAt = this.getClaims(tokenString).getExpiration();
        return expiredAt.after(new Date(System.currentTimeMillis()));
    }

    public boolean isValid(String tokenString, User user) {
        var userId = this.getClaims(tokenString).get("userId", String.class);
        var userToken = this.userService.findById(userId);
        var isValid = (user.getId().equals(userToken.get().getId())
                && userToken.get()
                        .getEmail()
                        .equalsIgnoreCase(user.getEmail())
                && userToken.get()
                        .getPassword()
                        .equals(user.getPassword())
                && !this.isExpired(tokenString));
        return isValid;
    }
}
