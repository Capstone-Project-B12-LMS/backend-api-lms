package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.RoleRepository;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
	}
	
	@Test
	public void testFindById() {
		when(this.roleRepository.findById(anyString())).thenReturn(Optional.empty());
		
		when(this.roleRepository.findById(anyString())).thenReturn(Optional.of(RoleServiceTest.role));
	}
	
	@Test
	public void testUpdate() {
		when(this.roleRepository.save(any(Role.class))).thenReturn(role);
		when(this.roleRepository.save(any(Role.class))).thenThrow(NullPointerException.class);
	}
	
	@Test
	public void testDeleteById() {
		when(this.roleRepository.findById(anyString())).thenReturn(Optional.of(role));
		
		when(this.roleRepository.findById(anyString())).thenReturn(Optional.empty());
	}
	
	@Test
	public void tstFindAllWithPageable() {
	}
	
	@Test
	public void tstFindAllWithPageableAndSort() {
	
	}
	
}