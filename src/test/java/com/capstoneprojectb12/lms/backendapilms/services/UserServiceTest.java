package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.Beans;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.UserNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.getResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@SpringBootTest
@Tag(value = "userServiceTest")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	public static final User user = User.builder()
			.id("id")
			.fullName("irda islakhu afa")
			.email("myemail@gmail.com")
			.telepon("0987654321")
			.roles(List.of(RoleServiceTest.role))
			.password("mypass")
			.build();
	private static final UserNew userNew = UserNew.builder()
			.fullName(user.getFullName())
			.email(user.getEmail())
			.password(user.getPassword())
			.telepon(user.getTelepon())
			.build();
	private static final UserUpdate userUpdate = UserUpdate.builder()
			.fullName(user.getFullName())
			.email(user.getEmail())
			.telepon(user.getTelepon())
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
	public void testSave() {
//		success
		var realPassword = user.getPassword();
		user.setPassword(Beans.getPasswordEncoder().encode(realPassword));
		when(this.userRepository.save(any())).thenReturn(user);
		var result = this.userService.save(userNew);
		var savedUser = (User) Objects.requireNonNull(getResponse(result).getData());
		
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(user.getFullName(), savedUser.getFullName());
		assertEquals(user.getEmail(), savedUser.getEmail());
		assertEquals(user.getTelepon(), savedUser.getTelepon());
		assertNotNull(savedUser.getPassword());

//		already exists
		reset(this.userRepository);
		when(this.userRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
		result = this.userService.save(userNew);
		savedUser = (User) getResponse(result).getData();
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		assertNull(savedUser);

//		runtime error
		reset(this.userRepository);
		when(this.userRepository.save(any(User.class))).thenThrow(RuntimeException.class);
		result = this.userService.save(userNew);
		savedUser = (User) getResponse(result).getData();
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
		assertNull(savedUser);
	}
	
	@Test
	public void testUpdate() {
//		success
		when(this.userRepository.findById(anyString())).thenReturn(Optional.of(user));
		when(this.userRepository.save(any(User.class))).thenReturn(user);
		var response = this.userService.update("id", userUpdate);
		var apiRes = getResponse(response);
		var updatedUser = (User) apiRes.getData();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(apiRes.isStatus());
		assertNull(apiRes.getErrors());
		
		assertEquals(user.getEmail(), updatedUser.getEmail());
		assertEquals(user.getFullName(), updatedUser.getFullName());
		assertEquals(user.getTelepon(), updatedUser.getTelepon());
		assertEquals(user.getPassword(), updatedUser.getPassword());
		reset(this.userRepository);

//		user not found
		when(this.userRepository.findById(anyString())).thenThrow(UserNotFoundException.class);
		response = this.userService.update("id", userUpdate);
		apiRes = getResponse(response);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertFalse(apiRes.isStatus());
		assertNotNull(apiRes.getErrors());
		assertNull(apiRes.getData());
		reset(this.userRepository);

//		any exception
		when(this.userRepository.findById(anyString())).thenThrow(NoSuchElementException.class);
		response = this.userService.update("id", userUpdate);
		apiRes = getResponse(response);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertFalse(apiRes.isStatus());
		assertNotNull(apiRes.getErrors());
		assertNull(apiRes.getData());
		
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
