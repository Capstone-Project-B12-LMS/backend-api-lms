package com.capstoneprojectb12.lms.backendapilms.controllers.rest.user;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserNew;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = OrderAnnotation.class)
public class UserControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @Test
        @Order(1)
        public void registerTestSuccess() throws Exception {
                var body = UserNew.builder()
                                .email("teacher@gmail.com")
                                .fullName("teacher")
                                .password("teacher")
                                .build();

                var requestBody = (new ObjectMapper()).valueToTree(body).toString();

                this.mockMvc.perform(
                                MockMvcRequestBuilders.post("/restapi/register")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .accept(MediaType.APPLICATION_JSON)
                                                .content(requestBody))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        }

        @Test
        public void registerBadRequest() throws Exception {
                var body = UserNew.builder()
                                .email("teachergmail.com")
                                .fullName("teacher")
                                .password("teacher")
                                .build();
                var requestBody = (new ObjectMapper()).valueToTree(body).toString();

                this.mockMvc.perform(
                                MockMvcRequestBuilders.post("/restapi/register")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .accept(MediaType.APPLICATION_JSON)
                                                .content(requestBody))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        }

        @Test
        public void registerInternalServerError() throws Exception {
                var body = UserNew.builder()
                                .email("teacher@gmail.com")
                                .fullName("teacher")
                                .password("teacher")
                                .build();
                var requestBody = (new ObjectMapper()).valueToTree(body).toString();

                // this.mockMvc.perform(
                // MockMvcRequestBuilders.post("/restapi/register")
                // .contentType(MediaType.APPLICATION_JSON)
                // .accept(MediaType.APPLICATION_JSON)
                // .content(requestBody))
                // .andExpect(MockMvcResultMatchers.status().isOk())
                // .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

                this.mockMvc.perform(
                                MockMvcRequestBuilders.post("/restapi/register")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .accept(MediaType.APPLICATION_JSON)
                                                .content(requestBody))
                                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        }
}
