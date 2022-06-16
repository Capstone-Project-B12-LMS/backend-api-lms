package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.material.MaterialNew;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Material;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.ClassRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.MaterialRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.DateUtils;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.AnyException;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.ClassNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.getResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Tag(value = "materialServiceTest")
public class MaterialServiceTest {
	public static final MaterialNew materialNew = MaterialNew.builder()
			.classId("id")
			.category("material category")
			.content("material content")
			.deadline("14/06/2022 22:34:50")
			.point(100)
//				.file(null) // TODO: create file service first
//				.video(null) // TODO: create file service first
			.title("material title")
			.topicId("topicId")
			.build();
	public static final Material material = Material.builder()
			.id("id")
			.classes(ClassServiceTest.classEntity)
			.title("material title")
			.content("material content")
			.topic(null)
			.videoUri("url")
			.fileUrl("url")
			.deadline(DateUtils.parse(materialNew.getDeadline()))
			.point(100)
			.category(null)
			.build();
	@Autowired
	private MaterialService materialService;
	@MockBean
	private MaterialRepository materialRepository;
	@MockBean
	private ClassRepository classRepository;
	
	@Test
	public void testNotNull() {
		assertNotNull(materialRepository);
	}
	
	@Test
	public void testSave() {
//        success
		when(this.materialRepository.save(any(Material.class))).thenReturn(material);
		when(this.classRepository.findById(anyString())).thenReturn(Optional.of(ClassServiceTest.classEntity));
		var res = this.materialService.save(materialNew);
		var api = getResponse(res);
		var materialData = (Material) api.getData();
		
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertInstanceOf(Material.class, api.getData());
		assertNotNull(api.getData());
		assertNull(api.getErrors());
		assertTrue(api.isStatus());
		assertEquals(material.getId(), materialData.getId());
		reset(this.classRepository, this.materialRepository);

//		class with {classId} nto found
		when(this.materialRepository.save(any(Material.class))).thenReturn(material);
		when(this.classRepository.findById(anyString())).thenReturn(Optional.empty());
		res = this.materialService.save(materialNew);
		api = getResponse(res);
		materialData = (Material) api.getData();
		
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertNull(api.getData());
		assertNotNull(api.getErrors());
		assertFalse(api.isStatus());
		assertInstanceOf(HashMap.class, api.getErrors());
		assertNotNull(((HashMap<String, Object>) api.getErrors()).get("message"));
		reset(this.classRepository, this.materialRepository);

//		any exceptions
		when(this.materialRepository.save(any(Material.class))).thenThrow(AnyException.class);
		when(this.classRepository.findById(anyString())).thenReturn(Optional.of(ClassServiceTest.classEntity));
		res = this.materialService.save(materialNew);
		api = getResponse(res);
		materialData = (Material) api.getData();
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
		assertNull(api.getData());
		assertNotNull(api.getErrors());
		assertFalse(api.isStatus());
		assertInstanceOf(HashMap.class, api.getErrors());
		assertNotNull(((HashMap<String, Object>) api.getErrors()).get("message"));
		reset(this.classRepository, this.materialRepository);
	}
	
	@Test
	public void testToEntity() {
//		success
		when(this.classRepository.findById(anyString())).thenReturn(Optional.of(ClassServiceTest.classEntity));
		var result = this.materialService.toEntity(materialNew);
		assertNotNull(result);
		assertEquals(materialNew.getClassId(), result.getClasses().getId());
//		assertEquals(materialNew.getCategory(), result.getCategory()); // TODO: create category repo/service first
//		assertEquals(materialNew.getTopicId(), result.getTopic().getId()); // TODO: create topic repo/service first
//		assertEquals(materialNew.getFile().getOriginalFilename(), result.getFileUrl()); // TODO: create file service first
//		assertEquals(materialNew.getVideo().getOriginalFilename(), result.getVideoUri()); // TODO: create file service first
		assertEquals(materialNew.getContent(), result.getContent());
		assertEquals(DateUtils.parse(materialNew.getDeadline()), result.getDeadline());
		assertEquals(materialNew.getPoint(), result.getPoint());
		assertEquals(materialNew.getTitle(), result.getTitle());

//		test exception when parse string to date
//		var materialFailed = MaterialNew.builder()
//				.deadline("hd930")
//				.classId("id")
//				.build();
		var tempDeadline = materialNew.getDeadline();
		materialNew.setDeadline("anyy");
		result = this.materialService.toEntity(materialNew);
		materialNew.setDeadline(tempDeadline);
		assertNull(result.getDeadline());
		reset(this.classRepository);

//		failed
		when(this.classRepository.findById(anyString())).thenReturn(Optional.empty());
		assertThrows(ClassNotFoundException.class, () -> this.materialService.toEntity(materialNew));
	}
	
	@Test
	public void testFindAllByClassId() {
//		success
		when(this.materialRepository.findByClassesId(anyString())).thenReturn(Optional.of(new ArrayList<>(List.of(material))));
		var res = this.materialService.findAllByClassId("id");
		var api = getResponse(res);
		var materials = extract(new ArrayList<Material>(), api);
		
		assertNotNull(res);
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertNull(api.getErrors());
		assertTrue(api.isStatus());
		assertNotNull(api.getData());
		assertInstanceOf(List.class, api.getData());
		assertTrue(materials.isPresent());
		assertEquals(1, materials.get().size());
		assertEquals(material, materials.get().get(0));
		reset(this.materialRepository, this.classRepository);

//		failed
		when(this.materialRepository.findByClassesId(anyString())).thenReturn(Optional.of(new ArrayList<>(List.of())));
		res = this.materialService.findAllByClassId("id");
		api = getResponse(res);
		materials = extract(new ArrayList<Material>(), api);
		
		assertNotNull(res);
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertNull(api.getErrors());
		assertTrue(api.isStatus());
		assertNotNull(api.getData());
		assertInstanceOf(List.class, api.getData());
		assertTrue(materials.isPresent());
		reset(this.materialRepository, this.classRepository);

//		class not found
		when(this.materialRepository.findByClassesId(anyString())).thenReturn(Optional.empty());
		res = this.materialService.findAllByClassId("id");
		api = getResponse(res);
		materials = extract(new ArrayList<Material>(), api);
		
		assertNotNull(res);
		assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
		assertNotNull(api.getErrors());
		assertFalse(api.isStatus());
		assertNull(api.getData());
		assertTrue(materials.isEmpty());
		reset(this.materialRepository, this.classRepository);

//		internal server error
		when(this.materialRepository.findByClassesId(anyString())).thenThrow(AnyException.class);
		res = this.materialService.findAllByClassId("id");
		api = getResponse(res);
		materials = extract(new ArrayList<Material>(), api);
		
		assertNotNull(res);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
		assertNotNull(api.getErrors());
		assertFalse(api.isStatus());
		assertNull(api.getData());
		assertTrue(materials.isEmpty());
		reset(this.materialRepository, this.classRepository);
	}
}
