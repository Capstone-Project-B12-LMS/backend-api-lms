package com.capstoneprojectb12.lms.backendapilms.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.capstoneprojectb12.lms.backendapilms.controllers.rest.InitRestFullAPI;

import springfox.documentation.builders.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class BeansConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(InitRestFullAPI.class.getPackageName()))
                .paths(PathSelectors.ant("/restapi/**"))
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .version("1.0")
                        .description("This is Rest API documentation for Backend API LMS (StudySpace) project")
                        .title("Backend API StudySpace App")
                        .build());
    }

}
