package com.capstoneprojectb12.lms.backendapilms.controllers.rest.classes;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.*;
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
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.ClassStatus;
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

        private Class savedClass = null;

        private final ObjectMapper objectMapper = new ObjectMapper();

        @BeforeEach
        public void first() {

        }

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

        @Test
        @Disabled
        public void testUpdate() throws Exception {

                var tempClass = this.classEntity;
                tempClass.setName("my class name");
                when(this.classService.findById("classId")).thenReturn(Optional.of(classEntity));
                when(this.classService.save(any())).thenReturn(Optional.of(tempClass));

                savedClass = this.classService.save(classEntity).get();
                var request = this.objectMapper.valueToTree(classUpdate).toString();
                var response = this.objectMapper.valueToTree(ApiResponse.responseOk(tempClass)).toString();
                this.mockMvc.perform(
                                MockMvcRequestBuilders.put("/restapi/class/updateBy/id/" + savedClass.getId())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(request))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andDo(MockMvcResultHandlers.print())
                // .andExpect(MockMvcResultMatchers.content().json(response))
                //
                ;
        }
}
