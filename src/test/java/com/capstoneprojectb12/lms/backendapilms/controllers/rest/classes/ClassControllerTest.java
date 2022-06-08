package com.capstoneprojectb12.lms.backendapilms.controllers.rest.classes;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassNew;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.services.ClassService;
import com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@Tag(value = "classControllerTest")
@AutoConfigureMockMvc
public class ClassControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClassService classService;

    private final Class classEntity = Class.builder()
            .id("id")
            .name("my class")
            .room("my room")
            .code("mycode")
            .build();

    private final ClassNew classNew = ClassNew.builder()
            .name("my class")
            .room("my room")
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testSave() throws Exception {
        when(this.classService.save(null)).thenReturn(Optional.of(classEntity));

        var request = this.objectMapper.valueToTree(classNew).toString();
        var response = this.objectMapper.valueToTree(ApiResponse.success(classEntity)).toString();
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/restapi/class")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json(response))
        //
        ;
    }
}
