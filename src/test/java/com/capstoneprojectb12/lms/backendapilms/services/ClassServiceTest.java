package com.capstoneprojectb12.lms.backendapilms.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.ClassStatus;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.ClassRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Tag(value = "classServiceTest")
public class ClassServiceTest {

    @MockBean
    private ClassRepository classRepository;

    @Autowired
    private ClassService classService;

    private final Class classEntity = Class.builder()
            .id("id")
            .name("my class")
            .room("my room")
            .code("wkwkwk")
            .status(ClassStatus.ACTIVE)
            .users(Collections.emptyList())
            .build();

    @Test
    public void testSave() {
        when(this.classRepository.save(any(Class.class))).thenReturn(classEntity);
        var result = this.classService.save(classEntity);
        assertTrue(result.isPresent());
        assertEquals(classEntity, result.get());
        assertEquals(classEntity.getStatus(), result.get().getStatus());
    }
}
