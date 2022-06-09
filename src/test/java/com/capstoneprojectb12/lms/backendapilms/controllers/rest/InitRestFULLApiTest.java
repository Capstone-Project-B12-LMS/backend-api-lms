package com.capstoneprojectb12.lms.backendapilms.controllers.rest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.hello.SayHelloInput;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@Tag(value = "initRestTest")
@AutoConfigureMockMvc
public class InitRestFULLApiTest {
        @Autowired
        private MockMvc mockMvc;

        @Test
        public void initRestTest() throws Exception {
                SayHelloInput body = SayHelloInput.builder().name("auuu").build();
                String requestBody = new ObjectMapper().valueToTree(body).toString();
                this.mockMvc
                                .perform(MockMvcRequestBuilders
                                                .post("/restapi")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(requestBody))
                                .andDo(MockMvcResultHandlers
                                                .print())
                                .andExpect(MockMvcResultMatchers
                                                .status().isOk());
        }
}
