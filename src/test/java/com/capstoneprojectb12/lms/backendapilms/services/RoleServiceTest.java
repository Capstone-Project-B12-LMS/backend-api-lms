package com.capstoneprojectb12.lms.backendapilms.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.RoleRepository;

@SpringBootTest
@Tag(value = "RoleServiceTest")
public class RoleServiceTest {

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    private static final Role role = Role.builder()
            .id("roleId")
            .name("TEACHER")
            .description("-")
            .build();

    @Test
    public void testSave() {
        when(this.roleRepository.save(any())).thenReturn(role);
        var savedRole = this.roleService.save(role);
        assertNotNull(savedRole);
        assertTrue(savedRole.isPresent());
        assertEquals("TEACHER", role.getName());
    }
}
