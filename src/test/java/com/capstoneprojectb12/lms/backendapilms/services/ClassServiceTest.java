package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.ClassStatus;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.ClassRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.getResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Tag(value = "classServiceTest")
public class ClassServiceTest {
	
	public static final Class classEntity = Class.builder()
			.id("id")
			.name("my class")
			.room("my room")
			.code("wkwkwk")
			.status(ClassStatus.ACTIVE)
			.users(Collections.emptyList())
			.build();
	private final Class classEntity2 = Class.builder()
			.id("id")
			.name("ny class")
			.room("my room")
			.code("wkwkwk")
			.status(ClassStatus.ACTIVE)
			.users(Collections.emptyList())
			.build();
	private final ClassUpdate classUpdate = ClassUpdate.builder()
			.name("updated class")
			.room("updated room")
			.status(ClassStatus.WILL_END)
			.build();
	private final Class updatedClass = Class.builder()
			.id("updated id")
			.name(classUpdate.getName())
			.room(classUpdate.getRoom())
			.status(classUpdate.getStatus())
			.code(classEntity.getCode())
			.users(classEntity.getUsers())
			.build();
	
	private final ClassNew classNew = ClassNew.builder()
			.name(classEntity.getName())
			.room(classEntity.getRoom())
			.build();
	@MockBean
	private ClassRepository classRepository;
	@Autowired
	private ClassService classService;
	
	@Test
	public void testSave() {
//		 success
		when(this.classRepository.save(any(Class.class))).thenReturn(classEntity);
		var res = this.classService.save(classNew);
		var api = getResponse(res);
		var data = (Class) api.getData();
		
		assertNotNull(res);
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertTrue(api.isStatus());
		assertNull(api.getErrors());
		assertNotNull(api.getData());
		assertEquals(classEntity.getName(), data.getName());
		assertEquals(classEntity.getRoom(), data.getRoom());
		reset(this.classRepository);

//		 failed
		when(this.classRepository.save(any(Class.class))).thenReturn(nullable(Class.class));
		
		res = this.classService.save(classNew);
		api = getResponse(res);
		data = (Class) api.getData();
		
		assertNotNull(res);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
		assertFalse(api.isStatus());
		assertNotNull(api.getErrors());
		assertNull(api.getData());
	}
	
	@Test
	public void testUpdate() {
		// success
		var tempName = classEntity.getName();
		classEntity.setName("updated name");
		when(this.classRepository.save(any(Class.class))).thenReturn(classEntity);
		
		// failed
		when(this.classRepository.save(any(Class.class))).thenReturn(nullable(Class.class));
		
		// update with class update
		when(this.classRepository.save(any(Class.class))).thenReturn(updatedClass);
		
	}
	
	@Test
	public void testDeleteById() {
		// success
		when(this.classRepository.existsById(anyString())).thenReturn(true);
		var result = this.classService.deleteById("id");
		
		// failed
		when(this.classRepository.existsById(anyString())).thenReturn(false);
		result = this.classService.deleteById("id");
	}
	
	@Test
	public void testFindById() {
		// success
		when(this.classRepository.findById(anyString())).thenReturn(Optional.ofNullable(classEntity));
		var result = this.classService.findById("id");
		
		// failed
		when(this.classRepository.findById(anyString())).thenReturn(Optional.empty());
		result = this.classService.findById("id");
	}
	
	@Test
	public void testFindAll() {
		when(this.classRepository.findAll()).thenReturn(List.of(classEntity));
		var result = this.classService.findAll();
		assertNotNull(result);
	}
	
	@Test
	public void testFindAllWithPageable() {
		// without sorting
		// success
		when(this.classRepository.findAll(any(Pageable.class)))
				.thenReturn(new PageImpl<>(List.of(classEntity)));
		var result = this.classService.findAll(0, 1);
		assertNotNull(result);
		
		// failed
		when(this.classRepository.findAll(any(Pageable.class)))
				.thenReturn(new PageImpl<>(Collections.emptyList()));
		result = this.classService.findAll(0, 1);
		assertNotNull(result);
		
	}
	
	@Test
	public void testFindAllWithSort() {
		// with sorting
		// ascending
		when(this.classRepository.findAll(any(Pageable.class)))
				.thenReturn(new PageImpl<>(List.of(classEntity, classEntity2)));
		var result = this.classService.findAll(0, 1, Sort.by("name").ascending());
		
		// descending
		when(this.classRepository.findAll(any(Pageable.class)))
				.thenReturn(new PageImpl<>(List.of(classEntity2, classEntity)));
		result = this.classService.findAll(0, 1, Sort.by("name").descending());
		
	}
	
	@Test
	public void testToPaginationResponse() {
		var purePage = new PageImpl<>(List.of(classEntity), PageRequest.of(0, 1), 1);
		var pageResponse = this.classService.toPaginationResponse(purePage);
		assertEquals(0, pageResponse.getPage());
		assertEquals(1, pageResponse.getSize());
		assertEquals(1, pageResponse.getTotalPage());
		assertEquals(1, pageResponse.getTotalSize());
		assertEquals(purePage.getContent(), pageResponse.getData());
	}
	
	@Test
	public void testToEntity() {
		var classNew = ClassNew.builder()
				.name("new class")
				.room("rom of new class")
				.build();
		var entity = this.classService.toEntity(classNew);
		assertEquals(classNew.getName(), entity.getName());
		assertEquals(classNew.getRoom(), entity.getRoom());
	}
}
