package com.capstoneprojectb12.lms.backendapilms.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassUpdate;
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
    private final ClassUpdate classUpdate = ClassUpdate.builder()
            .name("updated class")
            .room("updated room")
            .status(ClassStatus.WILL_END)
            .build();
    private final Class updatedClass = Class.builder()
            .id("updated id")
            .name(classUpdate.getName())
            .room(classUpdate.getRoom())
            .status(classUpdate.getStatus())
            .code(classEntity.getCode())
            .users(classEntity.getUsers())
            .build();

    @Test
    public void testSave() {
        // success
        when(this.classRepository.save(any(Class.class))).thenReturn(classEntity);
        var result = this.classService.save(classEntity);
        assertTrue(result.isPresent());
        assertEquals(classEntity, result.get());
        assertEquals(classEntity.getStatus(), result.get().getStatus());

        // failed
        when(this.classRepository.save(any(Class.class))).thenReturn(nullable(Class.class));
        result = this.classService.save(classEntity);
        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdate() {
        // success
        var tempName = classEntity.getName();
        classEntity.setName("updated name");
        when(this.classRepository.save(any(Class.class))).thenReturn(classEntity);
        var result = this.classService.update(classEntity);
        assertTrue(result.isPresent());
        assertEquals("updated name", result.get().getName());

        // failed
        when(this.classRepository.save(any(Class.class))).thenReturn(nullable(Class.class));
        result = this.classService.update(classEntity);
        assertFalse(result.isPresent());

        // update with class update
        when(this.classRepository.save(any(Class.class))).thenReturn(updatedClass);
        result = this.classService.update(classEntity, classUpdate);
        assertTrue(result.isPresent());
        assertEquals(updatedClass.getName(), result.get().getName());
        assertEquals(updatedClass.getStatus(), result.get().getStatus());
        assertEquals(updatedClass.getRoom(), result.get().getRoom());

    }

}
