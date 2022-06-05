package com.capstoneprojectb12.lms.backendapilms.configurations.graphql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.scalars.ExtendedScalars;

@Configuration
public class GraphQLScalarsConfiguration {
    // register scalar object
    @Bean
    public RuntimeWiringConfigurer objectScalar() {
        return (runtime) -> runtime.scalar(ExtendedScalars.Object);
    }
}
