package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.Beans;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.MethodNotImplementedException;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.UserNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.getResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@Tag(value = "userServiceTest")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    public static final User user = User.builder()
            .id("id")
            .fullName("irda islakhu afa")
            .email("myemail@gmail.com")
            .telepon("0987654321")
            .isDeleted(false)
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

    private static final Page<User> userPages = new PageImpl<User>(List.of(user), PageRequest.of(0, 1), 1);
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

        assertFalse(savedUser.getIsDeleted());
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
//		success
        when(this.userRepository.findById(anyString())).thenReturn(Optional.of(user));
        var response = this.userService.deleteById("id");
        var apiRes = getResponse(response);
        var deletedUser = (User) apiRes.getData();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(apiRes.isStatus());

        assertNotNull(deletedUser);
        assertEquals(user.getId(), deletedUser.getId());
        reset(this.userRepository);

//		not found
        when(this.userRepository.findById(anyString())).thenReturn(Optional.empty());
        response = this.userService.deleteById("id");
        apiRes = getResponse(response);
        deletedUser = (User) apiRes.getData();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        assertNull(deletedUser);
        assertNotNull(apiRes.getErrors());
        assertFalse(apiRes.isStatus());
        reset(this.userRepository);

//		any exception
        when(this.userRepository.findById(anyString())).thenThrow(NullPointerException.class);
        response = this.userService.deleteById("id");
        apiRes = getResponse(response);
        deletedUser = (User) apiRes.getData();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse(apiRes.isStatus());

        assertNull(deletedUser);
        assertNotNull(apiRes.getErrors());
    }

    @Test
    public void testFindAll() {
//		success
        when(this.userRepository.findAll()).thenReturn(List.of(user));
        var response = this.userService.findAll();
        var apiRes = getResponse(response);
        var users = (List<User>) apiRes.getData();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(apiRes.isStatus());
        assertNull(apiRes.getErrors());
        assertNotNull(users);
        reset(this.userRepository);

//		any exception
        when(this.userRepository.findAll()).thenThrow(NoSuchElementException.class);
        response = this.userService.findAll();
        apiRes = getResponse(response);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse(apiRes.isStatus());
        assertNotNull(apiRes.getErrors());
        assertNull(apiRes.getData());
    }

    @Test
    @Disabled
    public void testFindAllDeleted() {
//		not implemented
        assertThrows(MethodNotImplementedException.class, () -> this.userService.findAll(true));
    }

    @Test
    public void testFindAllWithPageable() {
//		success
        when(this.userRepository.findAll(any(Pageable.class))).thenReturn(userPages);
        var response = this.userService.findAll(0, 1);
        var apiRes = getResponse(response);
        var pageResponse = (PaginationResponse<List<User>>) apiRes.getData();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(pageResponse);
        assertEquals(0, pageResponse.getPage());
        assertEquals(1, pageResponse.getSize());
        assertEquals(1, pageResponse.getTotalPage());
        assertEquals(1, pageResponse.getTotalSize());
        reset(this.userRepository);

//		any exception
        when(this.userRepository.findAll(any(Pageable.class))).thenThrow(NullPointerException.class);
        response = this.userService.findAll(0, 1);
        apiRes = getResponse(response);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse(apiRes.isStatus());
        assertNotNull(apiRes.getErrors());
        assertNull(apiRes.getData());
    }

    @Test
    public void testGetCurrentUser() {
//		success
        MockedStatic<SecurityContextHolder> security = mockStatic(SecurityContextHolder.class);
        security.when(() -> SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(new SecurityContext() {
            @Override
            public Authentication getAuthentication() {
                return new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), user.getAuthorities());
            }

            @Override
            public void setAuthentication(Authentication authentication) {

            }
        });

        var email = this.userService.getCurrentUser();
        assertNotNull(email);
        assertEquals(user.getEmail(), email);
        security.close();

//		failed and use system
        email = this.userService.getCurrentUser();
        assertEquals("SYSTEM", email);
    }
}
