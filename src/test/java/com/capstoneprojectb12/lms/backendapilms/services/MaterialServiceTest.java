package com.capstoneprojectb12.lms.backendapilms.services;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Material;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Tag(value = "materialServiceTest")
public class MaterialServiceTest {
    private final Material material = Material.builder()

            .build();
}
