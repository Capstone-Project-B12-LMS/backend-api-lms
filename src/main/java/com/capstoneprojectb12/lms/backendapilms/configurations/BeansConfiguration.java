package com.capstoneprojectb12.lms.backendapilms.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class BeansConfiguration {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public Docket docket() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select()
//				.apis(RequestHandlerSelectors.basePackage(InitRestFullAPI.class.getPackageName()))
//				.paths(PathSelectors.ant("/restapi/**"))
//				.build()
//				.apiInfo(new ApiInfoBuilder()
//						.version("1.0")
//						.description("This is Rest API documentation for Backend API LMS (StudySpace) project")
//						.title("Backend API StudySpace App")
//						.build());
//	}
	
	@Bean
	public GroupedOpenApi openApi() {
		return GroupedOpenApi.builder()
				.group("RestAPI")
				.pathsToMatch("/restapi/**")
				.displayName("Rest FULL API")
				.build();
	}
	
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Backend API StudySpace App")
						.description("This is Rest API documentation for Backend API LMS (StudySpace) project")
						.version("1.0")
				);
	}

//	@Bean
//	public SimpleDateFormat simpleDateFormat() {
//		return new SimpleDateFormat(FinalVariable.DATE_FORMAT);
//	}
}
