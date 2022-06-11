package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.MaterialRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Material;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Tag(value = "materialServiceTest")
public class MaterialServiceTest {

    @MockBean
    private MaterialRepository materialRepository;

    private final Material material = Material.builder()
            .id("id")
            .classes(ClassServiceTest.classEntity)
            .title("material title")
            .content("material content")
            .topic(null)
            .videoUri("url")
            .fileUrl("url")
            .point(100)
            .category(null)
            .build();

    @Test
    public void testNotNull(){
        Assertions.assertNotNull(materialRepository);
    }

}
