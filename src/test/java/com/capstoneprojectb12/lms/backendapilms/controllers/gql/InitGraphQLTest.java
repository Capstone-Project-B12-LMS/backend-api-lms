package com.capstoneprojectb12.lms.backendapilms.controllers.gql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InitGraphQLTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testGraphqlEndpoint() throws Exception {
		this.mockMvc.perform(get("/gql/v1/graphiql?path=/gql/v1/graphql"))
				.andExpect(status().isOk())
				.andDo(print());
	}
}
