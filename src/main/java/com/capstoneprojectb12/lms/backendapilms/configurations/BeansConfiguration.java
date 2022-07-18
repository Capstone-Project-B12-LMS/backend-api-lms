package com.capstoneprojectb12.lms.backendapilms.configurations;

import dev.ditsche.mailo.config.MailoConfig;
import dev.ditsche.mailo.config.SmtpConfig;
import dev.ditsche.mailo.provider.MailProvider;
import dev.ditsche.mailo.provider.SmtpMailProvider;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value(value = "${mail.mailo.app.id}")
	private String mailoAppId;
	@Value(value = "${mail.mailo.secret.key}")
	private String mailoSecretKey;
	@Value(value = "${mail.smtp.host}")
	private String mailSmtpHost;
	@Value(value = "${mail.smtp.port}")
	private int mailSmtpPort;
	@Value(value = "${mail.smtp.user}")
	private String mailSmtpUser;
	@Value(value = "${mail.smtp.password}")
	private String mailSmtpPassword;
	
	@Bean
	public MailProvider mailProvider() {
		MailoConfig mailoConfig = MailoConfig.get();
		mailoConfig.setMjmlAppId(this.mailoAppId);
		mailoConfig.setMjmlAppSecret(this.mailoSecretKey);
		
		SmtpConfig smtpConfig = new SmtpConfig();
		smtpConfig.setHost(mailSmtpHost);
		smtpConfig.setPort(mailSmtpPort);
		smtpConfig.setUsername(mailSmtpUser);
		smtpConfig.setPassword(mailSmtpPassword);
		smtpConfig.setSsl(false);
		
		return new SmtpMailProvider(smtpConfig);
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
