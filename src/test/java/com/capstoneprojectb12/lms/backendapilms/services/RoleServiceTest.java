package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.RoleRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@Tag(value = "roleServiceTest")
public class RoleServiceTest {
	
	public static final Role role = Role.builder()
			.id("roleId")
			.name("USER")
			.description("-")
			.build();
	@MockBean
	private RoleRepository roleRepository;
	@Autowired
	private RoleService roleService;
	
	@Test
	public void testSave() {
		when(this.roleRepository.save(any())).thenReturn(role);
		var savedRole = this.roleService.save(role);
		assertNotNull(savedRole);
		assertTrue(savedRole.isPresent());
		assertEquals("USER", role.getName());
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
		
		when(this.roleRepository.save(any(Role.class))).thenThrow(NullPointerException.class);
		
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
	
	@Test
	public void tstFindAllWithPageable() {
		when(this.roleRepository.findAll(PageRequest.of(0, 2)))
				.thenReturn(new PageImpl<>(List.of(role), PageRequest.of(0, 2), 1));
		
		var result = this.roleService.findAll(0, 2);
		assertNotNull(result);
		assertEquals(1, result.getTotalPages());
		assertEquals(0, result.getNumber());
		assertEquals(1, result.getTotalElements());
		assertEquals(List.of(role), result.getContent());
		
		when(this.roleRepository.findAll(PageRequest.of(0, 2)))
				.thenReturn(new PageImpl<>(Collections.emptyList()));
		result = this.roleService.findAll(0, 2);
		assertNotNull(result);
		assertEquals(Collections.emptyList(), result.getContent());
		
	}
	
	@Test
	public void tstFindAllWithPageableAndSort() {
		when(this.roleRepository.findAll(PageRequest.of(0, 2, Sort.by("name").ascending())))
				.thenReturn(new PageImpl<>(List.of(role), PageRequest.of(0, 2), 1));
		
		var result = this.roleService.findAll(0, 2, Sort.by("name").ascending());
		assertNotNull(result);
		assertEquals(1, result.getTotalPages());
		assertEquals(0, result.getNumber());
		assertEquals(1, result.getTotalElements());
		assertEquals(List.of(role), result.getContent());
		
		when(this.roleRepository.findAll(PageRequest.of(0, 2)))
				.thenReturn(new PageImpl<>(Collections.emptyList()));
		result = this.roleService.findAll(0, 2);
		assertNotNull(result);
		assertEquals(Collections.emptyList(), result.getContent());
		
	}
	
}