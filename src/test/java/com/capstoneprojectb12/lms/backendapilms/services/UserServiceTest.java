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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;

@SpringBootTest
@Tag(value = "userServiceTest")
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private final static User user = User.builder()
            .fullName("irda islakhu afa")
            .email("myemail@gmail.com")
            .password("mypass")
            .build();

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
}
