package com.capstoneprojectb12.lms.backendapilms.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.capstoneprojectb12.lms.backendapilms.services.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // permit GraphQL url
                .authorizeHttpRequests().antMatchers("**/graphql/**").permitAll()

                // disable session
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // spill user deatils service and password encoder to spring security
                .and().authenticationProvider(
                        new DaoAuthenticationProvider() {
                            {
                                setPasswordEncoder(passwordEncoder);
                                setUserDetailsService(userService);
                            }
                        })
                .authorizeRequests()

                // disable csrf
                .and().csrf().disable();

        return http.build();
    }
}
