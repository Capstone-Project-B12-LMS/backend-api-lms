package com.capstoneprojectb12.lms.backendapilms.controllers.gql.user;

import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Tag(value = "userMutationTest")
public class UserMutationTest {
	private final String newUser = "{\"data\": { \"user\": { \"register\": { \"fullName\": \"irda islakhu afa\", \"email\": \"myemail@gmail.com\" } } } }";
	private final User user = User.builder()
			.id("id")
			.fullName("irda islakhu afa")
			.email("myemail@gmail.com")
			.build();
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;
	
	@Test
	@Disabled
	public void testRegister() throws Exception {
		final var mutationRegister = "{\"query\": \"mutation { user { register ( request: {fullName: \\\"irda    islakhu afa\\\", email: \\\"myemail@gmail.com\\\", password: \\\"mypass\\\",telepon: \\\"1234567890\\\"} ) { fullName email } } } \"}";
	}
}
