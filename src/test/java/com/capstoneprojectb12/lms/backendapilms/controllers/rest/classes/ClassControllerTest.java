package com.capstoneprojectb12.lms.backendapilms.controllers.rest.classes;

import static com.capstoneprojectb12.lms.backendapilms.controllers.rest.user.UserControllerTest.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.capstoneprojectb12.lms.backendapilms.controllers.rest.utils.Constant;
import com.capstoneprojectb12.lms.backendapilms.controllers.rest.utils.JSON;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserLogin;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.ClassStatus;
import com.capstoneprojectb12.lms.backendapilms.services.ClassService;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;
import com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(value = OrderAnnotation.class)
// @Tag(value = "userControllerTest")
public class ClassControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ClassService classService;

        @MockBean
        private UserService userService;

        private final Class classEntity = Class.builder()
                        .id("classId")
                        .name("my class")
                        .room("my room")
                        .code("mycode")
                        .status(ClassStatus.ACTIVE)
                        .build();

        private final ClassNew classNew = ClassNew.builder()
                        .name("my class")
                        .room("my room")
                        .build();

        private final ClassUpdate classUpdate = ClassUpdate.builder()
                        .name(this.classNew.getName())
                        .room(this.classNew.getRoom())
                        .status(ClassStatus.INACTIVE)
                        .build();

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Test
        @Order(1)
        public MvcResult testLoginSuccess() throws Exception {
                var user = UserLogin.builder()
                                .email("myemail@gmail.com")
                                .password("mypass")
                                .build();
                var request = JSON.create(user);

                when(this.userService.findByEmail(any(String.class))).thenReturn(Optional.of(userEntity));
                when(this.userService.loadUserByUsername(anyString())).thenReturn(userEntity);
                var result = this.mockMvc.perform(post(Constant.BASE_URL + "/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(request))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andReturn()
                //
                ;
                return result;
        }

        @Test
        @Disabled
        public void testSave() throws Exception {
                when(this.classService.save(null)).thenReturn(Optional.of(classEntity));
                var a = testLoginSuccess();
                var request = JSON.create(userNew);
                var response = JSON.create(ApiResponse.success(classEntity));
                this.mockMvc.perform(post(Constant.BASE_URL + "/class")
                                .content(request)
                                .header("Authorization", "Bearer "
                                                + JSON.create(a))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andDo(print())
                                .andExpect(content().json(response))
                //
                ;
        }

        @Test
        @Disabled
        public void testUpdate() throws Exception {

                var tempClass = this.classEntity;
                // tempClass.setName("my class name");
                when(this.classService.findById(anyString())).thenReturn(Optional.of(classEntity));
                when(this.classService.save(any())).thenReturn(Optional.of(classEntity));

                // save first
                this.mockMvc.perform(post(Constant.BASE_URL + "/class")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer "
                                                + JSON.create(this.testLoginSuccess()))
                                .accept(MediaType.APPLICATION_JSON)
                                .content(JSON.create(classNew)))
                                .andDo(print())
                                .andExpect(status().isOk());

                var request = JSON.create(classUpdate);
                this.mockMvc.perform(put(Constant.BASE_URL + "/class/" + UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer "
                                                + JSON.create(this.testLoginSuccess()))
                                .accept(MediaType.APPLICATION_JSON)
                                .content(request))
                                .andExpect(status().isBadRequest()) // TODO: must ok
                                .andDo(print())
                // .andExpect(MockMvcResultMatchers.content().json(response))
                //
                ;
        }
}
