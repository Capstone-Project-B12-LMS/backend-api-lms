package com.capstoneprojectb12.lms.backendapilms.controllers;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@Tag(value = "swaggerDocsTest")
@AutoConfigureMockMvc
public class SwaggerDocsTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void swaggerEndpointTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/restapi/docs/swagger-ui/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}
