package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.Beans;
import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@Tag(value = "userServiceTest")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	public static final User user = User.builder()
			.fullName("irda islakhu afa")
			.email("myemail@gmail.com")
			.roles(List.of(RoleServiceTest.role))
			.password("mypass")
			.build();
	@MockBean
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	
	@Test
	public void testLoadByUsername() {
		// success
		when(this.userRepository.findByEmailEqualsIgnoreCase(anyString())).thenReturn(Optional.of(user));
		var result = this.userService.loadUserByUsername("myemail@gmail.com");
		assertTrue(result.getUsername().equalsIgnoreCase(user.getUsername()));
		
		// exception
		when(this.userRepository.findByEmailEqualsIgnoreCase(anyString())).thenReturn(Optional.empty());
		assertThrows(UsernameNotFoundException.class, () -> this.userService.loadUserByUsername("myemail@gmail.com"));
	}
	
	@Test
	public void testFindById() {
		when(this.userRepository.findById(anyString())).thenReturn(Optional.of(user));
		var result = this.userService.findById("id");
		assertTrue(result.isPresent());
	}
	
	@Test
	public void testSaveSuccess() {
		var realPassword = user.getPassword();
		var tempUser = user;
		// tempUser.setPassword(Beans.getPasswordEncoder().encode(tempUser.getPassword()));
		when(this.userRepository.save(any())).thenReturn(tempUser);
		
		var result = this.userService.save(user);
		assertTrue(result.isPresent());
		System.out.println("result : " + result.get().getPassword() + " password: " + realPassword);
		System.out.println("result : " + result.get().getPassword() + " password: " + realPassword);
		assertTrue(Beans.getPasswordEncoder().matches(realPassword, result.get().getPassword()));
	}
	
	@Test
	public void testUpdateSuccess() {
		when(this.userRepository.save(any(User.class))).thenReturn(user);
		var result = this.userService.save(user);
		assertTrue(result.isPresent());
		assertEquals(user.getEmail(), result.get().getEmail());
	}
	
	@Test
	public void testDeleteById() {
		when(this.userRepository.findById(anyString())).thenReturn(Optional.of(user));
		var result = this.userService.deleteById("id");
		assertTrue(result);
		
		when(this.userRepository.findById(anyString())).thenReturn(Optional.empty());
		result = this.userService.deleteById("id");
		assertFalse(result);
	}
	
	@Test
	public void testExistsbyId() {
		when(this.userRepository.existsById(anyString())).thenReturn(true);
		assertTrue(this.userService.existsById("id"));
	}
	
	@Test
	public void testFindAll() {
		when(this.userService.findAll()).thenReturn(List.of(user));
		var result = this.userService.findAll();
		assertEquals(1, result.size());
		assertEquals(user, result.get(0));
	}
	
}
