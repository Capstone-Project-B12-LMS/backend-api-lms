package com.capstoneprojectb12.lms.backendapilms.controllers.rest.user;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.capstoneprojectb12.lms.backendapilms.controllers.rest.utils.Constant;
import com.capstoneprojectb12.lms.backendapilms.controllers.rest.utils.JSON;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserLogin;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserNew;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(value = OrderAnnotation.class)
@Tag(value = "userControllerTest")
public class UserControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private UserService userService;

        @InjectMocks
        private UserController userController;

        private static final UserNew userNew = UserNew.builder()
                        .fullName("irda islakhu afa")
                        .email("myemail@gmail.com")
                        .password("mypass")
                        .telepon("1234567890")
                        .build();

        @Test
        @Order(1)
        public void registerTestSuccess() throws Exception {
                var requestBody = JSON.create(userNew);

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
                var requestBody = JSON.create(userNew);

                this.mockMvc.perform(post(Constant.BASE_URL + "/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andDo(print())
                                .andExpect(status().isBadRequest())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        }

        @Test
        @Order(3)
        @Disabled
        public void registerInternalServerError() throws Exception {
                var body = userNew;
                body.setEmail("teacher@gmail.com");

                var requestBody = (Constant.getMapper()).valueToTree(body).toString();
                this.mockMvc.perform(post(Constant.BASE_URL + "/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andDo(print())
                                .andExpect(status().isInternalServerError())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        }

        @Test
        public void testLoginSuccess() throws Exception {
                var user = UserLogin.builder()
                                .email("myemail@gmail.com")
                                .password("mypass")
                                .build();
                var request = JSON.create(user);

                this.mockMvc.perform(post(Constant.BASE_URL + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(request))
                                .andDo(print())
                                .andExpect(status().isOk())
                //
                ;
        }

        @Test
        public void testLoginUserNotFound() throws Exception {
                var user = UserLogin.builder()
                                .email("myemail@gmail.commmm")
                                .password("mypass")
                                .build();
                var request = JSON.create(user);

                this.mockMvc.perform(post(Constant.BASE_URL + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(request))
                                .andDo(print())
                                .andExpect(status().isBadRequest())
                //
                ;
        }

        @Test
        public void testLoginValidationError() throws Exception {
                var user = UserLogin.builder()
                                .email("myemail@gmail.commmm")
                                .build();
                var request = JSON.create(user);

                this.mockMvc.perform(post(Constant.BASE_URL + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(request))
                                .andDo(print())
                                .andExpect(status().isBadRequest())
                //
                ;
        }

        @Test
        public void testLoginPasswordNotMatch() throws Exception {
                var user = UserLogin.builder()
                                .email("myemail@gmail.com")
                                .password("null")
                                .build();
                var request = JSON.create(user);

                this.mockMvc.perform(post(Constant.BASE_URL + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(request))
                                .andDo(print())
                                .andExpect(status().isBadRequest())
                //
                ;
        }

        @Test
        @Disabled // TODO: How to mock service layer in controller layer
        public void testLoginInternalServerError() throws Exception {
                when(this.userService.findByEmail(anyString())).thenThrow(NoSuchElementException.class);
                var user = UserLogin.builder()
                                .email("myemail@gmail.com")
                                .password("null")
                                .build();
                var request = JSON.create(user);
                this.mockMvc.perform(post(Constant.BASE_URL + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(request))
                                .andDo(print())
                                .andExpect(status().isInternalServerError())
                //
                ;
        }
}
