package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
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
//		success
		when(this.userRepository.findById(anyString())).thenReturn(Optional.of(user));
		var result = this.userService.findById("id");
		var apiRes = (ApiResponse<?>) result.getBody();
		var findedUser = (User) apiRes.getData();
		
		assertTrue(result.getStatusCode().is2xxSuccessful());
		assertEquals(user.getFullName(), findedUser.getFullName());
		assertEquals(user.getEmail(), findedUser.getEmail());
		assertEquals(user.getRoles(), findedUser.getRoles());
		assertEquals(user.getPassword(), findedUser.getPassword());

//		data not found
		when(this.userRepository.findById(anyString())).thenReturn(Optional.empty());
		result = this.userService.findById("id");
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

//		runtime exception
		when(this.userRepository.findById(anyString())).thenThrow(NoSuchElementException.class);
		result = this.userService.findById("id");
		assertEquals(result.getStatusCodeValue(), 500);
	}
	
	@Test
	public void testSaveSuccess() {
		var realPassword = user.getPassword();
		// tempUser.setPassword(Beans.getPasswordEncoder().encode(tempUser.getPassword()));
		when(this.userRepository.save(any())).thenReturn(user);

//		assertTrue(result.isPresent());
//		System.out.println("result : " + result.get().getPassword() + " password: " + realPassword);
//		System.out.println("result : " + result.get().getPassword() + " password: " + realPassword);
//		assertTrue(Beans.getPasswordEncoder().matches(realPassword, result.get().getPassword()));
	}
	
	@Test
	public void testUpdateSuccess() {
		when(this.userRepository.save(any(User.class))).thenReturn(user);
		
	}
	
	@Test
	public void testDeleteById() {
	}
	
	@Test
	public void testExistsbyId() {
	}
	
	@Test
	public void testFindAll() {
	}
	
}
