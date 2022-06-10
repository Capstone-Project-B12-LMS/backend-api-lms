package com.capstoneprojectb12.lms.backendapilms.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.RoleRepository;

@SpringBootTest
@Tag(value = "roleServiceTest")
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

    @Test
    public void testFindById() {
        when(this.roleRepository.findById(anyString())).thenReturn(Optional.empty());
        var role = this.roleService.deleteById("id");
        assertFalse(role);

        when(this.roleRepository.findById(anyString())).thenReturn(Optional.of(RoleServiceTest.role));
        role = this.roleService.deleteById("id");
        assertTrue(role);
    }

    @Test
    public void testUpdate() {
        when(this.roleRepository.save(any(Role.class))).thenReturn(role);
        var result = this.roleService.update(role);
        assertTrue(result.isPresent());
        assertEquals(role.getName(), result.get().getName());

        when(this.roleRepository.save(any(Role.class))).thenReturn(null);

        assertThrows(NullPointerException.class, () -> this.roleService.update(role));
    }

    @Test
    public void testDeleteById() {
        when(this.roleRepository.findById(anyString())).thenReturn(Optional.of(role));
        var result = this.roleService.deleteById("id");
        assertTrue(result);

        when(this.roleRepository.findById(anyString())).thenReturn(Optional.empty());
        result = this.roleService.deleteById("id");
        assertFalse(result);
    }
}