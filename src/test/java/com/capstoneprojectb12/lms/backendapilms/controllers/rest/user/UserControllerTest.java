package com.capstoneprojectb12.lms.backendapilms.controllers.rest.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.capstoneprojectb12.lms.backendapilms.controllers.rest.utils.Constant;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserNew;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = OrderAnnotation.class)
@Tag(value = "userControllerTest")
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

                var requestBody = (Constant.getMapper()).valueToTree(body).toString();

                this.mockMvc.perform(post(Constant.BASE_URL + "/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(status().isOk())
                                .andDo(print())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        }

        @Test
        @Order(2)
        public void registerBadRequest() throws Exception {
                var body = UserNew.builder()
                                .email("teachergmail.com")
                                .fullName("teacher")
                                .password("teacher")
                                .build();
                var requestBody = (Constant.getMapper()).valueToTree(body).toString();

                this.mockMvc.perform(post(Constant.BASE_URL + "/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(status().isBadRequest())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        }

        @Test
        @Order(3)
        @Disabled
        public void registerInternalServerError() throws Exception {
                var body = UserNew.builder()
                                .email("teacher@gmail.com")
                                .fullName("teacher")
                                .password("teacher")
                                .build();
                var requestBody = (Constant.getMapper()).valueToTree(body).toString();
                this.mockMvc.perform(post(Constant.BASE_URL + "/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(status().isInternalServerError())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        }

        @Test
        public void testRegisterSuccess() throws Exception {
                var user = UserNew.builder()
                                .fullName("irda islakhu afa")
                                .email("myemail@gmail.com")
                                .password("mypass")
                                .telepon("1234567890")
                                .build();
                var request = Constant.getMapper().valueToTree(user).toString();
                this.mockMvc.perform(post(Constant.BASE_URL + "/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(request))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andDo(print());
        }
}
