package com.capstoneprojectb12.lms.backendapilms.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassNew;
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
    private final Class classEntity2 = Class.builder()
            .id("id")
            .name("ny class")
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

    @Test
    public void testDeleteById() {
        // success
        when(this.classRepository.existsById(anyString())).thenReturn(true);
        var result = this.classService.deleteById("id");
        assertTrue(result);

        // failed
        when(this.classRepository.existsById(anyString())).thenReturn(false);
        result = this.classService.deleteById("id");
        assertFalse(result);
    }

    @Test
    public void testFindById() {
        // success
        when(this.classRepository.findById(anyString())).thenReturn(Optional.ofNullable(classEntity));
        var result = this.classService.findById("id");
        assertTrue(result.isPresent());
        assertEquals(classEntity.getCode(), result.get().getCode());

        // failed
        when(this.classRepository.findById(anyString())).thenReturn(Optional.empty());
        result = this.classService.findById("id");
        assertFalse(result.isPresent());
    }

    @Test
    public void testFindAll() {
        when(this.classRepository.findAll()).thenReturn(List.of(classEntity));
        var result = this.classService.findAll();
        assertNotNull(result);
        assertEquals(classEntity.getCode(), result.get(0).getCode());
    }

    @Test
    public void testFindAllWithPageable() {
        // without sorting
        // success
        when(this.classRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(classEntity)));
        var result = this.classService.findAll(0, 1);
        assertNotNull(result);
        assertEquals(List.of(classEntity), result.getContent());

        // failed
        when(this.classRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));
        result = this.classService.findAll(0, 1);
        assertNotNull(result);
        assertEquals(Collections.emptyList(), result.getContent());

    }

    @Test
    public void testFindAllWithSort() {
        // with sorting
        // ascending
        when(this.classRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(classEntity, classEntity2)));
        var result = this.classService.findAll(0, 1, Sort.by("name").ascending());
        assertNotEquals(Collections.emptyList(), result.getContent());
        assertEquals(classEntity, result.getContent().get(0));
        assertEquals(classEntity2, result.getContent().get(1));

        // descending
        when(this.classRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(classEntity2, classEntity)));
        result = this.classService.findAll(0, 1, Sort.by("name").descending());
        assertNotEquals(Collections.emptyList(), result.getContent());
        assertEquals(classEntity2, result.getContent().get(0));
        assertEquals(classEntity, result.getContent().get(1));

    }

    @Test
    public void testToPaginationResponse() {
        var purePage = new PageImpl<>(List.of(classEntity), PageRequest.of(0, 1), 1);
        var pageResponse = this.classService.toPaginationResponse(purePage);
        assertEquals(0, pageResponse.getPage());
        assertEquals(1, pageResponse.getSize());
        assertEquals(1, pageResponse.getTotalPage());
        assertEquals(1, pageResponse.getTotalSize());
        assertEquals(purePage.getContent(), pageResponse.getData());
    }

    @Test
    public void testToEntity() {
        var classNew = ClassNew.builder()
                .name("new class")
                .room("rom of new class")
                .build();

        var entity = this.classService.toEntity(classNew);
        assertEquals(classNew.getName(), entity.getName());
        assertEquals(classNew.getRoom(), entity.getRoom());
    }
}
