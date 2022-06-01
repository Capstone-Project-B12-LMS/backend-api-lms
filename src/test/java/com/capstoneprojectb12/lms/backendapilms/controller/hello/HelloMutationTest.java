package com.capstoneprojectb12.lms.backendapilms.controller.hello;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.hello.HelloMutation;
import com.capstoneprojectb12.lms.backendapilms.entities.responses.SayHelloResponse;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@Tag(value = "helloMutation")
public class HelloMutationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired

    @MockBean
    private HelloMutation helloMutation;

    @Test
    public void sayHelloMutation() throws Exception {
        when(this.helloMutation.sayHello(any()))
                .thenReturn(SayHelloResponse.builder()
                        .message("oioi")
                        .errors(null)
                        .build());

        // success
        String query = "{ \"query\": \"mutation { hello { sayHello(to: { \\\"name\\\": \\\"irda islakhu afa\\\"} { message errors} } }\" }";
        this.mockMvc.perform(post("/graphql")
                .contentType(MediaType.APPLICATION_GRAPHQL_VALUE)
                .content(query)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // failed/badrequest
        query = "{ \"query\": \"mutation { hello { sayHello(to: \"irda\") } }\" }";
        this.mockMvc.perform(post("/graphql")
                .contentType(MediaType.APPLICATION_GRAPHQL_VALUE)
                .content(query)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}