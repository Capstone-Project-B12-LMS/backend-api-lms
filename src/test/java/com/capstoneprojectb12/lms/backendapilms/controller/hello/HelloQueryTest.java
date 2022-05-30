package com.capstoneprojectb12.lms.backendapilms.controller.hello;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import lombok.AllArgsConstructor;

@SpringBootTest
@AutoConfigureMockMvc
@Tag(value = "helloQuery")
public class HelloQueryTest {

    @Autowired
    private MockMvc mockMvc;

    @AllArgsConstructor
    private class Query {
        public String query;
    }

    private class Response {
        Object data;
    }

    @Test
    public void sayHelloQuery() throws Exception {

        // success
        var response = this.mockMvc.perform(
                post("/graphql")
                        .contentType(MediaType.APPLICATION_GRAPHQL_VALUE)
                        .content("{ \"query\": \"query { hello { sayHello } }\" }")
                        .accept(MediaType.APPLICATION_JSON))
                // .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();

        assertNotNull(response);
        assertEquals(200, response.getStatus());

        // failed/bad request
        response = this.mockMvc.perform(
                post("/graphql-failed")
                        .contentType(MediaType.APPLICATION_GRAPHQL_VALUE)
                        .content("{ \"query\": \"query { hello { sayHello } }\" }")
                        .accept(MediaType.APPLICATION_JSON))
                // .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse();

        assertNotNull(response);
        assertEquals(404, response.getStatus());
    }
}
