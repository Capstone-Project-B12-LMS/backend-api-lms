package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.role.RoleNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.role.RoleUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.RoleRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.MethodNotImplementedException;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.getResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@SpringBootTest
@Tag(value = "roleServiceTest")
public class RoleServiceTest {

    public static final Role role = Role.builder()
            .id("roleId")
            .name("USER")
            .description("-")
            .build();
    private static final RoleNew roleNew = RoleNew.builder()
            .name(role.getName())
            .description(role.getDescription())
            .build();
    private static final RoleUpdate roleUpdate = RoleUpdate.builder()
            .name(roleNew.getName())
            .description(roleNew.getDescription())
            .build();
    @MockBean
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;


    @Test
    public void testSave() {
//		success
        when(this.roleRepository.save(any())).thenReturn(role);
        var res = this.roleService.save(roleNew);
        var api = getResponse(res);
        var saved = (Role) api.getData();

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertTrue(api.isStatus());
        assertNull(api.getErrors());
        assertNotNull(saved);
        assertEquals(role.getName(), saved.getName());
        assertEquals(role.getDescription(), saved.getDescription());
        reset(this.roleRepository);

//		failed and already exists
        when(this.roleRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
        res = this.roleService.save(roleNew);
        api = getResponse(res);
        saved = (Role) api.getData();

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        assertFalse(api.isStatus());
        assertNotNull(api.getErrors());
        assertNull(saved);
        reset(this.roleRepository);

//		any exception
        when(this.roleRepository.save(any())).thenThrow(NoSuchElementException.class);
        res = this.roleService.save(roleNew);
        api = getResponse(res);
        saved = (Role) api.getData();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
        assertFalse(api.isStatus());
        assertNotNull(api.getErrors());
        assertNull(saved);

    }

    @Test
    public void testUpdate() {
//		success
        when(this.roleRepository.findById(anyString())).thenReturn(Optional.of(role));
        when(this.roleRepository.save(any(Role.class))).thenReturn(role);
        var res = this.roleService.update("id", roleUpdate);
        var api = getResponse(res);
        var roleData = (Role) api.getData();

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertTrue(api.isStatus());
        assertNotNull(api.getData());
        assertNull(api.getErrors());
        assertEquals(role, roleData);
        reset(this.roleRepository);

//		any exception
        when(this.roleRepository.findById(anyString())).thenReturn(Optional.empty());
        res = this.roleService.update("id", roleUpdate);
        api = getResponse(res);
        roleData = (Role) api.getData();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
        assertFalse(api.isStatus());
        assertNull(api.getData());
        assertNotNull(api.getErrors());
    }

    @Test
    public void testFindById() {
//		success
        when(this.roleRepository.findById(anyString())).thenReturn(Optional.of(role));
        var res = this.roleService.findById("id");
        var api = getResponse(res);
        var roleData = (Role) api.getData();


        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertTrue(api.isStatus());
        assertNotNull(api.getData());
        assertNull(api.getErrors());
        reset(this.roleRepository);


//		data not found
        when(this.roleRepository.findById(anyString())).thenReturn(Optional.empty());
        res = this.roleService.findById("id");
        api = getResponse(res);
        roleData = (Role) api.getData();

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        assertFalse(api.isStatus());
        assertNotNull(api.getErrors());
        assertNull(api.getData());
        reset(this.roleRepository);

//		any exception
        when(this.roleRepository.findById(anyString())).thenThrow(NoSuchElementException.class);
        res = this.roleService.findById("id");
        api = getResponse(res);
        roleData = (Role) api.getData();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
        assertFalse(api.isStatus());
        assertNotNull(api.getErrors());
        assertNull(api.getData());
    }

    @Test
    public void testFindAll() {
//		success
        when(this.roleRepository.findAll()).thenReturn(List.of(role));
        var res = this.roleService.findAll();
        var api = getResponse(res);
        var roleData = (List<Role>) api.getData();

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertTrue(api.isStatus());
        assertNotNull(api.getData());
        assertNull(api.getErrors());
        assertEquals(List.of(role), roleData);
        reset(this.roleRepository);

//		any exception
        when(this.roleRepository.findAll()).thenThrow(NoSuchElementException.class);
        res = this.roleService.findAll();
        api = getResponse(res);
        roleData = (List<Role>) api.getData();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
        assertFalse(api.isStatus());
        assertNull(api.getData());
        assertNotNull(api.getErrors());
        assertNotEquals(List.of(role), roleData);
    }

    @Test
    @Disabled
    public void testFindAllDeleted() {
//		not implemented
        assertThrows(MethodNotImplementedException.class, () -> this.roleService.findAll(true));
    }

    @Test
    public void testDeleteById() {
//		success
        when(this.roleRepository.findById(anyString())).thenReturn(Optional.of(role));
        var res = this.roleService.deleteById("id");
        var api = getResponse(res);
        var roleData = (Role) api.getData();
        assertNotNull(roleData);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertTrue(api.isStatus());
        assertNull(api.getErrors());
        assertEquals(role.getId(), roleData.getId());
        assertEquals(role.getName(), roleData.getName());
        assertEquals(role.getDescription(), roleData.getDescription());
        reset(this.roleRepository);

//		data not found
        when(this.roleRepository.findById(anyString())).thenReturn(Optional.empty());
        res = this.roleService.deleteById("id");
        api = getResponse(res);
        roleData = (Role) api.getData();
        assertNull(roleData);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        assertFalse(api.isStatus());
        assertNotNull(api.getErrors());
        assertNull(api.getData());
        reset(this.roleRepository);

//		any exception
        when(this.roleRepository.findById(anyString())).thenThrow(NoSuchElementException.class);
        res = this.roleService.deleteById("id");
        api = getResponse(res);
        roleData = (Role) api.getData();
        assertNull(roleData);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
        assertFalse(api.isStatus());
        assertNotNull(api.getErrors());
        assertNull(api.getData());
    }

    @Test
    public void testFindAllWithPageable() {
//		success
        when(this.roleRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(role), PageRequest.of(0, 1), 1));
        var res = this.roleService.findAll(0, 1);
        var api = getResponse(res);
        var pageResponse = (PaginationResponse<List<Role>>) api.getData();

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertNotNull(api);
        assertTrue(api.isStatus());
        assertNotNull(api.getData());
        assertNull(api.getErrors());
        assertEquals(0, pageResponse.getPage());
        assertEquals(1, pageResponse.getSize());
        assertEquals(1, pageResponse.getTotalPage());
        assertEquals(1, pageResponse.getTotalSize());
        assertEquals(role, pageResponse.getData().get(0));
        reset(this.roleRepository);

//		any exception
        when(this.roleRepository.findAll(any(Pageable.class))).thenThrow(NoSuchElementException.class);
        res = this.roleService.findAll(0, 1);
        api = getResponse(res);
        pageResponse = (PaginationResponse<List<Role>>) api.getData();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
        assertNotNull(api);
        assertFalse(api.isStatus());
        assertNull(api.getData());
        assertNotNull(api.getErrors());
        assertNull(pageResponse);
    }

    @Test
    public void testFindByNames() {
//		success
        when(this.roleRepository.findByNameEqualsIgnoreCase(anyString())).thenReturn(Optional.of(role));
        var roles = this.roleService.findByNames("USER", "your name");
        assertNotNull(role);
        assertEquals(1, roles.size());
        assertEquals("USER", roles.get(0).getName());
        reset(this.roleRepository);

//		failed to get role from database and create it
        when(this.roleRepository.findByNameEqualsIgnoreCase(anyString())).thenReturn(Optional.empty());
        when(this.roleRepository.save(any(Role.class))).thenReturn(role);
        roles = this.roleService.findByNames("USER");
        assertNotNull(roles);
        assertEquals(1, roles.size());
    }
}