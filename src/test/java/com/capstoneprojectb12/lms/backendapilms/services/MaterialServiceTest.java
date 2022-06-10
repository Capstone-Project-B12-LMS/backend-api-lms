package com.capstoneprojectb12.lms.backendapilms.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.capstoneprojectb12.lms.backendapilms.models.repositories.MaterialRepository;

@SpringBootTest
@Tag(value = "materialServiceTest")
public class MaterialServiceTest {
    @MockBean
    private MaterialRepository materialRepository;

    @Test
    public void testAssertNotNull() {
        assertNotNull(materialRepository);
    }
}
