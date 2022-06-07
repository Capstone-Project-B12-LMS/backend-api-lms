package com.capstoneprojectb12.lms.backendapilms.controllers.user;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserNew;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registerTestSuccess() throws Exception {
        var body = UserNew.builder()
                .email("teacher@gmail.com")
                .fullName("teacher")
                .password("teacher")
                .roles(List.of("teacher"))
                .build();

        var requestBody = (new ObjectMapper()).valueToTree(body).toString();

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/restapi/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void registerBadRequest() throws Exception {
        var body = UserNew.builder()
                .email("teachergmail.com")
                .fullName("teacher")
                .password("teacher")
                .roles(List.of("teacher"))
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
                .roles(List.of("teacher"))
                .build();
        var requestBody = (new ObjectMapper()).valueToTree(body).toString();

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/restapi/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/restapi/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }
}
