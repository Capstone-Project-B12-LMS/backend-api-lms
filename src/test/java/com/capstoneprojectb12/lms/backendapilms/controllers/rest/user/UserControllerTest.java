package com.capstoneprojectb12.lms.backendapilms.controllers.rest.user;

import com.capstoneprojectb12.lms.backendapilms.controllers.rest.utils.Constant;
import com.capstoneprojectb12.lms.backendapilms.controllers.rest.utils.JSON;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserLogin;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;
import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;
import java.util.List;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(value = OrderAnnotation.class)
@Tag(value = "userControllerTest")
public class UserControllerTest {
	public static final UserNew userNew = UserNew.builder()
			.fullName("irda islakhu afa")
			.email("myemail@gmail.com")
			.password("mypass")
			.telepon("1234567890")
			.build();
	public static final User userEntity = User.builder()
			.id("id")
			.fullName(userNew.getFullName())
			.email(userNew.getEmail())
			.password(new BCryptPasswordEncoder()
					.encode(userNew.getPassword()))
			.telepon(userNew.getTelepon())
			.roles(List.of(Role.builder()
					.id("id")
					.name("USER")
					.build()))
			.build();
	public static final UserUpdate userUpdate = UserUpdate.builder()
			.fullName("updated name")
			.email("updated@gmail.com")
			.telepon("0987654321")
			.build();
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;
	
	@Test
	@Disabled
	@Order(1)
	public void registerTestSuccess() throws Exception {
		var requestBody = JSON.create(userNew);
		
		this.mockMvc.perform(post(Constant.BASE_URL + "/register")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
	}
	
	@Test
	@Disabled
	@Order(2)
	public void registerBadRequest() throws Exception {
		var tempUser = userNew;
		tempUser.setEmail(null);
		var requestBody = JSON.create(tempUser);
		
		this.mockMvc.perform(post(Constant.BASE_URL + "/register")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
	}
	
	@Test
	@Disabled
	public void testRegisterErrorValidation() throws Exception {
		var userRegister = userNew;
		userRegister.setEmail(null);
		
		var request = JSON.create(userRegister);
		this.mockMvc.perform(post(Constant.BASE_URL + "/register")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(request))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn()
		
		;
	}
	
	@Test
	@Disabled
	public void testRegisterAlreadyExists() throws Exception {
		this.mockMvc.perform(post(Constant.BASE_URL + "/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(JSON.create(userNew))
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@Disabled
	public MvcResult testLoginSuccess() throws Exception {
		var user = UserLogin.builder()
				.email("myemail@gmail.com")
				.password("mypass")
				.build();
		var request = JSON.create(user);
		
		var result = this.mockMvc.perform(post(Constant.BASE_URL + "/login")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(request))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				//
				;
		return result;
	}
	
	@Test
	@Disabled
	public void testLoginUserNotFound() throws Exception {
		var user = UserLogin.builder()
				.email("myemail@gmail.commmm")
				.password("mypass")
				.build();
		var request = JSON.create(user);
		
		this.mockMvc.perform(post(Constant.BASE_URL + "/login")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(request))
				.andDo(print())
				.andExpect(status().isBadRequest())
		//
		;
	}
	
	@Test
	@Disabled
	public void testLoginValidationError() throws Exception {
		var user = UserLogin.builder()
				.email("myemail@gmail.commmm")
				.build();
		var request = JSON.create(user);
		
		this.mockMvc.perform(post(Constant.BASE_URL + "/login")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(request))
				.andDo(print())
				.andExpect(status().isBadRequest())
		//
		;
	}
	
	@Test
	@Disabled
	public void testLoginPasswordNotMatch() throws Exception {
		var user = UserLogin.builder()
				.email("myemail@gmail.com")
				.password("null")
				.build();
		var request = JSON.create(user);
		this.mockMvc.perform(post(Constant.BASE_URL + "/login")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(request))
				.andDo(print())
				.andExpect(status().isBadRequest())
		//
		;
	}
	
	@Test
	@Disabled
	public void testLoginInternalServerError() throws Exception {
		var user = UserLogin.builder()
				.email("myemail@gmail.com")
				.password("null")
				.build();
		var request = JSON.create(user);
		this.mockMvc.perform(post(Constant.BASE_URL + "/login")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(request))
				.andDo(print())
				.andExpect(status().isInternalServerError())
		//
		;
	}
	
	@Test
	@Disabled
	public void testFindByIdUnauthorized() throws Exception {
		var request = JSON.create(userNew);
		this.mockMvc.perform(get(Constant.BASE_URL + "/users/" + "userid")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(request))
				.andDo(print())
				.andExpect(status().isForbidden())
				.andReturn();
	}
	
	@Test
	@Disabled
	public void testFindById() throws Exception {
		// success
		this.mockMvc.perform(get(Constant.BASE_URL + "/users/id")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + JSON.getToken(this.testLoginSuccess()))
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
		
		// not found
		this.mockMvc.perform(get(Constant.BASE_URL + "/users/id")
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + JSON.getToken(this.testLoginSuccess()))
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isForbidden());
	}
	
	@Test
	@Disabled
	public void testUpdate() throws Exception {
		// success
		this.mockMvc.perform(put(Constant.BASE_URL + "/users/id")
						.header("Authorization", "Bearer " + JSON.getToken(this.testLoginSuccess()))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(JSON.create(userUpdate)))
				.andDo(print())
				.andExpect(status().isOk());
		
		// forbidden
		this.mockMvc.perform(put(Constant.BASE_URL + "/users/id")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(JSON.create(userUpdate)))
				.andDo(print())
				.andExpect(status().isForbidden());
		
		// user not found
		this.mockMvc.perform(put(Constant.BASE_URL + "/users/id")
						.accept(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + JSON.getToken(this.testLoginSuccess()))
						.contentType(MediaType.APPLICATION_JSON)
						.content(JSON.create(userUpdate)))
				.andDo(print())
				.andExpect(status().isForbidden());
		
		// internal server error
		this.mockMvc.perform(put(Constant.BASE_URL + "/users/id")
						.accept(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + JSON.getToken(this.testLoginSuccess()))
						.contentType(MediaType.APPLICATION_JSON)
						.content(JSON.create(userUpdate)))
				.andDo(print())
				.andExpect(status().isInternalServerError());
		
	}
}
