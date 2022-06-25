package com.capstoneprojectb12.lms.backendapilms.configurations.graphql;

import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLScalarsConfiguration {
	// register scalar object
	@Bean
	public RuntimeWiringConfigurer objectScalar() {
		return (runtime) -> runtime.scalar(ExtendedScalars.Object);
	}
	
	@Bean
	public RuntimeWiringConfigurer dateScalar() {
		return (runtime) -> runtime.scalar(ExtendedScalars.Date);
	}
}
