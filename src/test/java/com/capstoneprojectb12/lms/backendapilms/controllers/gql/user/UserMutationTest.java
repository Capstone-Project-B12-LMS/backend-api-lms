package com.capstoneprojectb12.lms.backendapilms.controllers.gql.user;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@Tag(value = "userMutationTest")
public class UserMutationTest {
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private UserService userService;

        private final String newUser = "{\"data\": { \"user\": { \"register\": { \"fullName\": \"irda islakhu afa\", \"email\": \"myemail@gmail.com\" } } } }";
        private final User user = User.builder()
                        .id("id")
                        .fullName("irda islakhu afa")
                        .email("myemail@gmail.com")
                        .build();

        @Test
        @Disabled
        public void testRegister() throws Exception {
                final var mutationRegister = "{\"query\": \"mutation { user { register ( request: {fullName: \\\"irda    islakhu afa\\\", email: \\\"myemail@gmail.com\\\", password: \\\"mypass\\\",telepon: \\\"1234567890\\\"} ) { fullName email } } } \"}";
                when(this.userService.save(any())).thenReturn(Optional.of(user));

                this.mockMvc.perform(
                                MockMvcRequestBuilders.post("/graphql")
                                                .contentType(MediaType.APPLICATION_GRAPHQL)
                                                .content(mutationRegister)
                // .accept(MediaType.APPLICATION_JSON)
                )
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(MockMvcResultMatchers.status().isOk())
                // .andExpect(MockMvcResultMatchers.content().json(newUser))
                ;
        }
}
