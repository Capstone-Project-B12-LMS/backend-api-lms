package com.capstoneprojectb12.lms.backendapilms.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.*;

import com.capstoneprojectb12.lms.backendapilms.filters.JwtFilter;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvc
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // enable cors
                .cors().and()
                // permit GraphQL url
                .authorizeRequests().antMatchers("**/graphql/**").permitAll()

                // permit rest api url
                .antMatchers("/restapi/login", "/restapi/register").permitAll()

                // use jwt filter
                .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                // disable session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // spill user deatils service and password encoder to spring security
                .and().authenticationProvider(
                        new DaoAuthenticationProvider() {
                            {
                                setPasswordEncoder(passwordEncoder);
                                setUserDetailsService(userService);
                            }
                        })

                // disable csrf
                .csrf().disable();

        return http.build();
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry corsRegistry) {
                corsRegistry.addMapping("**").allowedOriginPatterns("**").allowedOrigins("**");
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
